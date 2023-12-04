package com.kjj.calculate.exception;

import lombok.Getter;

@Getter
public class WrongParameterException extends Exception{

    public WrongParameterException(String message) {
        super(message);
    }
}
