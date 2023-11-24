package com.kjj.noauth.exception;

import lombok.Getter;

@Getter
public class WrongRequestBodyException extends RuntimeException{

    public WrongRequestBodyException(String message) { super(message); }

}
