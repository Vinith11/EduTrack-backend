package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.FacultyInternshipFeedback;

import java.util.List;

public interface FacultyInternshipFeedbackService {
    FacultyInternshipFeedback createFeedback(FacultyInternshipFeedback feedback) throws NotFoundException;
    FacultyInternshipFeedback updateFeedback(Long id, FacultyInternshipFeedback feedback) throws NotFoundException;
    void deleteFeedback(Long id) throws NotFoundException;
    FacultyInternshipFeedback getFeedbackById(Long id) throws NotFoundException;
    List<FacultyInternshipFeedback> getAllFeedback();
    List<FacultyInternshipFeedback> getFeedbackByFaculty(String facultyUid) throws NotFoundException;
}
