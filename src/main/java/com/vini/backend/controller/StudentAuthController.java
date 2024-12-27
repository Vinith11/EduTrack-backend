package com.vini.backend.controller;


import com.vini.backend.request.LoginRequestStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vini.backend.config.JwtTokenProvider;
import com.vini.backend.models.Student;
import com.vini.backend.exception.UserException;
import com.vini.backend.repositories.StudentRepository;
import com.vini.backend.response.AuthResponse;
import com.vini.backend.service.StudentDetailsService;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/auth/student")
@RequiredArgsConstructor
public class StudentAuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentDetailsService studentDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createStudentHandler(@Valid @RequestBody Student student) throws UserException {
        String email = student.getStudentEmail();
        String password = student.getStudentPassword();
        String usn = student.getUsn();
        String studentName = student.getStudentName();
        String studentPhone = student.getStudentPhone();
        String studentBatch = student.getStudentBatch();


        Student isEmailExist = studentRepository.findByStudentEmail(email)
                .orElseThrow(() -> new UserException("User does not exist with email " + email));

        student.setStudentPassword(passwordEncoder.encode(password));
        student.setStudentEmail(email);
        student.setUsn(usn);
        student.setStudentName(studentName);
        student.setStudentPhone(studentPhone);
        student.setStudentBatch(studentBatch);
        Student savedStudent = studentRepository.save(student);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequestStudent loginRequestStudent) {
        String username = loginRequestStudent.getEmail();
        String password = loginRequestStudent.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        return getAuthentication(password, studentDetailsService.loadUserByUsername(username), passwordEncoder, username);
    }

    static Authentication getAuthentication(String password, UserDetails userDetails2, PasswordEncoder passwordEncoder, String username) {
        UserDetails userDetails = userDetails2;

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

