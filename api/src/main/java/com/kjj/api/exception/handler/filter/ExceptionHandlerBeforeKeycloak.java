package com.kjj.api.exception.handler.filter;

import com.kjj.api.exception.handler.filter.template.SetFilterErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerBeforeKeycloak extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (HttpClientErrorException e) {
            SetFilterErrorResponse.setResponse(request, response, HttpStatus.UNAUTHORIZED,"Keycloak 인증에 실패하였습니다.");
        }
    }
}
