package com.vini.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDto {
    private String facultyUid;
    private String facultyName;
    private String facultyPhone;

    private String facultyEmail;
}
