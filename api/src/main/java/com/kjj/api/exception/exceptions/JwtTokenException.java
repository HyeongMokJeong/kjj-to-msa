package com.kjj.api.exception.exceptions;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException{


    public JwtTokenException(String message) { super(message); }

}
