package com.kjj.user.controller.user;

import com.kjj.user.dto.user.UserDto;
import com.kjj.user.service.sso.UserKeycloakService;
import com.kjj.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class ForClientUserController {

    private final UserService userService;
    private final UserKeycloakService userKeycloakService;

    // no-auth 서버에서 탈퇴요청 수신 시 유저 삭제를 위해 사용하는 API
    @DeleteMapping("/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.withdraw(username));
    }

    // no-auth 서버에서 user정보 조회하기 위해 사용하는 api
    @GetMapping("/info/all")
    public ResponseEntity<UserDto> findByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userKeycloakService.findByUsername(username));
    }

    // no-auth 서버에서 탈퇴하기 위해 비밀번호 대조하는 api
    @GetMapping("/check/password")
    public ResponseEntity<Boolean> checkPasswordForWithdraw(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.checkPassword(username, password));
    }

    // menu 서버에서 메뉴가 삭제되었을 때 설정되어있던 정책을 null로 바꾸는 api
    @PatchMapping("/policy/menu-del")
    public void clearPolicyByDeletedMenu(@RequestParam("id") Long menuId) {
        userService.clearPolicyByDeletedMenu(menuId);
    }
}