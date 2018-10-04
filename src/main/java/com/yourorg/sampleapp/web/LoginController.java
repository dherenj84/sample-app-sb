package com.yourorg.sampleapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourorg.sampleapp.core.User;
import com.yourorg.sampleapp.model.AuthenticationRequest;
import com.yourorg.sampleapp.model.AuthenticationResponse;
import com.yourorg.sampleapp.security.AuthProvider;
import com.yourorg.sampleapp.security.TokenAuthProvider;

@RestController
public class LoginController extends BaseController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AuthProvider authProvider;

	@Autowired
	TokenAuthProvider tokenAuthProvider;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		AuthenticationResponse body = new AuthenticationResponse();
		try {
			User user = authProvider.authenticate(request);
			user.setToken(tokenAuthProvider.getToken(user.getUsername()));
			body.setData(user);
			body.setMessage("success");
		} catch (Exception e) {
			log.error("error authenticating user. Exception is---->", e);
		}
		return new ResponseEntity<AuthenticationResponse>(body, HttpStatus.OK);
	}
}
