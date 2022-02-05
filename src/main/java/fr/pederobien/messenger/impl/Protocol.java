package fr.pederobien.messenger.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IMessageCreator;
import fr.pederobien.messenger.interfaces.IProtocol;

public class Protocol implements IProtocol {
	private static final AtomicInteger IDENTIFIER = new AtomicInteger();

	private Function<Float, IHeader> header;
	private float version;
	private Map<String, IMessageCreator> messages;
	private Function<IHeader, String> parser;

	/**
	 * Creates a new communication protocol in order to store supported messages that can be created in order to be sent through the
	 * network or to be parsed when received from the network.
	 * 
	 * @param version The protocol version.
	 * @param header  The supplier responsible to create a new header when the supplier is called.
	 * @param parser  The function responsible to do the association between bytes and the message name.
	 */
	protected Protocol(float version, Function<Float, IHeader> header, Function<IHeader, String> parser) {
		this.version = version;
		this.header = header;
		this.parser = parser;

		messages = new HashMap<String, IMessageCreator>();
	}

	@Override
	public Iterator<IMessageCreator> iterator() {
		return messages.values().iterator();
	}

	@Override
	public float getVersion() {
		return version;
	}

	@Override
	public void register(IMessageCreator creator) {
		messages.put(creator.getName(), creator);
	}

	@Override
	public IMessage get(String name) {
		IMessageCreator creator = messages.get(name);
		if (creator == null)
			return null;

		IMessage message = creator.create(header.apply(version));
		message.getHeader().setIdentifier(IDENTIFIER.getAndIncrement());
		return message;
	}

	@Override
	public IMessage parse(IHeader header, byte[] buffer) {
		IMessageCreator creator = messages.get(parser.apply(header));
		return creator == null ? null : creator.create(header).parse(buffer);
	}

	@Override
	public IMessage answer(IMessage message, Object... properties) {
		return answer(message, message.getHeader(), properties);
	}

	@Override
	public IMessage answer(IMessage message, IHeader header, Object... properties) {
		IMessageCreator creator = messages.get(message.getName());
		if (creator == null)
			return null;

		IMessage answer = creator.create(header);
		answer.setProperties(properties);
		return answer;
	}

	@Override
	public List<IMessageCreator> toList() {
		return new ArrayList<IMessageCreator>(messages.values());
	}

	@Override
	public Stream<IMessageCreator> toStream() {
		return toList().stream();
	}
}
