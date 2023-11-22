package com.kjj.auth.exception;

import java.io.IOException;

public class SetFilterErrorResponseException extends IOException{

    public SetFilterErrorResponseException(Exception message) {
        super(message);
    }
}
