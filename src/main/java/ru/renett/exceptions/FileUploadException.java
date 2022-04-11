package ru.renett.exceptions;

public class FileUploadException extends RuntimeException {
    public FileUploadException() {
    }

    public FileUploadException(String s) {
        super(s);
    }

    public FileUploadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FileUploadException(Throwable throwable) {
        super(throwable);
    }

    public FileUploadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
