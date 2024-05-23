package com.example.demo.controller;

import com.example.demo.exception.StudentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler({RuntimeException.class})
    public String handleException(Exception ex, Model model) {
        log.error("RuntimeException", ex);
        model.addAttribute("exception", ex);
        return "error";
    }
}
