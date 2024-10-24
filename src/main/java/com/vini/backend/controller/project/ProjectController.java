package com.vini.backend.controller.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.models.project.Project;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws NotFoundException {

            Project savedProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);

    }

    @PutMapping("/approve/{projectId}")
    public ResponseEntity<Project> approveProject(@PathVariable Long projectId, @RequestParam Boolean approvalStatus) throws NotFoundException {
            Project project = projectService.approveProject(projectId, approvalStatus);
            return ResponseEntity.ok(project);
    }

    @PutMapping("/complete/{projectId}")
    public ResponseEntity<Project> completeProject(@PathVariable Long projectId) throws NotFoundException {
            Project project = projectService.completeProject(projectId);
            return ResponseEntity.ok(project);
    }

    @GetMapping("/pending-projects/{facultyUid}")
    public ResponseEntity<List<Project>> pendingApproveProjects(@PathVariable String facultyUid) throws NotFoundException {
            List<Project> projects = projectService.pendingAproveProjects(facultyUid);
            return ResponseEntity.ok(projects);
    }

    @GetMapping("/faculty-projects/{facultyUid}/{batch}")
    public ResponseEntity<List<Project>> getProjectsByFacultyUidByBatch(@PathVariable String facultyUid, @PathVariable String batch) throws NotFoundException {
            List<Project> projects = projectService.getProjectsByFacultyUidByBatch(facultyUid, batch);
            return ResponseEntity.ok(projects);
    }

    @GetMapping("/{batch}")
    public ResponseEntity<List<Project>> getProjectsByBatch(@PathVariable String batch) throws NotFoundException {
            List<Project> projects = projectService.getProjectsByBatch(batch);
            return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long projectId) throws NotFoundException {
            projectService.deleteProject(projectId);
            ApiResponse apiResponse = new ApiResponse("Project deleted successfully", true);
            return ResponseEntity.ok(apiResponse);
    }
}
