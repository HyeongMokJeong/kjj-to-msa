package com.kjj.user.exception;

import lombok.Getter;

@Getter
public class CantFindByIdException extends RuntimeException{

    public CantFindByIdException(String message) { super(message); }

}
