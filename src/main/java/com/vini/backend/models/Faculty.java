package com.vini.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    private String facultyUid;
    private String facultyName;
    private String facultyPhone;

    private String facultyEmail;
    private String facultyRole;

    private String facultyPassword;
}

