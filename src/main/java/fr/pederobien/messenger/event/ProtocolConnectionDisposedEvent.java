package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IProtocolConnection;

public class ProtocolConnectionDisposedEvent extends ProtocolConnectionEvent {

	/**
	 * Creates a connection disposed event.
	 * 
	 * @param connection The connection that is now disposed.
	 */
	public ProtocolConnectionDisposedEvent(IProtocolConnection connection) {
		super(connection);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("remote=" + getConnection());
		return String.format("%s_%s", getName(), joiner);
	}
}
