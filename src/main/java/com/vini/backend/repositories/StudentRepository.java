// src/main/java/com/vini/backend/repositories/StudentRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    public Student findByStudentEmail(String email);

    public List<Student> findAllStudentByOrderByUsn();

    public  Student findByUsn(String usn);

}
