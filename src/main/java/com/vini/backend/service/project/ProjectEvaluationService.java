package com.vini.backend.service.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.project.ProjectEvaluation;

import java.util.List;

public interface ProjectEvaluationService {
    ProjectEvaluation createEvaluation(ProjectEvaluation evaluation) throws NotFoundException;
    ProjectEvaluation updateEvaluation(Long evaluationId, ProjectEvaluation evaluation) throws NotFoundException;
    ProjectEvaluation getEvaluation(Long evaluationId) throws NotFoundException;
    void deleteEvaluation(Long evaluationId) throws NotFoundException;
    ProjectEvaluation getEvaluationsByProjectId(Long projectId) throws NotFoundException;
    List<ProjectEvaluation> getEvaluationsByFacultyUid(String facultyUid) throws NotFoundException;
    List<ProjectEvaluation> getEvaluationByFacultyUidAndBatchId(String facultyUid, String batchId) throws NotFoundException;
    List<ProjectEvaluation> getProjectEvaluationsByBatch(String batch) throws NotFoundException;
}