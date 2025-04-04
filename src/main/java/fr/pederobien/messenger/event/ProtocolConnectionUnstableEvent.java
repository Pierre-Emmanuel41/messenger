package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IProtocolConnection;

public class ProtocolConnectionUnstableEvent extends ProtocolConnectionEvent {

	/**
	 * Create a connection unstable event.
	 * 
	 * @param connection The unstable connection.
	 */
	public ProtocolConnectionUnstableEvent(IProtocolConnection connection) {
		super(connection);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("remote=" + getConnection());
		return String.format("%s_%s", getName(), joiner);
	}
}
