package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.client.IProtocolClient;

public class ProtocolClientEvent extends MessengerEvent {
	private final IProtocolClient client;

	/**
	 * Creates a client event.
	 * 
	 * @param client The client involved in this event.
	 */
	public ProtocolClientEvent(IProtocolClient client) {
		this.client = client;
	}

	/**
	 * @return The client involved in this event.
	 */
	public IProtocolClient getClient() {
		return client;
	}
}
