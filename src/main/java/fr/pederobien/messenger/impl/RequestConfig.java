package fr.pederobien.messenger.impl;

import fr.pederobien.messenger.interfaces.IPayloadWrapper;

public class RequestConfig {
	private int identifier;
	private IPayloadWrapper wrapper;

	/**
	 * Creates a request configuration.
	 * 
	 * @param identifier The request identifier.
	 * @param wrapper    The wrapper that parse/generates a bytes array from an
	 *                   object payload.
	 */
	public RequestConfig(int identifier, IPayloadWrapper wrapper) {
		this.identifier = identifier;
		this.wrapper = wrapper;
	}

	/**
	 * @return The request identifier.
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @return The payload wrapper.
	 */
	public IPayloadWrapper getWrapper() {
		return wrapper;
	}
}
