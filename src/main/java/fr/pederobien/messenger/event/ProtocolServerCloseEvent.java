package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.server.IProtocolServer;

import java.util.StringJoiner;

public class ProtocolServerCloseEvent extends ProtocolServerEvent {

    /**
     * Creates a server close event.
     *
     * @param server The closed server.
     */
    public ProtocolServerCloseEvent(IProtocolServer server) {
        super(server);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        joiner.add("server=" + getServer());
        return String.format("%s_%s", getName(), joiner);
    }
}
