package fr.pederobien.messenger.impl;

import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.IMessageCreator;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.utils.ByteWrapper;

public class ProtocolManager {
	private AtomicInteger sequence;
	private Function<Float, IHeader> header;
	private Function<IHeader, String> parser;
	private NavigableMap<Float, IProtocol> protocoles;

	/**
	 * Creates a manager responsible to gather several version of the same communication protocol.
	 * 
	 * @param sequence The first sequence number from which next messages sequence are incremented by 1.
	 * @param header   The function responsible to create a new header when the function is called.
	 * @param parser   The function responsible to do the association between the header properties and the message name to create.
	 */
	public ProtocolManager(int sequence, Function<Float, IHeader> header, Function<IHeader, String> parser) {
		this.sequence = new AtomicInteger(sequence);
		this.header = header;
		this.parser = parser;

		protocoles = new TreeMap<Float, IProtocol>();
	}

	/**
	 * Register a new protocol associated to the given version.
	 * 
	 * @param version The protocol version.
	 * 
	 * @return The created protocol.
	 * 
	 * @throws ProtocolAlreadyRegisteredException If a protocol is already registered for the given version.
	 */
	public IProtocol register(float version) {
		checkVersion(version);

		IProtocol protocol = new Protocol(sequence, version, header, parser);
		protocoles.put(version, protocol);
		return protocol;
	}

	/**
	 * Register a new protocol associated to the given version. Each creator registered in the given <code>basedOn</code> protocol are
	 * added to the new one.
	 * 
	 * @param version The protocol version to register.
	 * @param basedOn The protocol on which the new protocol is based.
	 * 
	 * @return The created protocol.
	 * 
	 * @throws ProtocolAlreadyRegisteredException If a protocol is already registered for the given version.
	 */
	public IProtocol register(float version, IProtocol basedOn) {
		IProtocol protocol = register(version);

		for (IMessageCreator creator : basedOn)
			protocol.register(creator);

		return protocol;
	}

	/**
	 * Get the protocol associated to the given version.
	 * 
	 * @param version The version of the protocol to retrieve.
	 * 
	 * @return An optional that contains the protocol if registered, an empty optional otherwise.
	 */
	public Optional<IProtocol> getProtocol(float version) {
		return Optional.ofNullable(protocoles.get(version));
	}

	/**
	 * @return The protocol associated to the latest version.
	 */
	public IProtocol getLatest() {
		return protocoles.lastEntry().getValue();
	}

	/**
	 * Creates a message that contains parsed information relative to the given bytes array.
	 * 
	 * @param buffer The bytes array that contains data to interpret.
	 * 
	 * @return The message that contains interpreted information.
	 */
	public IMessage parse(byte[] buffer) {
		ByteWrapper wrapper = ByteWrapper.wrap(buffer);
		int index = 0;

		// +0: Begin word
		index += 4;

		// +4: Version
		float version = wrapper.getFloat(index);

		IProtocol protocol = protocoles.get(version);
		if (protocol == null)
			return null;

		IHeader parsed = header.apply(version).parse(wrapper.extract(4, buffer.length - 4));
		byte[] headerBytes = parsed.generate();
		byte[] properties = wrapper.extract(8 + headerBytes.length, buffer.length - (8 + headerBytes.length + 2));
		return protocol.parse(parsed, properties);
	}

	/**
	 * @return header The function responsible to create a new header when the function is called.
	 */
	public Function<Float, IHeader> getHeader() {
		return header;
	}

	/**
	 * @return parser The function responsible to do the association between bytes and the message name.
	 */
	public Function<IHeader, String> getParser() {
		return parser;
	}

	/**
	 * @return An atomic integer in order to generate unique identifiers.
	 */
	public AtomicInteger getSequence() {
		return sequence;
	}

	private void checkVersion(float version) {
		IProtocol protocol = protocoles.get(version);
		if (protocol != null)
			throw new ProtocolAlreadyRegisteredException(protocol);
	}
}
