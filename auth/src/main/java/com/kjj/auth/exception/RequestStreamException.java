package com.kjj.auth.exception;

import lombok.Getter;

@Getter
public class RequestStreamException extends RuntimeException{


    public RequestStreamException(String message, Exception e) { super(message, e); }

}
