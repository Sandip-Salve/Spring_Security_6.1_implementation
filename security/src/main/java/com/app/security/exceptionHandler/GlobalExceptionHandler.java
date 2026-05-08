package com.app.security.exceptionHandler;

import com.app.security.utilities.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse> handleException(Exception ex){
        return new ResponseEntity<>(new CustomApiResponse("Something went wrong::"+ ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomApiResponse> handleBadCredentialsException(BadCredentialsException ex){
        return new ResponseEntity<>(new CustomApiResponse("Bad Request::"+ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<CustomApiResponse> handleAccessDeniedException(AccessDeniedException ex){
//        return new ResponseEntity<>(new CustomApiResponse("Access Denied::"+ex.getMessage()),HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        return new ResponseEntity<>(new CustomApiResponse(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
