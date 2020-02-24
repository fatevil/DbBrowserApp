package com.ataccama.restapiapp.controller;

import com.ataccama.restapiapp.exception.DatabaseConnectionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DatabaseConnectionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DatabaseConnectionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(DatabaseConnectionNotFoundException ex) {
        return ex.getMessage();
    }
}
