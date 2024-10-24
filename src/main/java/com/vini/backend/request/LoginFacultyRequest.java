package com.vini.backend.request;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFacultyRequest {
	private String email;
	private String password;
}
