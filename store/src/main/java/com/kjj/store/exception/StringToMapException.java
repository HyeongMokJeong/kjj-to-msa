package com.kjj.store.exception;

import lombok.Getter;

@Getter
public class StringToMapException extends Exception{

    public StringToMapException(String message) {
        super(message);
    }
}
