package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IProtocolConnection;

public class ProtocolConnectionLostEvent extends ProtocolConnectionEvent {

	/**
	 * Creates a connection lost event.
	 * 
	 * @param connection The connection which is no more connected with its remote.
	 */
	public ProtocolConnectionLostEvent(IProtocolConnection connection) {
		super(connection);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("remote=" + getConnection());
		return String.format("%s_%s", getName(), joiner);
	}
}
