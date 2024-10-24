package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.InternshipEvaluation;
import com.vini.backend.repositories.FacultyRepository;
import com.vini.backend.repositories.InternshipEvaluationRepository;
import com.vini.backend.repositories.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipEvaluationServiceImpl implements InternshipEvaluationService {

    @Autowired
    private InternshipEvaluationRepository evaluationRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public InternshipEvaluation createEvaluation(InternshipEvaluation evaluation) throws NotFoundException {
        validateInternshipAndFaculty(evaluation.getInternshipId(), evaluation.getFacultyUid());
        if (evaluationRepository.findByInternshipId(evaluation.getInternshipId()).isPresent()) {
            throw new IllegalStateException("Evaluation for this internship already exists.");
        }
        return evaluationRepository.save(evaluation);
    }

    @Override
    public InternshipEvaluation updateEvaluation(Long id, InternshipEvaluation evaluation) throws NotFoundException {
        InternshipEvaluation existingEvaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evaluation not found with id " + id));
        validateInternshipAndFaculty(evaluation.getInternshipId(), evaluation.getFacultyUid());
        existingEvaluation.setEvaluationDate(evaluation.getEvaluationDate());
        existingEvaluation.setEvaluationScore(evaluation.getEvaluationScore());
        existingEvaluation.setFacultyEvaluationYear(evaluation.getFacultyEvaluationYear());
        existingEvaluation.setInternshipId(evaluation.getInternshipId());
        existingEvaluation.setFacultyUid(evaluation.getFacultyUid());
        return evaluationRepository.save(existingEvaluation);
    }

    @Override
    public void deleteEvaluation(Long id) throws NotFoundException {
        evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evaluation not found with id " + id));
        evaluationRepository.deleteById(id);
    }

    @Override
    public InternshipEvaluation getEvaluationById(Long id) throws NotFoundException {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evaluation not found with id " + id));
    }

    @Override
    public List<InternshipEvaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Override
    public List<InternshipEvaluation> getEvaluationsByFaculty(String facultyUid) throws NotFoundException {
        if (!facultyRepository.existsById(facultyUid)) {
            throw new NotFoundException("Faculty not found with id " + facultyUid);
        }
        return evaluationRepository.findByFacultyUid(facultyUid);
    }

    private void validateInternshipAndFaculty(Long internshipId, String facultyUid) throws NotFoundException {
        if (!internshipRepository.existsById(internshipId)) {
            throw new NotFoundException("Internship not found with id " + internshipId);
        }
        if (!facultyRepository.existsById(facultyUid)) {
            throw new NotFoundException("Faculty not found with id " + facultyUid);
        }
    }
}
