package ru.renett.controllers.exception;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.ArticleNotFoundException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String postException(HttpServletResponse response){
        response.setStatus(500);

        return "exception_page";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String articleNotFound(HttpServletResponse response, ModelMap map) {
        response.setStatus(500);
        map.put(Constants.MESSAGE_ATTR, "Article not found :(");
        return "exception_page";
    }
}
