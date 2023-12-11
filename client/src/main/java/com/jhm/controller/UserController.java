package com.jhm.controller;

import com.jhm.client.MenuClient;
import com.jhm.client.UserClient;
import com.jhm.dto.user.JoinDto;
import com.jhm.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/client/user")
@RequiredArgsConstructor
public class UserController {
    private final UserClient userClient;

    // 유저 삭제를 위해 사용하는 API
    @DeleteMapping("/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestParam("username") String username) {
        return ResponseEntity.ok(userClient.withdraw(username));
    }

    // user정보 조회하기 위해 사용하는 api
    @GetMapping("/info/all")
    public ResponseEntity<UserDto> findByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userClient.findByUsername(username));
    }

    // 탈퇴하기 위해 비밀번호 대조하는 api
    @GetMapping("/check/password")
    public ResponseEntity<Boolean> checkPasswordForWithdraw(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return ResponseEntity.ok(userClient.checkPassword(username, password));
    }

    // 메뉴가 삭제되었을 때 설정되어있던 정책을 null로 바꾸는 api
    @PatchMapping("/policy/menu-del")
    public void clearPolicyByDeletedMenu(@RequestParam("id") Long menuId) {
        userClient.clearPolicyByDeletedMenu(menuId);
    }

    // 유저 아이디 중복 확인하는 api
    @GetMapping("/check")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userClient.existsByUsername(username));
    }

    // 자체 회원가입하는 api
    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody JoinDto dto) {
        return ResponseEntity.ok(userClient.join(dto));
    }

    // keycloak 회원가입하는 api
    @PostMapping("/join/keycloak")
    public ResponseEntity<UserDto> joinKeycloak(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userClient.joinKeycloak(dto));
    }
}
