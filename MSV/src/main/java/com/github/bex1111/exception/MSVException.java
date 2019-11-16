package com.github.bex1111.exception;

public class MSVException extends RuntimeException {
    public MSVException(String message) {
        super(message);
    }

    public MSVException(String message, Throwable cause) {
        super(message, cause);
    }
}
