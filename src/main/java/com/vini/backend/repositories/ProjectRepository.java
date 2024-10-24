package com.vini.backend.repositories;

import com.vini.backend.models.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStudentProjectGuideIdAndFacultyApprovalStatus(String facultyUid, Boolean approvalStatus);

    List<Project> findByStudentProjectGuideIdAndAcademicYearAndFacultyApprovalStatus(String facultyUid, String academicYear, boolean approvalStatus);

    List<Project> findByAcademicYearAndFacultyApprovalStatus(String academicYear, boolean approvalStatus);

    boolean existsByStudentProjectLeaderId(String studentId);

    boolean existsByTeamMembersContaining(String studentId);
}