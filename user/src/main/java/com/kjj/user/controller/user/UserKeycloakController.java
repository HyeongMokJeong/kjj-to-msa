package com.kjj.user.controller.user;

import com.kjj.user.dto.user.JoinDto;
import com.kjj.user.dto.user.UserDto;
import com.kjj.user.service.sso.UserKeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserKeycloakController {

    private final UserKeycloakService userKeycloakService;

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
}
