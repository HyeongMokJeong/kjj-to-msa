package com.kjj.api.controller;

import com.kjj.api.aop.annotation.RestControllerClass;
import com.kjj.api.auth.JwtUtil;
import com.kjj.api.service.user.manager.ManagerService;
import com.kjj.api.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequiredArgsConstructor
@RestControllerClass("/api")
public class TestController {
    private final UserService userService;
    private final ManagerService managerService;
    private final JwtUtil jwtUtil;

    @GetMapping("/user/login/test")
    public ResponseEntity<Map<String, String>> testToken(@RequestParam("username") String username){
        if (username == null) username = "user";
        return ResponseEntity.ok(Map.of("accessToken", jwtUtil.createToken(userService.loadUserByUsername(username))));
    }

    @GetMapping("/manager/login/test")
    public ResponseEntity<Map<String, String>> testTokenForManager(){
        String managerName = "manager";
        return ResponseEntity.ok(Map.of("accessToken", jwtUtil.createToken(managerService.loadUserByUsername(managerName))));
    }
}
