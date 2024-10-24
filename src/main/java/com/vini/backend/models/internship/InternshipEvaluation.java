// src/main/java/com/vini/backend/models/InternshipEvaluation.java
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
public class InternshipEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationId;
    private LocalDate evaluationDate;
    private Integer evaluationScore;
    private String facultyEvaluationYear;

    private Long internshipId;

    private String facultyUid;
}

