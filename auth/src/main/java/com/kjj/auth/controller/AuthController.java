package com.kjj.auth.controller;

import com.kjj.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/auth"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/check/jwt")
    public ResponseEntity<Boolean> checkJwt(@RequestParam("token") String token) {
        boolean checked = authService.checkJwt(token);
        if (checked) return ResponseEntity.ok(checked);
        else return ResponseEntity.badRequest().body(checked);
    }
}
