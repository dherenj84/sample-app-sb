package com.yourorg.sampleapp.security;

import org.springframework.stereotype.Service;

@Service
public class JWTAuthProvider implements TokenAuthProvider {

	public String getToken(String username) {
		return JWTTokenUtils.getToken(username);
	}

	public String verifyUserToken(String token) {
		return JWTTokenUtils.verifyUserToken(token);
	}
}
