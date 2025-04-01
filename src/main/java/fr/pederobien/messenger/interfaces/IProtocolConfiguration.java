package fr.pederobien.messenger.interfaces;

import fr.pederobien.protocol.interfaces.IRequest;

public interface IProtocolConfiguration {

	/**
	 * Parse the given bytes array. The input array shall have the following
	 * format:<br>
	 * 
	 * Byte 0 -> 3: Protocol version<br>
	 * Byte 4 -> 7: message identifier<br>
	 * Byte 8 -> 11: Error code<br>
	 * Byte 12 -> end: payload
	 * 
	 * @param data The bytes array that contains message information.
	 * 
	 * @return The message corresponding to the content of the bytes array, null if
	 *         the protocol version is not supported or if the message identifier is
	 *         not supported for the protocol version.
	 */
	IRequest parse(byte[] data);

	/**
	 * Creates a request associated to the given identifier, if supported by at
	 * least one protocol, and set its error code and payload.
	 * 
	 * @param identifier The request identifier.
	 * @param errorCode  The request error code.
	 * @param payload    The request payload.
	 * 
	 * @return The request ready to be sent to the server or null if the identifier
	 *         is not supported.
	 */
	IRequestMessage getRequest(int identifier, int errorCode, Object payload);
}
