package com.vini.backend.service.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.project.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project) throws NotFoundException;
    Project approveProject(Long projectId, Boolean approvalStatus) throws NotFoundException;
    Project completeProject(Long projectId) throws NotFoundException;
    List<Project> pendingAproveProjects(String facultyUid) throws NotFoundException;
    List<Project> getProjectsByFacultyUidByBatch(String facultyUid, String batch) throws NotFoundException;
    List<Project> getProjectsByBatch(String batch) throws NotFoundException;
    void deleteProject(Long projectId) throws NotFoundException;
}
