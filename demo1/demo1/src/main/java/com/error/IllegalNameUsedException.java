package com.springboot.demo1.error;

public class IllegalNameUsedException extends Exception{
    public IllegalNameUsedException() {
        super();
    }

    public IllegalNameUsedException(String message) {
        super(message);
    }

    public IllegalNameUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNameUsedException(Throwable cause) {
        super(cause);
    }

    protected IllegalNameUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
