package com.vini.backend.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private String usn;
    private String studentName;
    private String studentPhone;
    private String studentEmail;
    private String studentBatch;
    private Long projectId;
}
