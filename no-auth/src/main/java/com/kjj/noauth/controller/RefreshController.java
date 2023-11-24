package com.kjj.noauth.controller;

import com.kjj.noauth.dto.jwt.TokenRefreshResponseDto;
import com.kjj.noauth.exception.JwtRefreshException;
import com.kjj.noauth.service.AuthService;
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

    @PostMapping
    public ResponseEntity<TokenRefreshResponseDto> refreshJwt(HttpServletResponse response, @RequestParam("refresh-token") String refreshToken) {
        try {
            return ResponseEntity.ok(authService.refreshJwt(response, refreshToken));
        } catch (JwtRefreshException e) {
            return ResponseEntity.badRequest().body(TokenRefreshResponseDto.refreshFail(e));
        }
    }
}
