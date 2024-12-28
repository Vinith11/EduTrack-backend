package com.vini.backend.controller.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.Internship;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.response.InternshipDTO;
import com.vini.backend.service.internship.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping("/all")
    public List<Internship> getAllInternships() {
        return internshipService.getAllInternships();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) throws NotFoundException {
        Optional<Internship> internship = internshipService.getInternshipById(id);
        return internship.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Internship> createInternship(@RequestBody InternshipDTO internshipDTO) throws NotFoundException{
        Internship internship = new Internship();
        internship.setStudentUsn(internshipDTO.getStudentUsn());
        internship.setInternshipStart(internshipDTO.getInternshipStart());
        internship.setInternshipEnd(internshipDTO.getInternshipEnd());
        internship.setInternshipLocation(internshipDTO.getInternshipLocation());
        internship.setInternshipDomain(internshipDTO.getInternshipDomain());
        internship.setCompanyName(internshipDTO.getCompanyName());
        internship.setInternshipCompletionCertificateUrl(internshipDTO.getInternshipCompletionCertificateUrl());
        internship.setFacultyUid(internshipDTO.getFacultyUid());

        // Save internship
        Internship createdInternship = internshipService.createInternship(internship);

        return new ResponseEntity<>(createdInternship, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Internship> updateInternship(@PathVariable Long id, @RequestBody Internship internship) throws NotFoundException {
        Internship updatedInternship = internshipService.updateInternship(id, internship);
        if (updatedInternship != null) {
            return new ResponseEntity<>(updatedInternship, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteInternship(@PathVariable Long id) throws NotFoundException{
        Optional<Internship>  internship = internshipService.getInternshipById(id);
        internshipService.deleteInternship(id);
        ApiResponse res = new ApiResponse("Internship Deleted Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // New API endpoint to get internships by student USN
    @GetMapping("/student/{studentUsn}")
    public ResponseEntity<List<Internship>> getInternshipsByStudentUsn(@PathVariable String studentUsn) throws NotFoundException {
        List<Internship> internships = internshipService.getInternshipsByStudentUsn(studentUsn);
        if(internships.isEmpty()){
            return new ResponseEntity<>(internships, HttpStatus.OK);
        }
        return new ResponseEntity<>(internships, HttpStatus.OK);
    }

    @GetMapping("all-by-batch/{batch}")
    public ResponseEntity<List<Internship>> getInternshipsByBatch(@PathVariable String batch) throws NotFoundException {
        List<Internship> internships = internshipService.getInternshipsByBatch(batch);

        return ResponseEntity.ok(internships);
    }
}
