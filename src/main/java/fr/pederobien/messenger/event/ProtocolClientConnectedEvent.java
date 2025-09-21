package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.client.IProtocolClient;

import java.util.StringJoiner;

public class ProtocolClientConnectedEvent extends ProtocolClientEvent {

    /**
     * Creates an event thrown when the client is connected with the remote.
     *
     * @param client The client involved in this event.
     */
    public ProtocolClientConnectedEvent(IProtocolClient client) {
        super(client);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        joiner.add("client=" + getClient());
        return String.format("%s_%s", getName(), joiner);
    }
}
