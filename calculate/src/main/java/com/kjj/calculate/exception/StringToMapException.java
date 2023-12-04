package com.kjj.calculate.exception;

import lombok.Getter;

@Getter
public class StringToMapException extends Exception{
    public StringToMapException(String message, Exception e) {
        super(message, e);
    }
}
