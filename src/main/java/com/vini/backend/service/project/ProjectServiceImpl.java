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
import com.vini.backend.service.email.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyProjectGuideRepository facultyProjectGuideRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Project createProject(Project project) throws NotFoundException {
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

        if(projectRepository.existsByStudentProjectLeaderId(leaderId)){
            throw new NotFoundException("Student with USN " + leaderId + " already has a project.");
        }



        if(project.getTeamMembers().size()>3 || project.getTeamMembers().isEmpty()){
            throw new NotFoundException("Team members should not exceed 3 or be less than 1" );
        }

        // Check if team members are not already part of another group
        for (String memberId : project.getTeamMembers()) {
            Student teamMember = studentRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("Student with USN " + memberId + " not found."));
            if (teamMember.getProjectId() != null) {
                throw new NotFoundException("Student with USN " + memberId + " is already part of another group.");
            }
            if(projectRepository.existsByTeamMembersContaining(memberId)){
                throw new NotFoundException("Student with USN " + memberId + " is already part of another group.");
            }
        }

        // Check faculty guide's group limit
        Long count = facultyProjectGuideRepository.countByFacultyUidAndBatch(project.getStudentProjectGuideId(), project.getAcademicYear());
        if (count >= 3) {
            throw new NotFoundException("Faculty guide has already reached the maximum group limit.");
        }

        project.setFacultyApprovalStatus(false); // Set default approval status as false

        Project savedProject = projectRepository.save(project);

        // Send email to faculty for approval

        emailService.sendApprovalRequestEmail(guide.get().getFacultyEmail(), savedProject);



        return savedProject;
    }

    @Override
    public Project approveProject(Long projectId, Boolean approvalStatus) throws NotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found."));


        if (approvalStatus) {
            // Check faculty guide's group limit
            if(project.getFacultyApprovalStatus()){
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
            leader.setProjectId(project.getProjectId());
            studentRepository.save(leader);

            // Update team members
            for (String memberId : project.getTeamMembers()) {
                Student member = studentRepository.findById(memberId)
                        .orElseThrow(() -> new NotFoundException("Member not found."));
                member.setProjectId(project.getProjectId());
                studentRepository.save(member);
            }

            // Add to FacultyProjectGuide
            FacultyProjectGuide guide = new FacultyProjectGuide(null, project.getProjectId(), project.getStudentProjectGuideId(), project.getAcademicYear());
            facultyProjectGuideRepository.save(guide);

            // Send approval email to students

                emailService.sendAcceptEmail(leader.getStudentEmail(), project);

        } else {
            Student leader = studentRepository.findById(project.getStudentProjectLeaderId())
                    .orElseThrow(() -> new NotFoundException("Leader not found."));
            // Project rejected, delete project and revert any changes

                emailService.sendRejectionEmail(leader.getStudentEmail(), project);

            projectRepository.delete(project);
        }

        return project;
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
}

