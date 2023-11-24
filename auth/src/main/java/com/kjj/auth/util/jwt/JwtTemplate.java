package com.kjj.auth.util.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTemplate {
    public String getSecret() { return "capston2023kjj";}

    public String getTokenPrefix() {
        return "Bearer ";
    }
}
