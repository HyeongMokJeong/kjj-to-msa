package com.kjj.auth.security.filter.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjj.auth.exception.SetFilterErrorResponseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SetFilterErrorResponse {
    public static void setResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String message) throws SetFilterErrorResponseException {
        ObjectMapper objectMapper = new ObjectMapper();
        ExceptionTemplate exceptionTemplate = new ExceptionTemplate(new Date().toString(), message, request.getRequestURI(), status.value());
        String result;
        response.setStatus(status.value());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            result = objectMapper.writeValueAsString(exceptionTemplate);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result);
        } catch (IOException | IllegalStateException ex) {
            throw new SetFilterErrorResponseException(ex);
        }
    }
}
