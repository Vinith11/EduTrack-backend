package com.vini.backend.controller.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.project.ProjectEvaluation;
import com.vini.backend.service.project.ProjectEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-evaluations")
public class ProjectEvaluationController {

    @Autowired
    private ProjectEvaluationService evaluationService;

    @PostMapping("/create")
    public ResponseEntity<ProjectEvaluation> createEvaluation(@RequestBody ProjectEvaluation evaluation) throws NotFoundException {
        ProjectEvaluation createdEvaluation = evaluationService.createEvaluation(evaluation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }

    @PutMapping("/update/{evaluation_id}")
    public ResponseEntity<ProjectEvaluation> updateEvaluation(@PathVariable Long evaluation_id, @RequestBody ProjectEvaluation evaluation) throws NotFoundException {

            ProjectEvaluation updatedEvaluation = evaluationService.updateEvaluation(evaluation_id, evaluation);
            return ResponseEntity.ok(updatedEvaluation);

    }

    @GetMapping("/{evaluation_id}")
    public ResponseEntity<ProjectEvaluation> getEvaluation(@PathVariable Long evaluation_id) throws NotFoundException {

            ProjectEvaluation evaluation = evaluationService.getEvaluation(evaluation_id);
            return ResponseEntity.ok(evaluation);

    }

    @DeleteMapping("/delete/{evaluation_id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long evaluation_id) throws NotFoundException {
            evaluationService.deleteEvaluation(evaluation_id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{project_id}")
    public ResponseEntity<ProjectEvaluation> getEvaluationByProjectId(@PathVariable Long project_id) throws NotFoundException {
        ProjectEvaluation evaluation = evaluationService.getEvaluationsByProjectId(project_id);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/faculty/{faculty_uid}")
    public ResponseEntity<List<ProjectEvaluation>> getEvaluationByFacultyUid(@PathVariable String faculty_uid) throws NotFoundException {
        List<ProjectEvaluation> evaluation = evaluationService.getEvaluationsByFacultyUid(faculty_uid);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/faculty/{faculty_uid}/batch/{batch_id}")
    public ResponseEntity<List<ProjectEvaluation>> getEvaluationByFacultyUidAndBatchId(@PathVariable String faculty_uid, @PathVariable String batch_id) throws NotFoundException {
        List<ProjectEvaluation> evaluation = evaluationService.getEvaluationByFacultyUidAndBatchId(faculty_uid, batch_id);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/batch/{batch_id}")
    public ResponseEntity<List<ProjectEvaluation>> getProjectEvaluationsByBatch(@PathVariable String batch_id) throws NotFoundException {
        List<ProjectEvaluation> evaluations = evaluationService.getProjectEvaluationsByBatch(batch_id);
        return ResponseEntity.ok(evaluations);
    }

}
