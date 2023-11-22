package com.kjj.api.exception.exceptions;

import lombok.Getter;

@Getter
public class JwtException extends Exception{
    public JwtException(String message, Exception e) {
        super(message, e);
    }
}
