package com.kjj.image.exception;

import lombok.Getter;

@Getter
public class UploadFileException extends RuntimeException{

    public UploadFileException(String message) { super(message); }

}
