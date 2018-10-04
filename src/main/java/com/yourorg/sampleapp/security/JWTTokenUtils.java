package com.yourorg.sampleapp.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTTokenUtils {
	private static final Logger log = LoggerFactory.getLogger("JWTTokenUtils");
	private static final String TOKEN_SECRET = "your_jwt_secret";
	private static final Algorithm JWT_ALGO = Algorithm.HMAC256(TOKEN_SECRET);

	public static String getToken(String username) {
		String token = null;
		try {
			token = JWT.create().withSubject(username).withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000)).withNotBefore(new Date())
					.sign(JWT_ALGO);
		} catch (JWTCreationException exception) {
			log.error("error generating JWT Token. Exception is---->", exception);
		}
		return token;
	}

	public static String verifyUserToken(String token) {
		String username = null;
		try {
			JWTVerifier verifier = JWT.require(JWT_ALGO).build();
			DecodedJWT jwt = verifier.verify(token);
			username = jwt.getSubject();
		} catch (Exception exception) {
			log.error("error verifying JWT Token. Exception is---->", exception);
		}
		return username;
	}

	public static String getSubject(String token) {
		String username = null;
		try {
			DecodedJWT jwt = JWT.decode(token);
			username = jwt.getSubject();
		} catch (Exception exception) {
			log.error("error verifying JWT Token. Exception is---->", exception);
		}
		return username;
	}
}
