// src/main/java/com/vini/backend/repositories/InternshipEvaluationRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.internship.InternshipEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipEvaluationRepository extends JpaRepository<InternshipEvaluation, Long> {
    Optional<InternshipEvaluation> findByInternshipId(Long internshipId);
    List<InternshipEvaluation> findByFacultyUid(String facultyUid);
}
