package com.yourorg.sampleapp.security;

public interface TokenAuthProvider {
	public String getToken(String username);

	public String verifyUserToken(String token);
}
