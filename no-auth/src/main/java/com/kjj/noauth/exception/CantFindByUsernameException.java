package com.kjj.noauth.exception;

import lombok.Getter;

@Getter
public class CantFindByUsernameException extends RuntimeException{

    public CantFindByUsernameException(String message) { super(message); }

}
