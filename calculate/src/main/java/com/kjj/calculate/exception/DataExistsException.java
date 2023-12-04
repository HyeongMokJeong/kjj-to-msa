package com.kjj.calculate.exception;

import lombok.Getter;

@Getter
public class DataExistsException extends RuntimeException{

    public DataExistsException(String message) { super(message); }

}
