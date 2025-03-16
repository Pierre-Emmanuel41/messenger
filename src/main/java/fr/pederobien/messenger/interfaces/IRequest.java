package fr.pederobien.messenger.interfaces;

import fr.pederobien.utils.ReadableByteWrapper;

public interface IRequest {

	/**
	 * @return The version of the communication protocol
	 */
	float getVersion();

	/**
	 * @return The request identifier.
	 */
	int getIdentifier();

	/**
	 * @return The request error code value.
	 */
	int getErrorCode();

	/**
	 * Set the error code of the request.
	 * 
	 * @param errorCode The value of the error code.
	 */
	void setErrorCode(int errorCode);

	/**
	 * @return The request payload.
	 */
	IPayload getPayload();

	/**
	 * Parse the content of the input wrapper. The input array shall have the
	 * following format:<br>
	 * <br>
	 * Byte 0 -> 3: Error code<br>
	 * Byte 4 -> end: Payload<br>
	 * 
	 * @param wrapper The wrapper that contains request information.
	 */
	IRequest parse(ReadableByteWrapper wrapper);

	/**
	 * Generates a bytes array with the following format:<br>
	 * <br>
	 * Byte 0 -> 3: Protocol version<br>
	 * Byte 4 -> 7: Message identifier<br>
	 * Byte 8 -> 11: Error code<br>
	 * Byte 12 -> end: Payload
	 * 
	 * @return The bytes array to send to the remote.
	 */
	byte[] getBytes();
}
