package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

public class ProtocolServerEvent extends MessengerEvent {
	private final IProtocolServer server;

	/**
	 * Creates a server event.
	 * 
	 * @param server The server involved in this event
	 */
	public ProtocolServerEvent(IProtocolServer server) {
		this.server = server;
	}

	/**
	 * @return The server involved in this event.
	 */
	public IProtocolServer getServer() {
		return server;
	}
}
