package com.kjj.noauth.controller;

import com.kjj.noauth.service.UserService;
import com.kjj.noauth.util.jwt.JwtTools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping({"/v1/no-auth"})
@RequiredArgsConstructor
public class TestController {

    private final JwtTools jwtTools;
    private final UserService userService;

    @GetMapping("test")
    public ResponseEntity<Map<String, String>> testToken(@RequestParam("username") String username){
        if (username == null) username = "manager";
        return ResponseEntity.ok(Map.of("accessToken", jwtTools.createToken(userService.loadUserByUsername(username))));
    }
}
