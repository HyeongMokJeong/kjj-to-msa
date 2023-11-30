package com.kjj.user.exception;

import lombok.Getter;

@Getter
public class JwtRefreshException extends RuntimeException{

    public JwtRefreshException(String message) { super(message); }

}
