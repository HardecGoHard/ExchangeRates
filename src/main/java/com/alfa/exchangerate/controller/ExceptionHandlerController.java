package com.alfa.exchangerate.controller;

import com.alfa.exchangerate.exception.ExchangeRateException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ModelAndView exchangeErrorHandler(ExchangeRateException ex) {
        ModelAndView modelAndView = new ModelAndView("errorMessage");
        modelAndView.addObject("error",ex.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
