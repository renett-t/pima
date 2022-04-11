package ru.renett.exceptions;

public class InvalidRequestDataException extends RuntimeException {
    public InvalidRequestDataException() {
    }

    public InvalidRequestDataException(String s) {
        super(s);
    }

    public InvalidRequestDataException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidRequestDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidRequestDataException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
