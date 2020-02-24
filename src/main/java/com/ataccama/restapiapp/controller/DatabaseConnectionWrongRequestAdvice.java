package com.ataccama.restapiapp.controller;

import com.ataccama.restapiapp.exception.DatabaseConnectionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class DatabaseConnectionWrongRequestAdvice {

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String sqlExceptionHandler(SQLException ex) {
        return ex.getMessage();
    }
}
