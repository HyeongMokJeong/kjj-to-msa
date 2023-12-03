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

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getInfo(@RequestParam("userId") Long id) {

        return ResponseEntity.ok(userService.getInfo(id));
    }
}