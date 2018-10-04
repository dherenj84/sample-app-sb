package com.yourorg.sampleapp.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecureApiInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(SecureApiInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("authorizing secure api request");
		if (request.getMethod().equals(HttpMethod.OPTIONS.name()))
			return true;
		if (request.getHeader("Authorization") == null || !request.getHeader("Authorization").startsWith("Bearer")
				|| JWTTokenUtils.verifyUserToken(request.getHeader("Authorization").substring(7)) == null) {
			JSONObject body = new JSONObject();
			body.put("status", HttpServletResponse.SC_FORBIDDEN);
			body.put("message", "Access to this resource is forbidden. Not authenticated!");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("application/json");
			response.getWriter().append(body.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return false;
		}
		return true;
	}
}
