package com.vini.backend.controller;


import com.vini.backend.response.StudentResponseDto;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;
import com.vini.backend.service.StudentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/users")
public class StudentUserController {
    private StudentUserService studentUserService;

    public StudentUserController(StudentUserService studentUserService) {
        this.studentUserService = studentUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllUsers(@RequestHeader("Authorization") String jwt) {
        System.out.println("/api/student/users/all");
        List<Student> student = studentUserService.findAllUsers();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/student/users/profile");
        Student student = studentUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<Student>(student,HttpStatus.ACCEPTED);
    }

    @GetMapping("/batch/{batch}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByBatch(@PathVariable String batch) {
        List<StudentResponseDto> response = studentUserService.getStudentsByBatch(batch);
        return ResponseEntity.ok(response);
    }

}
