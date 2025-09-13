package fr.pederobien.messenger.example;

public enum Identifiers {

    // The identifier of the request whose payload contains a string
    STRING_ID(1),

    // The identifier of the request whose payload contains an integer
    INT_ID(2),

    // The identifier of the request whose payload contains a float
    FLOAT_ID(3),

    // The identifier of the request whose payload contains a player
    PLAYER_ID(4);

    private final int value;

    Identifiers(int value) {
        this.value = value;
    }

    /**
     * @return The identifier value
     */
    public int getValue() {
        return value;
    }
}
