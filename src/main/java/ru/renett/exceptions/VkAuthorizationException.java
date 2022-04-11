package ru.renett.exceptions;

public class VkAuthorizationException extends RuntimeException {
    public VkAuthorizationException() {
    }

    public VkAuthorizationException(String s) {
        super(s);
    }

    public VkAuthorizationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public VkAuthorizationException(Throwable throwable) {
        super(throwable);
    }

    public VkAuthorizationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
