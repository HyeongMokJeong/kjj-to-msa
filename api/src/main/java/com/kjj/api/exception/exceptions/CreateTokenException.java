package com.kjj.api.exception.exceptions;

import lombok.Getter;

@Getter
public class CreateTokenException extends RuntimeException{
    public CreateTokenException(Exception e) { super(e); }

}
