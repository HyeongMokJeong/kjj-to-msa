package com.kjj.user.exception;

import lombok.Getter;

@Getter
public class JoinKeycloakException extends RuntimeException{

    public JoinKeycloakException(String message) { super(message); }

}
