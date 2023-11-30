package com.kjj.noauth.exception;

import lombok.Getter;

@Getter
public class KeycloakWithdrawException extends RuntimeException{

    public KeycloakWithdrawException(String message) { super(message); }

}
