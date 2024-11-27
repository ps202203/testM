package com.nexon.maplestory.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model){
        model.addAttribute("error", ex.getMessage());
        return "error"; // error.html 템플릿
    }

    // 추가적인 예외 핸들러 가능
}