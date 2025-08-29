package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.client.IProtocolClient;

public class ProtocolClientUnstableEvent extends ProtocolClientEvent {

	/**
	 * Creates an event thrown when a client is unstable.
	 * 
	 * @param client The unstable client.
	 */
	public ProtocolClientUnstableEvent(IProtocolClient client) {
		super(client);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("client=" + getClient());
		return String.format("%s_%s", getName(), joiner);
	}
}
