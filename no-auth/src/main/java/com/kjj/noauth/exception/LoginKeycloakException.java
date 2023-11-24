package com.kjj.noauth.exception;

import lombok.Getter;

@Getter
public class LoginKeycloakException extends RuntimeException{

    public LoginKeycloakException(String message) { super(message); }

}
