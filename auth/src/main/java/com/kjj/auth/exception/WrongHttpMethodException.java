package com.kjj.auth.exception;

import lombok.Getter;

@Getter
public class WrongHttpMethodException extends RuntimeException{


    public WrongHttpMethodException(String message) { super(message); }

}
