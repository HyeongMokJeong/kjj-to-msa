package com.kjj.noauth.util;

import com.kjj.noauth.dto.user.UserDto;
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

    public void setTokenToHttpServletResponse(HttpServletResponse response, UserDto userDto) {
        String accessToken = jwtTools.createToken(userDto);
        String refreshToken = jwtTools.createRefreshToken(userDto);

        response.setHeader(jwtTemplate.getHeaderString(), jwtTemplate.getTokenPrefix() + accessToken);
        response.setHeader(jwtTemplate.getRefreshHeaderString(), jwtTemplate.getTokenPrefix() + refreshToken);
    }
}
