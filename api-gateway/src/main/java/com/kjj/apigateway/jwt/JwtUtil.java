package com.kjj.apigateway.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    
    private final JwtTemplate jwtTemplate;

    public String getUsernameFromToken(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        return JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim(jwtTemplate.getClaimUsername()).asString();
    }

    public boolean isTokenExpired(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");
        try {
            Date exp = JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim("exp").asDate();
            return exp.before(new Date());
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    public String getIdFromToken(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        Long originId = JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim(jwtTemplate.getClaimId()).asLong();
        return String.valueOf(originId);
    }

    public String getRolesFromToken(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        return JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim(jwtTemplate.getClaimRoles()).asString();
    }
}