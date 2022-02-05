package fr.pederobien.messenger.impl;

import java.util.function.Function;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IMessageCreator;

public class MessageCreator implements IMessageCreator {
	private String name;
	private Function<IHeader, IMessage> creator;

	/**
	 * Creates a new message creator.
	 * 
	 * @param name    The name of the created message.
	 * @param creator The message creator.
	 */
	public MessageCreator(String name, Function<IHeader, IMessage> creator) {
		this.name = name;
		this.creator = creator;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IMessage create(IHeader header) {
		return creator.apply(header);
	}
}
