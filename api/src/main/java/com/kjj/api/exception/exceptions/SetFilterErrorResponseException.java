package com.kjj.api.exception.exceptions;

import java.io.IOException;

public class SetFilterErrorResponseException extends IOException{

    public SetFilterErrorResponseException(Exception message) {
        super(message);
    }
}
