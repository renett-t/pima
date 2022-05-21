package ru.renett.controllers.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.service.impl.UsersServiceImpl;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    private final static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

//    @ExceptionHandler(Exception.class)
//    public String postException(HttpServletResponse response){
//        logger.error("Exception happened. Returning 500 error.");
//        response.setStatus(500);
//
//        return "exception_page";
//    }
//
//    @ExceptionHandler(ArticleNotFoundException.class)
//    public String articleNotFound(HttpServletResponse response, ModelMap map) {
//        response.setStatus(404);
//        logger.warn("Requested unknown Article. Returning 404 error.");
//        map.put(Constants.MESSAGE_ATTR, "Article not found :(");
//        return "exception_page";
//    }
}
