package com.vini.backend.controller.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.FacultyInternshipFeedback;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.internship.FacultyInternshipFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty-internship-feedback")
public class FacultyInternshipFeedbackController {

    @Autowired
    private FacultyInternshipFeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<FacultyInternshipFeedback> createFeedback(@RequestBody FacultyInternshipFeedback feedback) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.createFeedback(feedback), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FacultyInternshipFeedback> updateFeedback(@PathVariable Long id, @RequestBody FacultyInternshipFeedback feedback) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.updateFeedback(id, feedback), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Long id) throws NotFoundException {
        feedbackService.deleteFeedback(id);
        ApiResponse response = new ApiResponse("Feedback deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyInternshipFeedback> getFeedbackById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.getFeedbackById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FacultyInternshipFeedback>> getAllFeedback() {
        return new ResponseEntity<>(feedbackService.getAllFeedback(), HttpStatus.OK);
    }

    @GetMapping("/faculty/{facultyUid}")
    public ResponseEntity<List<FacultyInternshipFeedback>> getFeedbackByFaculty(@PathVariable String facultyUid) throws NotFoundException {
        return new ResponseEntity<>(feedbackService.getFeedbackByFaculty(facultyUid), HttpStatus.OK);
    }
}
