package com.vini.backend.controller.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.InternshipEvaluation;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.internship.InternshipEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship-evaluation")
public class InternshipEvaluationController {

    @Autowired

    private InternshipEvaluationService evaluationService;

    @PostMapping("/create")
    public ResponseEntity<InternshipEvaluation> createEvaluation(@RequestBody InternshipEvaluation evaluation) throws NotFoundException {
        return new ResponseEntity<>(evaluationService.createEvaluation(evaluation), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InternshipEvaluation> updateEvaluation(@PathVariable Long id, @RequestBody InternshipEvaluation evaluation) throws NotFoundException {
        return new ResponseEntity<>(evaluationService.updateEvaluation(id, evaluation), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEvaluation(@PathVariable Long id) throws NotFoundException {
        evaluationService.deleteEvaluation(id);
        ApiResponse response = new ApiResponse("Evaluation deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipEvaluation> getEvaluationById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(evaluationService.getEvaluationById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InternshipEvaluation>> getAllEvaluations() {
        return new ResponseEntity<>(evaluationService.getAllEvaluations(), HttpStatus.OK);
    }

    @GetMapping("/faculty/{facultyUid}")
    public ResponseEntity<List<InternshipEvaluation>> getEvaluationsByFaculty(@PathVariable String facultyUid) throws NotFoundException {
        return new ResponseEntity<>(evaluationService.getEvaluationsByFaculty(facultyUid), HttpStatus.OK);
    }
}
