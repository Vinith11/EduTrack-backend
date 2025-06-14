package com.vini.backend.request;

import com.vini.backend.models.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDTO {

    private Project project;
    private String teamMembers;
}
