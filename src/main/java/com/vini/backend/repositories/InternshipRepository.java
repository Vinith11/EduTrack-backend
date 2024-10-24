
// src/main/java/com/vini/backend/repositories/InternshipRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.internship.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByStudentUsn(String studentUsn);
}
