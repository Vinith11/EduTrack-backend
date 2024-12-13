package com.vini.backend.controller.project;

import com.vini.backend.exception.NotFoundException;
import com.vini.backend.exception.UserException;
import com.vini.backend.models.Faculty;
import com.vini.backend.models.Student;
import com.vini.backend.models.project.Project;
import com.vini.backend.response.ApiResponse;
import com.vini.backend.service.FacultyUserService;
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

    @Autowired
    private FacultyUserService facultyUserService;

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

    @GetMapping("/project-by-id/{projectId}")
    public ResponseEntity<Project> getProjectById(@RequestHeader("Authorization") String jwt, @PathVariable Long projectId) throws NotFoundException, UserException {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/complete/{projectId}")
    public ResponseEntity<Project> completeProject(@PathVariable Long projectId) throws NotFoundException {
            Project project = projectService.completeProject(projectId);
            return ResponseEntity.ok(project);
    }

    @GetMapping("/pending-projects")
    public ResponseEntity<List<Project>> pendingApproveProjects(@RequestHeader("Authorization") String jwt) throws NotFoundException, UserException {
            Faculty faculty = facultyUserService.findUserProfileByJwt(jwt);
            List<Project> projects = projectService.pendingAproveProjects(faculty.getFacultyUid());
            return ResponseEntity.ok(projects);
    }

    @GetMapping("/faculty-projects/{facultyUid}/{batch}")
    public ResponseEntity<List<Project>> getProjectsByFacultyUidByBatch(@PathVariable String facultyUid, @PathVariable String batch) throws NotFoundException {
            List<Project> projects = projectService.getProjectsByFacultyUidByBatch(facultyUid, batch);
            return ResponseEntity.ok(projects);
    }

    @GetMapping("/available-students/{batch}")
    public ResponseEntity<List<Student>> getAvailableStudentsByBatch(@PathVariable String batch) throws NotFoundException {
        List<Student> students = projectService.getAvailableStudentsBatchWise(batch);
        return ResponseEntity.ok(students);
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
