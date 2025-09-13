package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

import java.util.StringJoiner;

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
