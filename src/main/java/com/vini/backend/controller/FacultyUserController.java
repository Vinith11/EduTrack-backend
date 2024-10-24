package com.vini.backend.controller;

import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.service.FacultyUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/users")
public class FacultyUserController {
    private FacultyUserService facultyUserService;

    public FacultyUserController(FacultyUserService facultyUserService) {
        this.facultyUserService = facultyUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Faculty>> getAllUsers(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/faculty/users/all");
        List<Faculty> faculty = facultyUserService.findAllUsers();
        return new ResponseEntity<>(faculty, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Faculty> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/faculty/users/profile");
        Faculty faculty = facultyUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<Faculty>(faculty, HttpStatus.ACCEPTED);
    }
}
