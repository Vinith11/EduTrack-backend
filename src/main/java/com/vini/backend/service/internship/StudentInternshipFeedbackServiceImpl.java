package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.StudentInternshipFeedback;
import com.vini.backend.repositories.InternshipRepository;
import com.vini.backend.repositories.StudentInternshipFeedbackRepository;
import com.vini.backend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInternshipFeedbackServiceImpl implements StudentInternshipFeedbackService {

    @Autowired
    private StudentInternshipFeedbackRepository feedbackRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public StudentInternshipFeedback createFeedback(StudentInternshipFeedback feedback) throws NotFoundException {
        if(studentRepository.findById(feedback.getUsn()).isEmpty()) {
            throw new NotFoundException("Student not found with USN " + feedback.getUsn());
        } else if(internshipRepository.findById(feedback.getInternshipId()).isEmpty()) {
            throw new NotFoundException("Internship not found with ID " + feedback.getInternshipId());
        } else if(feedbackRepository.findByInternshipId(feedback.getInternshipId()) != null) {
            throw new NotFoundException("Feedback already exists with id " + feedback.getInternshipId());
        } else if(!internshipRepository.findById(feedback.getInternshipId()).get().getStudentUsn().equals(feedback.getUsn())) {
            throw new NotFoundException("Student cannot give feedback to this ID " + feedback.getInternshipId());
        }
        return feedbackRepository.save(feedback);
    }

    @Override
    public StudentInternshipFeedback updateFeedback(Long id, StudentInternshipFeedback feedback) throws NotFoundException {
        if(studentRepository.findById(feedback.getUsn()).isEmpty()) {
            throw new NotFoundException("Student not found with USN " + feedback.getUsn());
        } else if(internshipRepository.findById(feedback.getInternshipId()).isEmpty()) {
            throw new NotFoundException("Internship not found with USN " + feedback.getInternshipId());
        }
        StudentInternshipFeedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));

        existingFeedback.setFeedbackText(feedback.getFeedbackText());
        existingFeedback.setFeedbackDate(feedback.getFeedbackDate());
        return feedbackRepository.save(existingFeedback);

    }

    @Override
    public void deleteFeedback(Long id) throws NotFoundException {
        feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));
        feedbackRepository.deleteById(id);

    }

    @Override
    public StudentInternshipFeedback getFeedbackById(Long id) throws NotFoundException {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));
    }

    @Override
    public List<StudentInternshipFeedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<StudentInternshipFeedback> getFeedbackByStudentUsn(String facultyUid) throws NotFoundException {
        if(studentRepository.findById(facultyUid).isEmpty()) {
            throw new NotFoundException("Student not found with USN " + facultyUid);
        }
        return feedbackRepository.findByUsn(facultyUid);
    }
}
