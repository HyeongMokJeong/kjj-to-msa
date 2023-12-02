package com.kjj.user.controller.user;

import com.kjj.user.dto.user.JoinDto;
import com.kjj.user.dto.user.UserDto;
import com.kjj.user.service.sso.UserKeycloakService;
import com.kjj.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;
    private final UserKeycloakService userKeycloakService;

    // no-auth 서버에서 user정보 조회하기 위해 사용하는 api
    @GetMapping("/info/all")
    public ResponseEntity<UserDto> findByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userKeycloakService.findByUsername(username));
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userKeycloakService.existsByUsername(username));
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody JoinDto dto) {
        return ResponseEntity.ok(userKeycloakService.join(dto));
    }

    @PostMapping("/join/keycloak")
    public ResponseEntity<UserDto> joinKeycloak(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userKeycloakService.joinKeycloak(dto));
    }

    // no-auth 서버에서 탈퇴하기 위해 비밀번호 대조하는 api
    @GetMapping("/check/password")
    public ResponseEntity<Boolean> checkPasswordForWithdraw(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.checkPassword(username, password));
    }
}
