package com.kjj.auth.controller;

import com.kjj.auth.tool.HttpTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/api/user/menu/**", "/api/manager/menu/**"})
@RequiredArgsConstructor
public class MenuProxyController {

    private final RestTemplate restTemplate;
    private final HttpTools httpTools;

    @GetMapping
    public ResponseEntity<Object> receiveToMenuServerGetRequest(HttpServletRequest request) {
        ResponseEntity<Object> response = restTemplate.exchange(
                httpTools.createProxyUrlForMenuServer(request),
                HttpMethod.GET,
                httpTools.createHttpEntityWithHeaderJwtAndJsonBodyNo(request),
                Object.class);
        return ResponseEntity.ok(response.getBody());
    }
}
