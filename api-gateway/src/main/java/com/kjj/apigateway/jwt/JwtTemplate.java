package com.kjj.apigateway.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTemplate {
    public String getSecret() { return "capston2023kjj";}
    public String getTokenPrefix() {
        return "Bearer ";
    }

    public String getClaimUsername() { return "username"; }
    public String getClaimRoles() { return "roles"; }
    public String getClaimId() { return "id"; }
}
