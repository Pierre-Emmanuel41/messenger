package fr.pederobien.messenger.event;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServer;

public class NewProtocolClientEvent extends ProtocolServerEvent {
	private final IProtocolClient client;

	/**
	 * Creates an event thrown when a client is connected to the server.
	 * 
	 * @param server The server to which the client is connected.
	 * @param client The client object connected with the remote.
	 */
	public NewProtocolClientEvent(IProtocolServer server, IProtocolClient client) {
		super(server);

		this.client = client;
	}

	/**
	 * @return The client object connected to the remote.
	 */
	public IProtocolClient getClient() {
		return client;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("server=" + getServer());
		joiner.add("remote=" + getClient());

		return joiner.toString();
	}
}
