package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

public class ProtocolServerUnstableEvent extends ProtocolServerEvent {

	/**
	 * Create a server unstable event.
	 * 
	 * @param server The server involved in this event.
	 */
	public ProtocolServerUnstableEvent(IProtocolServer server) {
		super(server);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("server=" + getServer());
		return String.format("%s_%s", getName(), joiner);
	}
}
