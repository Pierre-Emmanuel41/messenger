package fr.pederobien.messenger.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import fr.pederobien.messenger.interfaces.IErrorCodeFactory;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.messenger.interfaces.IRequest;
import fr.pederobien.utils.ReadableByteWrapper;

public class ProtocolManager {
	private NavigableMap<Float, Protocol> protocols;
	private IErrorCodeFactory factory;

	/**
	 * Creates a protocol manager to insure the backward compatibility.
	 */
	public ProtocolManager() {
		protocols = new TreeMap<Float, Protocol>();
		factory = new ErrorCodeFactory();
	}

	/**
	 * Creates a protocol if no protocol is existing for the given version.
	 * 
	 * @param version The protocol version to get or to create.
	 * 
	 * @return The protocol associated to the given version.
	 */
	public IProtocol getOrCreate(float version) {
		Protocol protocol = protocols.get(version);
		if (protocol == null) {
			protocol = new Protocol(version, factory);
			protocols.put(version, protocol);
		}

		return protocol;
	}

	/**
	 * Find the latest protocol version that support the given identifier. If a
	 * protocol supports the identifier then a request will be created.
	 * 
	 * @param identifier The identifier of the request to generate.
	 * 
	 * @return The request if the identifier is supported, null otherwise.
	 */
	public IRequest get(int identifier) {
		Iterator<Map.Entry<Float, Protocol>> iterator = protocols.descendingMap().entrySet().iterator();
		IRequest request = null;

		while (iterator.hasNext() && request != null) {
			request = iterator.next().getValue().get(identifier);
		}

		return request;
	}

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
	public IRequest parse(byte[] data) {
		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(data);

		// Byte 0 -> 3: Protocol version
		float version = wrapper.nextFloat();

		Protocol protocol = protocols.get(version);
		if (protocol == null) {
			return null;
		}

		return protocol.parse(wrapper);
	}

	/**
	 * @return The error code factory to store all possible errors and their
	 *         meaning.
	 */
	public IErrorCodeFactory getErrorCodeFactory() {
		return factory;
	}

	private class ErrorCodeFactory implements IErrorCodeFactory {
		private static final String NOT_SUPPORTED = "CODE_NOT_SUPPORTED";
		private Map<Integer, String> errorCodes;

		public ErrorCodeFactory() {
			errorCodes = new HashMap<Integer, String>();
		}

		@Override
		public void register(int value, String message) {
			errorCodes.put(value, message);
		}

		@Override
		public String getMessage(int value) {
			String message = errorCodes.get(value);
			return message == null ? NOT_SUPPORTED : message;
		}
	}
}
