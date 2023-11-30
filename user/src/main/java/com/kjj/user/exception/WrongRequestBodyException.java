package com.kjj.user.exception;

import lombok.Getter;

@Getter
public class WrongRequestBodyException extends RuntimeException{

    public WrongRequestBodyException(String message) { super(message); }

}
