package fr.pederobien.messenger.interfaces;

public interface IMessageCreator {

	/**
	 * @return The name of the message to create.
	 */
	String getName();

	/**
	 * Creates a message.
	 * 
	 * @param header The message header.
	 * 
	 * @return A new message.
	 */
	IMessage create(IHeader header);
}
