package com.kjj.noauth.controller;

import com.kjj.noauth.dto.HttpResponse;
import com.kjj.noauth.dto.user.WithdrawDto;
import com.kjj.noauth.service.KeycloakService;
import com.kjj.noauth.service.UserService;
import com.kjj.noauth.util.jwt.JwtTemplate;
import com.kjj.noauth.util.jwt.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/no-auth/withdraw"})
@RequiredArgsConstructor
public class WithdrawController {

    private final JwtTemplate jwtTemplate;
    private final JwtTools jwtTools;
    private final UserService userService;
    private final KeycloakService keycloakService;

    @DeleteMapping
    public ResponseEntity<HttpResponse<Boolean>> withdraw(HttpServletRequest request, @RequestBody WithdrawDto dto) {
        String token = request.getHeader(jwtTemplate.getHeaderString());
        String username = jwtTools.getUsernameFromToken(token);

        Boolean result = userService.withdraw(username, dto);
        if (result) {
            return ResponseEntity.ok(new HttpResponse<>(result, "회원탈퇴에 성공했습니다."));
        } else {
            return ResponseEntity.badRequest().body(new HttpResponse<>(result, "회원탈퇴에 실패했습니다."));
        }
    }

    @DeleteMapping("/keycloak")
    public ResponseEntity<HttpResponse<Boolean>> withdrawKeycloak(HttpServletRequest request) {
        String token = request.getHeader(jwtTemplate.getHeaderString());
        String username = jwtTools.getUsernameFromToken(token);
        Boolean result = keycloakService.withdrawSso(username);

        if (result) {
            return ResponseEntity.ok(new HttpResponse<>(result, "회원탈퇴에 성공했습니다."));
        } else {
            return ResponseEntity.badRequest().body(new HttpResponse<>(result, "회원탈퇴에 실패했습니다."));
        }
    }
}
