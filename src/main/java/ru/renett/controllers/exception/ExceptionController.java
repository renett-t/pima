package ru.renett.controllers.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String postException(HttpServletResponse response){
        response.setStatus(500);

        return "exception_page";
    }
}
