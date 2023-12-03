package com.kjj.menu.exception;

import lombok.Getter;

@Getter
public class SameNameException extends RuntimeException{

    public SameNameException(String message) { super(message); }

}
