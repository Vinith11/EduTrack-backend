package com.vini.backend.service;


import com.vini.backend.models.Student;
import com.vini.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByStudentEmail(username);

        if (student == null) {
            throw new UsernameNotFoundException("Student not found with email " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(student.getStudentEmail(), student.getStudentPassword(), authorities);
    }
}


