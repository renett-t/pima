package ru.renett.exceptions;

import ru.renett.dto.rest.ArticlesPage;

public class InvalidArticlesRequestException extends RuntimeException {
    private ArticlesPage result;

    public InvalidArticlesRequestException(ArticlesPage result) {
        this.result = result;
    }

    public InvalidArticlesRequestException() {
    }

    public InvalidArticlesRequestException(String message) {
        super(message);
    }

    public InvalidArticlesRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArticlesRequestException(Throwable cause) {
        super(cause);
    }

    public InvalidArticlesRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ArticlesPage getResult() {
        return result;
    }

    public void setResult(ArticlesPage result) {
        this.result = result;
    }
}
