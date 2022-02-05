package fr.pederobien.messenger.impl;

import fr.pederobien.messenger.interfaces.IProtocol;

public class ProtocolAlreadyRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IProtocol protocol;

	public ProtocolAlreadyRegisteredException(IProtocol protocol) {
		super(String.format("A protocol with version %s is already registered", protocol.getVersion()));
		this.protocol = protocol;
	}

	/**
	 * @return The already registered protocol.
	 */
	public IProtocol getProtocol() {
		return protocol;
	}
}
