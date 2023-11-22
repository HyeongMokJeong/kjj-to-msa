package com.kjj.auth.tool;

import com.kjj.auth.security.JwtTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class HttpTools {

    private final JwtTemplate jwtTemplate = new JwtTemplate();
    @Value("${micro-service.kjj.api}") private String apiServerUrl;
    @Value("${micro-service.kjj.menu}") private String menuServerUrl;

    public String createProxyUrlForApiServer(HttpServletRequest request) {
        return apiServerUrl + request.getRequestURI();
    }

    public String createProxyUrlForMenuServer(HttpServletRequest request) {
        return menuServerUrl + request.getRequestURI();
    }

    public HttpEntity<MultiValueMap<String, String>> createHttpEntityWithHeaderJwtAndJsonBodyNo(HttpServletRequest request) {
        String headerName = jwtTemplate.getHeaderString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(headerName, request.getHeader(headerName));
        return new HttpEntity<>(headers);
    }

    public HttpEntity<Object> createHttpEntityWithHeaderJwtAndJsonBodyYes(HttpServletRequest request, Object body) {
        String headerName = jwtTemplate.getHeaderString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(headerName, request.getHeader(headerName));
        return new HttpEntity<>(body, headers);
    }
}
