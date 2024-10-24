// src/main/java/com/vini/backend/repositories/ProjectEvaluationRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.project.ProjectEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEvaluationRepository extends JpaRepository<ProjectEvaluation, Long> {
    boolean existsByProjectIdAndFacultyUid(Long projectId, String facultyUid);
    ProjectEvaluation findByProjectId(Long projectId);
    boolean existsByFacultyUid(String facultyUid);
    List<ProjectEvaluation> findByFacultyUid(String facultyUid);

    @Query("SELECT pe FROM ProjectEvaluation pe JOIN Project p ON pe.projectId = p.projectId WHERE pe.facultyUid = :facultyUid AND p.academicYear = :batch")
    List<ProjectEvaluation> findEvaluationsByFacultyAndBatch(@Param("facultyUid") String facultyUid, @Param("batch") String batch);

    @Query("SELECT pe FROM ProjectEvaluation pe JOIN Project p ON pe.projectId = p.projectId WHERE p.academicYear = :batch")
    List<ProjectEvaluation> findProjectEvaluationsByBatch(@Param("batch") String batch);

}
