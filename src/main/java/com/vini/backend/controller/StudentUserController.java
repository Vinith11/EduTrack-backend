package com.vini.backend.controller;


import com.vini.backend.response.StudentResponseDto;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Student;
import com.vini.backend.service.StudentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/users")
@RequiredArgsConstructor
public class StudentUserController {
    private final StudentUserService studentUserService;

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllUsers(@RequestHeader("Authorization") String jwt) {
        List<Student> student = studentUserService.findAllUsers();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentResponseDto> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        StudentResponseDto student = studentUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/batch/{batch}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByBatch(@PathVariable String batch) {
        List<StudentResponseDto> response = studentUserService.getStudentsByBatch(batch);
        return ResponseEntity.ok(response);
    }

}
