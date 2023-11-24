package com.kjj.noauth.controller;

import com.kjj.noauth.dto.user.LoginDto;
import com.kjj.noauth.dto.user.UserInfoDto;
import com.kjj.noauth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/v1/no-auth/login"})
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping("/id")
    public ResponseEntity<UserInfoDto> idLogin(HttpServletResponse response, @RequestBody LoginDto dto) {
        if (!dto.checkForm()) return ResponseEntity.badRequest().body(UserInfoDto.loginFail("""
                데이터가 충분하지 않습니다.
                필드가 비어있거나 null 값이 담겨있습니다.
                dto : """ + dto));

        return ResponseEntity.ok(authService.defaultLogin(response, dto));
    }

    @PostMapping("/keycloak")
    public ResponseEntity<UserInfoDto> keycloakLogin(HttpServletResponse response, @RequestParam("token") String keycloakToken) {
        return ResponseEntity.ok(authService.keycloakLogin(response, keycloakToken));
    }
}
