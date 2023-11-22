package com.kjj.auth.controller;

import com.kjj.auth.tool.HttpTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/**")
@RequiredArgsConstructor
public class ApiProxyController {

    private final RestTemplate restTemplate;
    private final HttpTools httpTools;

    @GetMapping
    public ResponseEntity<Object> receiveToApiServerGetRequest(HttpServletRequest request) {
        ResponseEntity<Object> response = restTemplate.exchange(
                httpTools.createProxyUrlForApiServer(request),
                HttpMethod.GET,
                httpTools.createHttpEntityWithHeaderJwtAndJsonBodyNo(request),
                Object.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<Object> receiveToApiServerPostRequest(HttpServletRequest request, @RequestBody Map<String, String> body) {
        ResponseEntity<Object> response = restTemplate.exchange(
                httpTools.createProxyUrlForApiServer(request),
                HttpMethod.POST,
                httpTools.createHttpEntityWithHeaderJwtAndJsonBodyYes(request, body),
                Object.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PatchMapping
    public ResponseEntity<Object> receiveToApiServerPatchRequest(HttpServletRequest request, @RequestBody Object body) {
        ResponseEntity<Object> response = restTemplate.exchange(
                httpTools.createProxyUrlForApiServer(request),
                HttpMethod.PATCH,
                httpTools.createHttpEntityWithHeaderJwtAndJsonBodyYes(request, body),
                Object.class);
        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Object> receiveToApiServerDeleteRequest(HttpServletRequest request, @RequestBody Object body) {
        ResponseEntity<Object> response = restTemplate.exchange(
                httpTools.createProxyUrlForApiServer(request),
                HttpMethod.DELETE,
                httpTools.createHttpEntityWithHeaderJwtAndJsonBodyYes(request, body),
                Object.class);
        return ResponseEntity.ok(response.getBody());
    }
}
