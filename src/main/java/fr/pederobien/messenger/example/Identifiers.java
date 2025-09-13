package fr.pederobien.messenger.example;

import fr.pederobien.protocol.interfaces.IIdentifier;

public enum Identifiers implements IIdentifier {

    // The identifier of the request whose payload contains a string
    STRING_ID(1, "SEND_STRING"),

    // The identifier of the request whose payload contains an integer
    INT_ID(2, "SEND_INTEGER"),

    // The identifier of the request whose payload contains a float
    FLOAT_ID(3, "SEND_FLOAT"),

    // The identifier of the request whose payload contains a player
    PLAYER_ID(4, "SEND_PLAYER");

    private final int code;
    private final String message;

    Identifiers(int code, String message) {
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
