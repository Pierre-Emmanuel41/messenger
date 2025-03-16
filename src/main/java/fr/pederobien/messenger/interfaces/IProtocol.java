package fr.pederobien.messenger.interfaces;

import fr.pederobien.utils.ReadableByteWrapper;

public interface IProtocol {

	/**
	 * @return The version of this protocol.
	 */
	float getVersion();

	/**
	 * Register internally a request configuration to easily generate a request.
	 * 
	 * @param identifier The request identifier.
	 * @param payload    the request payload.
	 */
	void register(int identifier, IPayload payload);

	/**
	 * Creates a new request to send to the remote if the given identifier is
	 * supported by the protocol.
	 * 
	 * @param identifier The identifier of the request to create.
	 * 
	 * @return The created request if the identifier is supported, false otherwise.
	 */
	IRequest get(int identifier);

	/**
	 * Parse the content of the input wrapper. The input array shall have the
	 * following format:<br>
	 * <br>
	 * Byte 0 -> 3: Message identifier<br>
	 * Byte 4 -> 7: Error code<br>
	 * Byte 8 -> end: Payload<br>
	 * 
	 * @param wrapper The wrapper that contains request information.
	 */
	IRequest parse(ReadableByteWrapper wrapper);
}
