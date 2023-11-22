package com.kjj.api.exception.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StringToMapException extends JsonProcessingException {
    public StringToMapException(String m, Exception e) {
        super(m, e);
    };
}
