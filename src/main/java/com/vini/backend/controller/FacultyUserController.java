package com.vini.backend.controller;

import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.response.FacultyResponseDto;
import com.vini.backend.service.FacultyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/users")
@RequiredArgsConstructor
public class FacultyUserController {
    private final FacultyUserService facultyUserService;

    @GetMapping("/all")
    public ResponseEntity<List<Faculty>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/faculty/users/all");
        List<Faculty> faculty = facultyUserService.findAllUsers();
        return new ResponseEntity<>(faculty, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<FacultyResponseDto> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/faculty/users/profile");
        FacultyResponseDto faculty = facultyUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }
}
