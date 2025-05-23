// src/main/java/com/vini/backend/repositories/StudentRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentEmail(String email);

    List<Student> findAllStudentByOrderByUsn();

    Student findByUsn(String usn);

    List<Student> findByStudentBatch(String batch);

    List<Student> findByStudentBatchAndProjectIdIsNull(String batch);

}
