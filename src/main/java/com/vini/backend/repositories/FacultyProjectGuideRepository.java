package com.vini.backend.repositories;

import com.vini.backend.models.project.FacultyProjectGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyProjectGuideRepository extends JpaRepository<FacultyProjectGuide, Long> {
    Long countByFacultyUidAndBatch(String facultyUid, String batch);
    FacultyProjectGuide findFacultyProjectGuideByProjectId(Long projectId);
}
