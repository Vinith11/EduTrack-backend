// src/main/java/com/vini/backend/repositories/FacultyRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Optional<Faculty> findByFacultyEmail(String email);

    List<Faculty> findAllFacultyByOrderByFacultyUid();

    Faculty findByFacultyUid(String facultyUid);

}