package com.yourorg.sampleapp.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.yourorg.sampleapp.security.JWTTokenUtils;

@RequestMapping("/secure/api")
public class SecureBaseController {
	protected String getLoggedInUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		return JWTTokenUtils.getSubject(token);
	}
}
