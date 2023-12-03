package com.kjj.menu.exception;

import lombok.Getter;

@Getter
public class CantFindByIdException extends RuntimeException{

    public CantFindByIdException(String message) { super(message); }

}
