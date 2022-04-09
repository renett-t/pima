package ru.kpfu.itis.renett.exceptions;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException() {
    }

    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidUserDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidUserDataException(String message, Throwable throwable, boolean b, boolean b1) {
        super(message, throwable, b, b1);
    }
}
