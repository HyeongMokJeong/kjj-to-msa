package com.kjj.user.controller.user;

import com.kjj.user.dto.user.UserInfoDto;
import com.kjj.user.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserApiController {

    private final UserService userService;

    // no-auth 서버에서 탈퇴요청 수신 시 유저 삭제를 위해 사용하는 API
    @DeleteMapping("/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.withdraw(username));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getInfo(@RequestParam("id") Long id) {

        return ResponseEntity.ok(userService.getInfo(id));
    }
}