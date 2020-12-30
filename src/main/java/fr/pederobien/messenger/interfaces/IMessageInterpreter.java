package fr.pederobien.messenger.interfaces;

public interface IMessageInterpreter {

	/**
	 * Generates the bytes array associated to the given objects array.
	 * 
	 * @param payload An array that contains informations to send to the remote.
	 * 
	 * @return The associated byte array.
	 */
	byte[] generate(Object[] payload);

	/**
	 * Interpret the byte array in order to recreate the payload.
	 * 
	 * @param payload An array that contains informations received from the remote.
	 * 
	 * @return The associated objects array.
	 */
	Object[] interprete(byte[] payload);
}
