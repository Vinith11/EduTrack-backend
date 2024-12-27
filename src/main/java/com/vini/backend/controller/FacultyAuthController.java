package com.vini.backend.controller;

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
import com.vini.backend.models.Faculty;
import com.vini.backend.exception.UserException;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.request.LoginFacultyRequest;
import com.vini.backend.response.AuthResponse;
import com.vini.backend.service.FacultyDetailsService;

import jakarta.validation.Valid;

import static com.vini.backend.controller.StudentAuthController.getAuthentication;

@RestController
@RequestMapping("/auth/faculty")
@RequiredArgsConstructor
public class FacultyAuthController {

    private final FacultyRepository facultyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final FacultyDetailsService facultyDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createFacultyHandler(@Valid @RequestBody Faculty faculty) throws UserException {
        String email = faculty.getFacultyEmail();
        String password = faculty.getFacultyPassword();
        String facultyName = faculty.getFacultyName();
        String facultyPhone = faculty.getFacultyPhone();
        String facultyRole = faculty.getFacultyRole();
        String facultyUid = faculty.getFacultyUid();

        Faculty isEmailExist = facultyRepository.findByFacultyEmail(email)
                .orElseThrow(() -> new UserException("Faculty with email " + email + " does not exist"));


        faculty.setFacultyPassword(passwordEncoder.encode(password));
        faculty.setFacultyEmail(email);
        faculty.setFacultyRole(facultyRole);
        faculty.setFacultyName(facultyName);
        faculty.setFacultyPhone(facultyPhone);
        faculty.setFacultyUid(facultyUid);

        Faculty savedFaculty = facultyRepository.save(faculty);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginFacultyRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        return getAuthentication(password, facultyDetailsService.loadUserByUsername(username), passwordEncoder, username);
    }
}

