package fr.pederobien.messenger.impl;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.InterpretersFactory;

public class MessageFactory<T extends IHeader<T>> {
	private InterpretersFactory<T> interpreters;

	/**
	 * Create a message factory based on the given interpreters factory.
	 * 
	 * @param interpreters The interpreters used to parse bytes array or to generate byte array.
	 */
	public MessageFactory(InterpretersFactory<T> interpreters) {
		this.interpreters = interpreters;
	}

	/**
	 * Create a message based on the given header and the given payload.
	 * 
	 * @param header  The message header.
	 * @param payload The message payload.
	 * 
	 * @return A new message.
	 */
	public IMessage<T> create(T header, Object... payload) {
		return new Message<T>(interpreters, header, payload);
	}

	/**
	 * Parse the given byte array in order to retrieve informations sent by the remote.
	 * 
	 * @param header The message header that is filled.
	 * @param buffer the bytes array received from the remote.
	 * 
	 * @return A new message.
	 */
	public IMessage<T> parse(T header, byte[] buffer) {
		return Message.parse(interpreters, header, buffer);
	}
}
