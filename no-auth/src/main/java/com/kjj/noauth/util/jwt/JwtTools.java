package com.kjj.noauth.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kjj.noauth.entity.User;
import com.kjj.noauth.exception.JwtRefreshException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTools {
    
    private final JwtTemplate jwtTemplate = new JwtTemplate();
    private static final String USERNAME = "username";

    public String createToken(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(jwtTemplate.getExpiration());
        return JWT.create()
                .withSubject(jwtTemplate.getTokenPrefix())
                .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("id", user.getId())
                .withClaim(USERNAME, user.getUsername())
                .withClaim("roles", user.getRoles())
                .sign(Algorithm.HMAC256(jwtTemplate.getSecret()));
    }

    public String createRefreshToken(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(jwtTemplate.getExpiration());
        return JWT.create()
                .withSubject(jwtTemplate.getTokenPrefix())
                .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim(USERNAME, user.getUsername())
                .withClaim("type", jwtTemplate.getRefreshType())
                .sign(Algorithm.HMAC256(jwtTemplate.getSecret()));
    }

    public String getUsernameFromToken(String token) {
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        return JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim(USERNAME).asString();
    }

    public String getTypeFromRefreshToken(String token){
        token = token.replace(jwtTemplate.getTokenPrefix(), "");

        return JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim("type").asString();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date exp = JWT.require(Algorithm.HMAC256(jwtTemplate.getSecret())).build().verify(token).getClaim("exp").asDate();
            return exp.before(new Date());
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    public String getUsernameFromRefreshToken(String token) {
        String data = getUsernameFromToken(token);
        if (data == null) throw new JwtRefreshException("username이 null 입니다.");
        return data;
    }
}