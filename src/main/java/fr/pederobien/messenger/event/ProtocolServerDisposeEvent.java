package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

public class ProtocolServerDisposeEvent extends ProtocolServerEvent {

	/**
	 * Creates a server dispose event.
	 * 
	 * @param server The disposed server.
	 */
	public ProtocolServerDisposeEvent(IProtocolServer server) {
		super(server);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("server=" + getServer());
		return String.format("%s_%s", getName(), joiner);
	}
}
