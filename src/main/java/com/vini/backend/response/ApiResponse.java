package com.vini.backend.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private boolean status;
}
