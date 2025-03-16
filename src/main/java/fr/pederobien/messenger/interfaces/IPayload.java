package fr.pederobien.messenger.interfaces;

import fr.pederobien.utils.ReadableByteWrapper;

public interface IPayload {

	/**
	 * @return An array of bytes that represent this payload.
	 */
	byte[] getBytes();

	/**
	 * @return The payload as an object.
	 */
	Object get();

	/**
	 * Set the payload value. The given object should contains all the data to send
	 * to the remote.
	 * 
	 * @param value The payload value.
	 */
	void set(Object value);

	/**
	 * Parse the content of the wrapper.
	 * 
	 * @param wrapper Wrapper that contains the data to parse.
	 */
	void parse(ReadableByteWrapper wrapper);
}
