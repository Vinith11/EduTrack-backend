package com.vini.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String usn;
    private String studentName;
    private String studentPhone;
    private String studentEmail;
    private String studentBatch;

    private String studentPassword;

    private Long projectId;

    private boolean isLeader; // New field to indicate if the student is a leader
}
