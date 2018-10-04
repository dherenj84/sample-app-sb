package com.yourorg.sampleapp.security;

import com.yourorg.sampleapp.core.User;
import com.yourorg.sampleapp.model.AuthenticationRequest;

public interface AuthProvider {
	public User authenticate(AuthenticationRequest request);
}
