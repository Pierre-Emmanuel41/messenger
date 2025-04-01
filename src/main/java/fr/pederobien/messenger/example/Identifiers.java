package fr.pederobien.messenger.example;

public enum Identifiers {

	// The identifier of the request whose payload contains an string
	STRING_ID(1),

	// The identifier of the request whose payload contains an integer
	INT_ID(2),

	// The identifier of the request whose payload contains an float
	FLOAT_ID(3),

	// The identifier of the request whose payload contains an player
	PLAYER_ID(4);

	private int value;

	private Identifiers(int value) {
		this.value = value;
	}

	/**
	 * @return The identifier value
	 */
	public int getValue() {
		return value;
	}
}
