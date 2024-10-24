
// src/main/java/com/vini/backend/repositories/InternshipFeedbackRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.internship.FacultyInternshipFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyInternshipFeedbackRepository extends JpaRepository<FacultyInternshipFeedback, Long> {
    List<FacultyInternshipFeedback> findByFacultyUid(String facultyUid);

    FacultyInternshipFeedback findByInternshipId(Long internshipId);
}
