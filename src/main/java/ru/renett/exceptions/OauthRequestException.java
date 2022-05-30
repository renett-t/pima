package ru.renett.exceptions;

public class OauthRequestException extends RuntimeException {
    public OauthRequestException() {
    }

    public OauthRequestException(String s) {
        super(s);
    }

    public OauthRequestException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public OauthRequestException(Throwable throwable) {
        super(throwable);
    }

    public OauthRequestException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
