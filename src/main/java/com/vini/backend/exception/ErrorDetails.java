package com.vini.backend.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	
	private String error;
	private String details;
	private LocalDateTime timestamp;

}
