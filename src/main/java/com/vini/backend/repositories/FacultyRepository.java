// src/main/java/com/vini/backend/repositories/FacultyRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, String> {
    public Faculty findByFacultyEmail(String email);

    public List<Faculty> findAllFacultyByOrderByFacultyUid();

    public  Faculty findByFacultyUid(String facultyUid);

}