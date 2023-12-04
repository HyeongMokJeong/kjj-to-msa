package com.kjj.store.exception;

import lombok.Getter;

@Getter
public class CantFindByIdException extends Exception{
    public CantFindByIdException(String message) {
        super(message);
    }

}
