package ru.renett.exceptions;

public class DataBaseException extends RuntimeException {

    public DataBaseException() {
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataBaseException(Throwable throwable) {
        super(throwable);
    }

}
