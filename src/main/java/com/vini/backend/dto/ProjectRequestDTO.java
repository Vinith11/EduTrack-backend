package com.vini.backend.dto;

import com.vini.backend.models.project.Project;

import java.util.List;

public class ProjectRequestDTO {

    private Project project;
    private List<String> teamMembers;

    // Default constructor
    public ProjectRequestDTO() {
    }

    // Parameterized constructor
    public ProjectRequestDTO(Project project, List<String> teamMembers) {
        this.project = project;
        this.teamMembers = teamMembers;
    }

    // Getters and Setters
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
