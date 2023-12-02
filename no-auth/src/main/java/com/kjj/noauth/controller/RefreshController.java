package com.kjj.noauth.controller;

import com.kjj.noauth.dto.jwt.TokenRefreshResponseDto;
import com.kjj.noauth.exception.JwtRefreshException;
import com.kjj.noauth.service.AuthService;
import com.kjj.noauth.util.jwt.JwtTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/no-auth/refresh"})
@RequiredArgsConstructor
public class RefreshController {

    private final AuthService authService;
    private final JwtTemplate jwtTemplate;

    @PostMapping
    public ResponseEntity<TokenRefreshResponseDto> refreshJwt(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(jwtTemplate.getHeaderString());
        refreshToken = refreshToken.replace(jwtTemplate.getTokenPrefix(), "");
        try {
            return ResponseEntity.ok(authService.refreshJwt(response, refreshToken));
        } catch (JwtRefreshException e) {
            return ResponseEntity.badRequest().body(TokenRefreshResponseDto.refreshFail(e));
        }
    }
}
