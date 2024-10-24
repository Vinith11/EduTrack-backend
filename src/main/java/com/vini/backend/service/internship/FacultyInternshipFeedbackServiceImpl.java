package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.FacultyInternshipFeedback;
import com.vini.backend.repositories.FacultyInternshipFeedbackRepository;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.repositories.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyInternshipFeedbackServiceImpl implements FacultyInternshipFeedbackService {

    @Autowired
    private FacultyInternshipFeedbackRepository feedbackRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    @Override
    public FacultyInternshipFeedback createFeedback(FacultyInternshipFeedback feedback) throws NotFoundException{
        if(facultyRepository.findById(feedback.getFacultyUid()).isEmpty()) {
            throw new NotFoundException("Faculty not found with UID " + feedback.getFacultyUid());
        } else if(internshipRepository.findById(feedback.getInternshipId()).isEmpty()) {
            throw new NotFoundException("Internship not found with ID " + feedback.getInternshipId());
        } else if(feedbackRepository.findByInternshipId(feedback.getInternshipId()) != null) {
            throw new NotFoundException("Feedback already exists with id " + feedback.getInternshipId());
        }
        return feedbackRepository.save(feedback);
    }

    @Override
    public FacultyInternshipFeedback updateFeedback(Long id, FacultyInternshipFeedback feedback) throws NotFoundException{
        if(facultyRepository.findById(feedback.getFacultyUid()).isEmpty()) {
            throw new NotFoundException("Faculty not found with UID " + feedback.getFacultyUid());
        } else if(internshipRepository.findById(feedback.getInternshipId()).isEmpty()) {
            throw new NotFoundException("Internship not found with USN " + feedback.getInternshipId());
        }
        FacultyInternshipFeedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));

        existingFeedback.setFeedbackText(feedback.getFeedbackText());
        existingFeedback.setFeedbackDate(feedback.getFeedbackDate());
        return feedbackRepository.save(existingFeedback);
    }

    @Override
    public void deleteFeedback(Long id) throws NotFoundException{
        feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));
        feedbackRepository.deleteById(id);
    }

    @Override
    public FacultyInternshipFeedback getFeedbackById(Long id) throws NotFoundException{
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found with id " + id));
    }

    @Override
    public List<FacultyInternshipFeedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<FacultyInternshipFeedback> getFeedbackByFaculty(String facultyUid) throws NotFoundException{
        if(facultyRepository.findById(facultyUid).isEmpty()) {
            throw new NotFoundException("Faculty not found with UID " + facultyUid);
        }
        return feedbackRepository.findByFacultyUid(facultyUid);
    }

}
