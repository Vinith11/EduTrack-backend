package com.vini.backend.service.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.Faculty;
import com.vini.backend.models.Student;
import com.vini.backend.models.project.FacultyProjectGuide;
import com.vini.backend.models.project.Project;
import com.vini.backend.repositories.FacultyProjectGuideRepository;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.repositories.ProjectRepository;
import com.vini.backend.repositories.StudentRepository;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final FacultyProjectGuideRepository facultyProjectGuideRepository;
    private final EmailService emailService;


    @Override
    public String createProject(Project project) throws NotFoundException {
        // Check student leader and guide existence
        String leaderId = project.getStudentProjectLeaderId();
        Optional<Student> leader = studentRepository.findById(leaderId);
        if (leader.isEmpty()) {
            throw new NotFoundException("Project leader not found.");
        }
        Optional<Faculty> guide = facultyRepository.findById(project.getStudentProjectGuideId());
        if (guide.isEmpty()) {
            throw new NotFoundException("Faculty guide not found.");
        }

        if (projectRepository.existsByStudentProjectLeaderId(leaderId)) {
            throw new NotFoundException("Student with USN " + leaderId + " already has a project.");
        }

        if (project.getTeamMembers().size() > 3 || project.getTeamMembers().isEmpty()) {
            throw new NotFoundException("Team members should not exceed 3 or be less than 1");
        }

        // Check if team members are not already part of another group
        for (String memberId : project.getTeamMembers()) {
            Student teamMember = studentRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Student with USN " + memberId + " not found."));
            if (teamMember.getProjectId() != null) {
                throw new NotFoundException("Student with USN " + memberId + " is already part of another group.");
            }
            if (projectRepository.existsByTeamMembersContaining(memberId)) {
                throw new NotFoundException("Student with USN " + memberId + " is already part of another group.");
            }
        }

        // Check faculty guide's group limit
        Long count = facultyProjectGuideRepository.countByFacultyUidAndBatch(project.getStudentProjectGuideId(), project.getAcademicYear());
        if (count >= 3) {
            throw new NotFoundException("Faculty guide has already reached the maximum group limit.");
        }

        project.setFacultyApprovalStatus(false); // Default approval status is false

        // Save project to obtain project ID
        Project savedProject = projectRepository.save(project);

        // Assign projectId to leader and team members
        Student projectLeader = leader.get();
        projectLeader.setProjectId(savedProject.getProjectId());
        studentRepository.save(projectLeader);

        for (String memberId : project.getTeamMembers()) {
            Student teamMember = studentRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Student with USN " + memberId + " not found."));
            teamMember.setProjectId(savedProject.getProjectId());
            studentRepository.save(teamMember);
        }

        // Send email to faculty for approval
        emailService.sendApprovalRequestEmail(guide.get().getFacultyEmail(), savedProject)
                .exceptionally(throwable -> {
                    System.err.println("Email sending failed: " + throwable.getMessage());
                    return null;
                });

        return "Form submitted successfully";
    }



    @Override
    @Transactional
    public String approveProject(Long projectId, Boolean approvalStatus) throws NotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));

        if (approvalStatus) {
            // Approve the project
            if (project.getFacultyApprovalStatus()) {
                throw new NotFoundException("Project already approved.");
            }

            Long count = facultyProjectGuideRepository.countByFacultyUidAndBatch(project.getStudentProjectGuideId(), project.getAcademicYear());
            if (count >= 3) {
                throw new NotFoundException("Faculty guide has already reached the maximum group limit.");
            }

            project.setFacultyApprovalStatus(true);

            // Update student leader
            Student leader = studentRepository.findById(project.getStudentProjectLeaderId())
                    .orElseThrow(() -> new NotFoundException("Leader not found."));
            leader.setLeader(true);
            studentRepository.save(leader);

            // Add to FacultyProjectGuide
            FacultyProjectGuide guide = new FacultyProjectGuide(null, project.getProjectId(), project.getStudentProjectGuideId(), project.getAcademicYear());
            facultyProjectGuideRepository.save(guide);

            // Send approval email
            emailService.sendAcceptEmail(leader.getStudentEmail(), project)
                    .exceptionally(throwable -> {
                        System.err.println("Email sending failed: " + throwable.getMessage());
                        return null;
                    });

            return "Request approved successfully";
        } else {
            // Reject the project and remove projectId from students
            for (String memberId : project.getTeamMembers()) {
                Student member = studentRepository.findById(memberId)
                        .orElseThrow(() -> new NotFoundException("Student with USN " + memberId + " not found."));
                member.setProjectId(null);
                studentRepository.save(member);
            }

            Student leader = studentRepository.findById(project.getStudentProjectLeaderId())
                    .orElseThrow(() -> new NotFoundException("Leader not found."));
            leader.setLeader(false);
            leader.setProjectId(null);
            studentRepository.save(leader);

            // Send rejection email
            emailService.sendRejectionEmail(leader.getStudentEmail(), project)                .exceptionally(throwable -> {
                System.err.println("Email sending failed: " + throwable.getMessage());
                return null;
            });

            projectRepository.delete(project);

            return "Request rejected successfully";
        }
    }



    @Override
    public Project completeProject(Long projectId) throws NotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));
        project.setStudentProjectCompletionStatus("Completed");
        return projectRepository.save(project);
    }

    @Override
    public List<Project> pendingAproveProjects(String facultyUid) throws NotFoundException {
        Faculty faculty = facultyRepository.findById(facultyUid)
                .orElseThrow(() -> new NotFoundException("Faculty not found."));
        return projectRepository.findByStudentProjectGuideIdAndFacultyApprovalStatus(facultyUid, false);
    }

    @Override
    public List<Project> getProjectsByFacultyUidByBatch(String facultyUid, String batch) throws NotFoundException {
        Faculty faculty = facultyRepository.findById(facultyUid)
                .orElseThrow(() -> new NotFoundException("Faculty not found."));
        return projectRepository.findByStudentProjectGuideIdAndAcademicYearAndFacultyApprovalStatus(facultyUid, batch, true);
    }

    @Override
    public List<Project> getProjectsByBatch(String batch) throws NotFoundException {
        return projectRepository.findByAcademicYearAndFacultyApprovalStatus(batch, true);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) throws NotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));

        FacultyProjectGuide guide = facultyProjectGuideRepository.findFacultyProjectGuideByProjectId(projectId);
        facultyProjectGuideRepository.delete(guide);

        Student leader = studentRepository.findById(project.getStudentProjectLeaderId())
                .orElseThrow(() -> new NotFoundException("Leader not found."));
        leader.setLeader(false);
        leader.setProjectId(null);
        studentRepository.save(leader);

        List<String> members = project.getTeamMembers();
        for (String memberId : members) {
            Student member = studentRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Member not found."));
            member.setProjectId(null);
            studentRepository.save(member);
        }

        projectRepository.delete(project);
    }

    @Override
    public Project getProjectById(Long projectId) throws NotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));
    }

    @Override
    public List<Student> getAvailableStudentsBatchWise(String batch) throws NotFoundException {
        List<Student> students = studentRepository.findByStudentBatchAndProjectIdIsNull(batch);
        if (students.isEmpty()) {
            throw new NotFoundException("No available students found for batch: " + batch);
        }
        return students;
    }

    @Override
    public boolean updateProjectUrls(Long projectId, String studentProjectUrl, String studentProjectReport) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setStudentProjectUrl(studentProjectUrl);
            project.setStudentProjectReport(studentProjectReport);
            project.setStudentProjectCompletionStatus("Completed");
            projectRepository.save(project);
            return true;
        }
        return false;
    }

    @Override
    public List<Faculty> getAvailableFacultyBatchWise(String batch) throws NotFoundException {
        // Fetch all faculty members from the database
        List<Faculty> allFaculty = facultyRepository.findAll();

        // Fetch all project guides for the specified batch
        List<FacultyProjectGuide> projectGuides = facultyProjectGuideRepository.findByBatch(batch);

        // Create a map to count projects assigned to each faculty in the given batch
        Map<String, Long> facultyCountMap = projectGuides.stream()
                .collect(Collectors.groupingBy(FacultyProjectGuide::getFacultyUid, Collectors.counting()));

        // Filter faculty based on the conditions
        List<Faculty> availableFaculty = allFaculty.stream()
                .filter(faculty -> {
                    Long count = facultyCountMap.getOrDefault(faculty.getFacultyUid(), 0L);
                    return count < 2; // Include faculty with less than 2 assignments or not assigned
                })
                .collect(Collectors.toList());

        // If no available faculty found, throw NotFoundException
        if (availableFaculty.isEmpty()) {
            throw new NotFoundException("No available faculty found for batch: " + batch);
        }

        return availableFaculty;
    }


}

