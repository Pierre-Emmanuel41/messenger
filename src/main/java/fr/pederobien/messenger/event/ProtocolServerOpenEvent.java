package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

public class ProtocolServerOpenEvent extends ProtocolServerEvent {

	/**
	 * Creates a server open event.
	 * 
	 * @param server The opened server.
	 */
	public ProtocolServerOpenEvent(IProtocolServer server) {
		super(server);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("server=" + getServer());
		return String.format("%s_%s", getName(), joiner);
	}
}
