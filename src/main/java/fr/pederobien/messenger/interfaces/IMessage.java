package fr.pederobien.messenger.interfaces;

public interface IMessage<T extends IHeader<T>> {

	/**
	 * @return The bytes array associated to this message.
	 */
	byte[] getBytes();

	/**
	 * @return The message identifier.
	 */
	int getIdentifier();

	/**
	 * @return An object array that contains informations to send to the remote.
	 */
	Object[] getPayload();

	/**
	 * @return The header associated to this message.
	 */
	T getHeader();

	/**
	 * Answer to this message with the given payload. Neither the identifier nor the header are modified.
	 * 
	 * @param payload An array that contains the answer of this message.
	 * 
	 * @return The message associated to the answer.
	 */
	IMessage<T> answer(Object... payload);

	/**
	 * Answer to this message with the given header and payload. The identifier is not modified.
	 * 
	 * @param header  The message header.
	 * @param payload An array that contains the answer of this message.
	 * 
	 * @return The message associated to the answer.
	 */
	IMessage<T> answer(T header, Object... payload);

}
