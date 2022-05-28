package ru.renett.exceptions;

public class DuplicateParameterException extends RuntimeException{
    public DuplicateParameterException() {
    }

    public DuplicateParameterException(String message) {
        super(message);
    }

    public DuplicateParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateParameterException(Throwable cause) {
        super(cause);
    }

    public DuplicateParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
