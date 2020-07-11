package com.company.eshop.error;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class, JWTVerificationException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        //Custom Application Object
        ApplicationError error = new ApplicationError("User Not Found", 4);
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(ex instanceof JWTVerificationException)
            error = new ApplicationError(ex.getLocalizedMessage(), 101);
        if(ex instanceof TokenExpiredException)
            error = new ApplicationError("Token Has Expired", 102);
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}