package com.kjj.user.controller.handler;

import com.kjj.user.dto.response.ResponseExeptionTemplate;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.exception.CantFindByUsernameException;
import com.kjj.user.exception.WrongParameterException;
import com.kjj.user.exception.WrongRequestBodyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
     HttpStatus status;

    @ExceptionHandler({CantFindByIdException.class, CantFindByUsernameException.class})
    public final ResponseEntity<Object> cantFindById(Exception ex, WebRequest request){
        status = HttpStatus.BAD_REQUEST;
        ResponseExeptionTemplate exceptionResponse = new ResponseExeptionTemplate(new Date().toString(), ex.getMessage(), ((ServletWebRequest)request).getRequest().getRequestURI(), status.value());
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler({WrongRequestBodyException.class, WrongParameterException.class})
    public final ResponseEntity<Object> wrongRequest(Exception ex, WebRequest request){
        status = HttpStatus.BAD_REQUEST;
        ResponseExeptionTemplate exceptionResponse = new ResponseExeptionTemplate(new Date().toString(), ex.getMessage(), ((ServletWebRequest)request).getRequest().getRequestURI(), status.value());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}