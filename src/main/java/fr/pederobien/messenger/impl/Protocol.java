package fr.pederobien.messenger.impl;

import java.util.HashMap;
import java.util.Map;

import fr.pederobien.messenger.interfaces.IErrorCodeFactory;
import fr.pederobien.messenger.interfaces.IPayload;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.messenger.interfaces.IRequest;
import fr.pederobien.utils.ReadableByteWrapper;

public class Protocol implements IProtocol {
	private float version;
	private Map<Integer, RequestConfig> configs;
	private IErrorCodeFactory factory;

	/**
	 * Creates a protocol associated to a specific version.
	 * 
	 * @param version The protocol version
	 */
	public Protocol(float version, IErrorCodeFactory factory) {
		this.version = version;
		this.factory = factory;

		configs = new HashMap<Integer, RequestConfig>();
	}

	@Override
	public float getVersion() {
		return version;
	}

	@Override
	public void register(int identifier, IPayload payload) {
		configs.put(identifier, new RequestConfig(identifier, payload));
	}

	@Override
	public IRequest get(int identifier) {
		RequestConfig config = configs.get(identifier);

		// Check if identifier is supported
		if (config == null)
			return null;

		return new Request(version, factory, config.getIdentifier(), 0, config.getPayload());
	}

	@Override
	public IRequest parse(ReadableByteWrapper wrapper) {
		// Byte 0 -> 3: Request identifier
		IRequest request = get(wrapper.nextInt());
		if (request == null) {
			return null;
		}

		return request.parse(wrapper);
	}

	private class RequestConfig {
		private int identifier;
		private IPayload payload;

		/**
		 * Creates a request configuration.
		 * 
		 * @param identifier The request identifier.
		 * @param payload    The request payload
		 */
		public RequestConfig(int identifier, IPayload payload) {
			this.identifier = identifier;
			this.payload = payload;
		}

		/**
		 * @return The request identifier.
		 */
		public int getIdentifier() {
			return identifier;
		}

		/**
		 * @return The request payload.
		 */
		public IPayload getPayload() {
			return payload;
		}
	}
}
