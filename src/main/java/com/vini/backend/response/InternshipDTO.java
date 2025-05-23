package com.vini.backend.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipDTO {
    private String studentUsn;
    private LocalDate internshipStart;
    private LocalDate internshipEnd;
    private String internshipDuration; // Calculated in the service
    private String internshipLocation;
    private String internshipDomain;
    private String companyName;
    private String internshipCompletionCertificateUrl;
    private String facultyUid;
}
