package com.vini.backend.repositories;

import com.vini.backend.models.project.FacultyProjectGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyProjectGuideRepository extends JpaRepository<FacultyProjectGuide, Long> {
    Long countByFacultyUidAndBatch(String facultyUid, String batch);
    FacultyProjectGuide findFacultyProjectGuideByProjectId(Long projectId);


    @Query("SELECT f.facultyUid FROM FacultyProjectGuide f " +
            "WHERE f.batch = :batch " +
            "GROUP BY f.facultyUid " +
            "HAVING COUNT(f.facultyUid) < 3")
    List<String> findFacultyUidsWithLessThanThreeAssignments(@Param("batch") String batch);

    List<FacultyProjectGuide> findByBatch(String batch);

}
