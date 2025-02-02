package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.Student;
import com.vini.backend.models.internship.Internship;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.repositories.InternshipRepository;
import com.vini.backend.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternshipServiceImpl implements InternshipService {

    private final InternshipRepository internshipRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    @Override
    public Optional<Internship> getInternshipById(Long internshipId) throws NotFoundException {
        Optional<Internship> existingInternship = internshipRepository.findById(internshipId);
        if (existingInternship.isEmpty()) {
            throw new NotFoundException("Internship not found with ID " + internshipId);
        }
        return internshipRepository.findById(internshipId);
    }

    @Override
    public Internship createInternship(Internship internship) throws NotFoundException {
        if(studentRepository.findById(internship.getStudentUsn()).isEmpty()) {
            throw new NotFoundException("Student not found with USN " + internship.getStudentUsn());
        }

        if(facultyRepository.findByFacultyUid(internship.getFacultyUid()) == null) {
            throw new NotFoundException("Faculty not found with UID " + internship.getFacultyUid());
        }

        // Calculate duration in months
        if (internship.getInternshipStart() != null && internship.getInternshipEnd() != null) {
            long months = ChronoUnit.MONTHS.between(
                    internship.getInternshipStart().withDayOfMonth(1),
                    internship.getInternshipEnd().withDayOfMonth(1)
            );
            internship.setInternshipDuration(months + " months");
        } else {
            internship.setInternshipDuration("Duration not available");
        }


        return internshipRepository.save(internship);
    }

    @Override
    public Internship updateInternship(Long internshipId, Internship internship) throws NotFoundException {
        if(studentRepository.findById(internship.getStudentUsn()).isEmpty()) {
            throw new NotFoundException("Student not found with USN " + internship.getStudentUsn());
        } else if(facultyRepository.findByFacultyUid(internship.getFacultyUid()) == null) {
            throw new NotFoundException("Faculty not found with UID " + internship.getFacultyUid());
        }
        Optional<Internship> existingInternship = internshipRepository.findById(internshipId);
        if (existingInternship.isPresent()) {
            Internship updatedInternship = existingInternship.get();
            updatedInternship.setStudentUsn(internship.getStudentUsn());
            updatedInternship.setInternshipStart(internship.getInternshipStart());
            updatedInternship.setInternshipEnd(internship.getInternshipEnd());
            updatedInternship.setInternshipDuration(internship.getInternshipDuration());
            updatedInternship.setInternshipLocation(internship.getInternshipLocation());
            updatedInternship.setInternshipDomain(internship.getInternshipDomain());
            updatedInternship.setCompanyName(internship.getCompanyName());
            updatedInternship.setInternshipCompletionCertificateUrl(internship.getInternshipCompletionCertificateUrl());
            updatedInternship.setFacultyUid(internship.getFacultyUid());
            return internshipRepository.save(updatedInternship);
        }
        throw new NotFoundException("Internship not found with ID " + internshipId);
    }

    @Override
    public void deleteInternship(Long internshipId) throws NotFoundException {
        Optional<Internship> existingInternship = internshipRepository.findById(internshipId);
        if (existingInternship.isEmpty()) {
            throw new NotFoundException("Internship not found with ID " + internshipId);
        }
        internshipRepository.deleteById(internshipId);
    }

    // Implementing the new method to fetch internships by student USN
    @Override
    public List<Internship> getInternshipsByStudentUsn(String studentUsn) throws NotFoundException {
            // Fetch the student by USN
            studentRepository.findById(studentUsn)
                    .orElseThrow(() -> new NotFoundException("Student not found with USN " + studentUsn));
        if(internshipRepository.findByStudentUsn(studentUsn).isEmpty()) {
            return new ArrayList<>();
        }

        return internshipRepository.findByStudentUsn(studentUsn);
    }

    public List<Internship> getInternshipsByBatch(String batch) throws NotFoundException {
        // Fetch all students in the given batch
        List<Student> students = studentRepository.findByStudentBatch(batch);

        if (students.isEmpty()) {
            throw new NotFoundException("No students found for batch: " + batch);
        }

        // Extract USNs of students in the batch
        List<String> studentUsns = students.stream()
                .map(Student::getUsn)
                .collect(Collectors.toList());

        // Fetch internships for these USNs
        List<Internship> internships = internshipRepository.findByStudentUsnIn(studentUsns);



        return internships;
    }
}
