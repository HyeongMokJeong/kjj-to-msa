package com.kjj.menu.exception;

import lombok.Getter;

@Getter
public class WrongParameterException extends RuntimeException{

    public WrongParameterException(String message) { super(message); }

}
