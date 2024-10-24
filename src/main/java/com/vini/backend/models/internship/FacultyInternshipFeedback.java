// src/main/java/com/vini/backend/models/InternshipFeedback.java
package com.vini.backend.models.internship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyInternshipFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    private String feedbackText;
    private LocalDate feedbackDate;

    private Long internshipId;

    private String facultyUid;
}
