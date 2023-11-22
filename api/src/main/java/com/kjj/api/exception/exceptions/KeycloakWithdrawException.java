package com.kjj.api.exception.exceptions;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class KeycloakWithdrawException extends AuthenticationException {

    public KeycloakWithdrawException(String message) {
        super(message);
    }
}
