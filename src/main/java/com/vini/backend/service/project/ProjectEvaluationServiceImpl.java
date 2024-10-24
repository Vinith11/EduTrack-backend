package com.vini.backend.service.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.project.ProjectEvaluation;
import com.vini.backend.repositories.ProjectEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectEvaluationServiceImpl implements ProjectEvaluationService {

    @Autowired
    private ProjectEvaluationRepository evaluationRepository;

    @Override
    public ProjectEvaluation createEvaluation(ProjectEvaluation evaluation) throws NotFoundException {
        if(evaluation.getProjectId() == null) {
            throw new NotFoundException("Project not found");
        } else if (evaluation.getFacultyUid() == null) {
            throw new NotFoundException("Faculty not found");
        } else if(evaluation.getEvaluationScore() < 0 || evaluation.getEvaluationScore() > 100) {
            throw new NotFoundException("Invalid evaluation score");
        } else if(evaluationRepository.existsByProjectIdAndFacultyUid(evaluation.getProjectId(), evaluation.getFacultyUid())) {
            throw new NotFoundException("Evaluation already exists");
        }

        return evaluationRepository.save(evaluation);
    }

    @Override
    public ProjectEvaluation updateEvaluation(Long evaluationId, ProjectEvaluation evaluation) throws NotFoundException {
        ProjectEvaluation existingEvaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new NotFoundException("Evaluation not found"));
        if (existingEvaluation.getFacultyUid() == null) {
            throw new NotFoundException("Faculty not found");
        } else if(existingEvaluation.getEvaluationScore() < 0 || evaluation.getEvaluationScore() > 100) {
            throw new NotFoundException("Invalid evaluation score");
        }
        existingEvaluation.setEvaluationDate(evaluation.getEvaluationDate());
        existingEvaluation.setEvaluationScore(evaluation.getEvaluationScore());
        existingEvaluation.setProjectId(evaluation.getProjectId());
        existingEvaluation.setFacultyUid(evaluation.getFacultyUid());
        return evaluationRepository.save(existingEvaluation);
    }

    @Override
    public ProjectEvaluation getEvaluation(Long evaluationId) throws NotFoundException {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new NotFoundException("Evaluation not found"));
    }

    @Override
    public void deleteEvaluation(Long evaluationId) throws NotFoundException {
        ProjectEvaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new NotFoundException("Evaluation not found"));
        evaluationRepository.delete(evaluation);
    }

    @Override
    public ProjectEvaluation getEvaluationsByProjectId(Long projectId) throws NotFoundException {
        ProjectEvaluation evaluation = evaluationRepository.findByProjectId(projectId);
        if (evaluation == null) {
            throw new NotFoundException("Evaluation not found");
        }
        return evaluation;
    }

    @Override
    public List<ProjectEvaluation> getEvaluationsByFacultyUid(String facultyUid) throws NotFoundException {
        if (!evaluationRepository.existsByFacultyUid(facultyUid)) {
            throw new NotFoundException("Faculty not found");
        }
        return evaluationRepository.findByFacultyUid(facultyUid);
    }

    @Override
    public List<ProjectEvaluation> getEvaluationByFacultyUidAndBatchId(String facultyUid, String batchId) throws NotFoundException {
        if (!evaluationRepository.existsByFacultyUid(facultyUid)) {
            throw new NotFoundException("Faculty not found");
        }
        if (!batchId.matches("\\d{4}")) {
            throw new NotFoundException("Wrong year format. Please provide the year in YYYY format.");
        }
        return evaluationRepository.findEvaluationsByFacultyAndBatch(facultyUid, batchId);
    }

    @Override
    public List<ProjectEvaluation> getProjectEvaluationsByBatch(String batch) throws NotFoundException {
        if (!batch.matches("\\d{4}")) {
            throw new NotFoundException("Wrong year format. Please provide the year in YYYY format.");
        }
        return evaluationRepository.findProjectEvaluationsByBatch(batch);
    }

}


