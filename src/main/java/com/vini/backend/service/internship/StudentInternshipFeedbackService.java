package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.StudentInternshipFeedback;

import java.util.List;

public interface StudentInternshipFeedbackService {
    StudentInternshipFeedback createFeedback(StudentInternshipFeedback feedback) throws NotFoundException;
    StudentInternshipFeedback updateFeedback(Long id, StudentInternshipFeedback feedback) throws NotFoundException;
    void deleteFeedback(Long id) throws NotFoundException;
    StudentInternshipFeedback getFeedbackById(Long id) throws NotFoundException;
    List<StudentInternshipFeedback> getAllFeedback();
    List<StudentInternshipFeedback> getFeedbackByStudentUsn(String facultyUid) throws NotFoundException;

}
