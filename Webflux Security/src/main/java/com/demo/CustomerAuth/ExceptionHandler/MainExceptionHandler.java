package com.demo.CustomerAuth.ExceptionHandler;

import com.demo.CustomerAuth.Exception.AccountNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> method(AccountNotFoundException ex) {
        String str = ex.msg;
        return ResponseEntity.badRequest().body(str);
    }

}





