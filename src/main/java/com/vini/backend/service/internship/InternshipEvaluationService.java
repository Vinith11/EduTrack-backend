package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.InternshipEvaluation;

import java.util.List;

public interface InternshipEvaluationService {
    InternshipEvaluation createEvaluation(InternshipEvaluation evaluation) throws NotFoundException;
    InternshipEvaluation updateEvaluation(Long id, InternshipEvaluation evaluation) throws NotFoundException;
    void deleteEvaluation(Long id) throws NotFoundException;
    InternshipEvaluation getEvaluationById(Long id) throws NotFoundException;
    List<InternshipEvaluation> getAllEvaluations();
    List<InternshipEvaluation> getEvaluationsByFaculty(String facultyUid) throws NotFoundException;
}
