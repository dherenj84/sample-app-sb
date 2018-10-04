package com.yourorg.sampleapp.model;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String message;
	private Object data;
}
