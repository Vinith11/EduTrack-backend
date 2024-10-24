// src/main/java/com/vini/backend/repositories/ProjectFeedbackRepository.java
package com.vini.backend.repositories;

import com.vini.backend.models.project.ProjectFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFeedbackRepository extends JpaRepository<ProjectFeedback, Long> {
}
