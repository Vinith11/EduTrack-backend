package com.vini.backend.controller.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.StudentInternshipFeedback;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.internship.StudentInternshipFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-internship-feedback")
public class StudentInternshipFeedbackController {
    @Autowired
    private StudentInternshipFeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<StudentInternshipFeedback> createFeedback(@RequestBody StudentInternshipFeedback feedback) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.createFeedback(feedback), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentInternshipFeedback> updateFeedback(@PathVariable Long id, @RequestBody StudentInternshipFeedback feedback) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.updateFeedback(id, feedback), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Long id) throws NotFoundException {
        feedbackService.deleteFeedback(id);
        ApiResponse response = new ApiResponse("Feedback deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentInternshipFeedback> getFeedbackById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.getFeedbackById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentInternshipFeedback>> getAllFeedback() {
        return new ResponseEntity<>(feedbackService.getAllFeedback(), HttpStatus.OK);
    }

    @GetMapping("/student/{usn}")
    public ResponseEntity<List<StudentInternshipFeedback>> getFeedbackByStudent(@PathVariable String usn) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.getFeedbackByStudentUsn(usn), HttpStatus.OK);
    }

}
