package com.kjj.api.exception.exceptions;

import java.io.IOException;

public class LoginFilterException extends IOException {

    public LoginFilterException(String m, Exception e) {
        super(m, e);
    }
}
