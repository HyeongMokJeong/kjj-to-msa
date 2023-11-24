package com.kjj.auth.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtTools {
    
    private final JwtTemplate jwtTemplate = new JwtTemplate();
    private static final String USERNAME = "username";

    public String getUsernameFromToken(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        return JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim(USERNAME).asString();
    }
}