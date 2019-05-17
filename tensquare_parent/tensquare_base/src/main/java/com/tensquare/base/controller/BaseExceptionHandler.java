package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    //@ResponseBody
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
