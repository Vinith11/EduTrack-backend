
// src/main/java/com/vini/backend/repositories/StudentInternshipFeedbackRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.internship.StudentInternshipFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInternshipFeedbackRepository extends JpaRepository<StudentInternshipFeedback, Long> {
    StudentInternshipFeedback findByInternshipId(Long internshipId);

    List<StudentInternshipFeedback> findByUsn(String usn);
}