package com.kjj.user.controller.handler

import com.kjj.user.dto.response.ResponseExceptionTemplate
import com.kjj.user.exception.CantFindByIdException
import com.kjj.user.exception.CantFindByUsernameException
import com.kjj.user.exception.WrongParameterException
import com.kjj.user.exception.WrongRequestBodyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@RestControllerAdvice
class ControllerExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CantFindByIdException::class, CantFindByUsernameException::class])
    fun cantFindById(e: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val exceptionResponse = ResponseExceptionTemplate(
            Date().toString(),
            e.message,
            (request as ServletWebRequest).request.requestURI,
            status.value()
        )
        return ResponseEntity<Any>(exceptionResponse, status)
    }

    @ExceptionHandler(value = [WrongRequestBodyException::class, WrongParameterException::class])
    fun wrongRequest(e: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val exceptionResponse = ResponseExceptionTemplate(
            Date().toString(),
            e.message,
            (request as ServletWebRequest).request.requestURI,
            status.value()
        )
        return ResponseEntity<Any>(exceptionResponse, status)
    }
}