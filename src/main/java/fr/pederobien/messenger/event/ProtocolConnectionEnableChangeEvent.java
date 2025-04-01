package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IProtocolConnection;

public class ProtocolConnectionEnableChangeEvent extends ProtocolConnectionEvent {
	private boolean isEnabled;

	/**
	 * Creates a connection enable change event.
	 * 
	 * @param connection The connection associated to this event.
	 */
	public ProtocolConnectionEnableChangeEvent(IProtocolConnection connection, boolean isEnabled) {
		super(connection);
		this.isEnabled = isEnabled;
	}

	/**
	 * @return True if the connection is enabled, false otherwise.
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("remote=" + getConnection());
		joiner.add("isEnabled=" + isEnabled());
		return String.format("%s_%s", getName(), joiner);
	}
}
