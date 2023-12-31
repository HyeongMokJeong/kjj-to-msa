package com.kjj.noauth.util.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTemplate {
    public String getSecret() { return "capston2023kjj";}

    public int getExpiration() {
        return 30;
    }

    public String getTokenPrefix() {
        return "Bearer ";
    }

    public String getHeaderString() {
        return "Authorization";
    }

    public String getRefreshHeaderString() {
        return "Refresh_Token";
    }

    public String getRefreshType() { return "Refresh"; }
}
