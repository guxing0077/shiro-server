package com.lee.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtHelper {

	private static final String SECRET = "b0f8b49f22c718e9924f5b1165111a67";

	private static final String ISSUER = "lee";

	private final static Logger logger = LoggerFactory.getLogger(JwtHelper.class);

	public static String genToken(Map<String, String> claims) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER)
					.withExpiresAt(DateUtils.addDays(new Date(), 1));
			claims.forEach((k, v) -> builder.withClaim(k, v));
			return builder.sign(algorithm).toString();
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, String> verifyToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
			DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> map = jwt.getClaims();
			Map<String, String> resultMap = Maps.newHashMap();
			map.forEach((k, v) -> resultMap.put(k, v.asString()));
			return resultMap;
		} catch (Exception e) {
			return new HashMap<>(0);
		}
	}

}
