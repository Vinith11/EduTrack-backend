package com.vini.backend.service.internship;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.internship.Internship;

import java.util.List;
import java.util.Optional;

public interface InternshipService {
    List<Internship> getAllInternships();
    Optional<Internship> getInternshipById(Long internshipId) throws NotFoundException;
    Internship createInternship(Internship internship) throws NotFoundException ;
    Internship updateInternship(Long internshipId, Internship internship) throws NotFoundException;
    void deleteInternship(Long internshipId) throws NotFoundException;
    List<Internship> getInternshipsByStudentUsn(String studentUsn) throws NotFoundException;
}
