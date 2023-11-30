package com.kjj.noauth.controller;

import com.kjj.noauth.dto.user.JoinDto;
import com.kjj.noauth.exception.WrongRequestBodyException;
import com.kjj.noauth.service.KeycloakService;
import com.kjj.noauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/v1/no-auth/join"})
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;
    private final KeycloakService keycloakService;

    @PostMapping
    public ResponseEntity<Boolean> join(@RequestBody JoinDto dto) {
        if (!dto.checkForm()) throw new WrongRequestBodyException("""
                데이터 형식이 올바르지 않습니다.
                dto : """ + dto);

        return ResponseEntity.ok(userService.join(dto));
    }

    @PostMapping("/keycloak")
    public ResponseEntity<String> joinToKeycloakServer(@RequestBody JoinDto dto) {
        if (!dto.checkForm()) throw new WrongRequestBodyException("""
                데이터 형식이 올바르지 않습니다.
                dto : """ + dto);
        keycloakService.joinKeycloakServer(dto);

        return ResponseEntity.ok("Keyclock 회원가입에 성공했습니다.");
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkLoginId(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.checkLoginId(username));
    }
}
