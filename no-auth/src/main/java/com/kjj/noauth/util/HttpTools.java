package com.kjj.noauth.util;

import com.kjj.noauth.entity.User;
import com.kjj.noauth.util.jwt.JwtTemplate;
import com.kjj.noauth.util.jwt.JwtTools;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpTools {
    private final JwtTemplate jwtTemplate;
    private final JwtTools jwtTools;

    public void setTokenToHttpServletResponse(HttpServletResponse response, User user) {
        String accessToken = jwtTools.createToken(user);
        String refreshToken = jwtTools.createRefreshToken(user);

        response.setHeader(jwtTemplate.getHeaderString(), jwtTemplate.getTokenPrefix() + accessToken);
        response.setHeader(jwtTemplate.getRefreshHeaderString(), jwtTemplate.getTokenPrefix() + refreshToken);
    }
}
