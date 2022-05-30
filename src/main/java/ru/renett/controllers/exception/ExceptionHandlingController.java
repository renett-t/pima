package ru.renett.controllers.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.ServiceException;

@ControllerAdvice
public class ExceptionHandlingController {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler({ServiceException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String postException(ServiceException ex, Model map) {
        logger.error("Exception happened. Returning 500 error. Exception: " + ex.getClass() + ", message: " + ex.getMessage());

        map.addAttribute(Constants.CODE_ATTR, "500");
        return "exception_page";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String articleNotFound(ArticleNotFoundException ex, Model map) {
        logger.warn("Requested unknown Article. Returning 404 error.");

        map.addAttribute(Constants.MESSAGE_ATTR, "Article not found :(");
        map.addAttribute(Constants.CODE_ATTR, "404");
        return "exception_page";
    }
}
