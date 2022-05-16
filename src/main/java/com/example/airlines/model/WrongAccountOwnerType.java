package com.example.airlines.model;

public class WrongAccountOwnerType extends Exception {
    public WrongAccountOwnerType() {
    }

    public WrongAccountOwnerType(String message) {
        super(message);
    }

    public WrongAccountOwnerType(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAccountOwnerType(Throwable cause) {
        super(cause);
    }

    public WrongAccountOwnerType(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
