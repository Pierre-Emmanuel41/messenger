package fr.pederobien.messenger.event;

import fr.pederobien.messenger.interfaces.IProtocolConnection;

public class ProtocolConnectionEvent extends MessengerEvent {
    private final IProtocolConnection connection;

    /**
     * Creates a connection event.
     *
     * @param connection The connection involved in this event.
     */
    public ProtocolConnectionEvent(IProtocolConnection connection) {
        this.connection = connection;
    }

    /**
     * @return The connection involved in this event.
     */
    public IProtocolConnection getConnection() {
        return connection;
    }
}
