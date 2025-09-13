package fr.pederobien.messenger.example;

import fr.pederobien.protocol.interfaces.IError;

public enum Errors implements IError {
    NO_ERROR(0, "No Error"),
    REQUEST_DENIED(1, "Request denied");

    private int code;
    private String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
