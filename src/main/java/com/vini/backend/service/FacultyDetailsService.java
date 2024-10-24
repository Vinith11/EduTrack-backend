package com.vini.backend.service;

import com.vini.backend.models.Faculty;
import com.vini.backend.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyDetailsService implements UserDetailsService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyDetailsService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Faculty faculty = facultyRepository.findByFacultyEmail(username);

        if (faculty == null) {
            throw new UsernameNotFoundException("Faculty not found with email " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(faculty.getFacultyEmail(), faculty.getFacultyPassword(), authorities);
    }
}
