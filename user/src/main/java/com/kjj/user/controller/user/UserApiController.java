package com.kjj.user.controller.user;

import com.kjj.user.dto.auth.WithdrawDto;
import com.kjj.user.dto.user.UserInfoDto;
import com.kjj.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserApiController {

    private final UserService userService;

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam("username") String username, @RequestBody WithdrawDto dto) {
        userService.withdraw(username, dto);

        return ResponseEntity.ok("탈퇴되었습니다.");
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getInfo(@RequestParam("id") Long id) {

        return ResponseEntity.ok(userService.getInfo(id));
    }
}