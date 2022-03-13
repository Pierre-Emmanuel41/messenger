package fr.pederobien.messenger.interfaces;

import java.util.List;
import java.util.stream.Stream;

public interface IProtocol extends Iterable<IMessageCreator> {

	/**
	 * @return The version of this protocol.
	 */
	float getVersion();

	/**
	 * Register a message creator in order to create a message to send through the network or received from the network.
	 * 
	 * @param name    The message name.
	 * @param creator A function responsible to create a specific message.
	 */
	void register(IMessageCreator creator);

	/**
	 * Creates a message.
	 * 
	 * @param name   The name of the message to build.
	 * @param header The properties associated to the header of the message to build.
	 * 
	 * @return A message.
	 */
	IMessage get(String name);

	/**
	 * Interprets the given buffer in order to return the associated message with interpreted properties.
	 * 
	 * @param header The header that contains already parsed information.
	 * @param buffer The bytes array that contains data to interpret.
	 * 
	 * @return The associated message.
	 */
	IMessage parse(IHeader header, byte[] buffer);

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified.
	 * 
	 * @param message    The message to answer.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	IMessage answer(IMessage message, Object... properties);

	/**
	 * Creates a new message corresponding to the answer of the <code>message</code>. Neither the identifier nor the header are
	 * modified.
	 * 
	 * @param message    The message to answer.
	 * @param header     The response header.
	 * @param properties The response properties.
	 * 
	 * @return A new message.
	 */
	IMessage answer(IMessage message, IHeader header, Object... properties);

	/**
	 * @return A copy of the list of registered message creator.
	 */
	List<IMessageCreator> toList();

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IMessageCreator> stream();
}
