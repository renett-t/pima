package ru.renett.exceptions;

public class PasswordsMismatchException extends RuntimeException {
    public PasswordsMismatchException() {
    }

    public PasswordsMismatchException(String message) {
        super(message);
    }

    public PasswordsMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsMismatchException(Throwable cause) {
        super(cause);
    }

    public PasswordsMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
