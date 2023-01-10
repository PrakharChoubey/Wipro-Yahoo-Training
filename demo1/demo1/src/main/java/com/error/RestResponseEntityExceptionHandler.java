package com.springboot.demo1.error;

import com.springboot.demo1.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorMessage> departmentNotFound(DepartmentNotFoundException exception,
                                                          WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(IllegalNameUsedException.class)
    public ResponseEntity<ErrorMessage> illegalNameUsed(IllegalNameUsedException exception){
        ErrorMessage message= new ErrorMessage(HttpStatus.BAD_REQUEST,"Illegal name is used");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }



}
