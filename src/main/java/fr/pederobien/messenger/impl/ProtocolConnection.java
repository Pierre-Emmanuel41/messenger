package fr.pederobien.messenger.impl;

import fr.pederobien.communication.event.ConnectionDisposedEvent;
import fr.pederobien.communication.event.ConnectionEnableChangedEvent;
import fr.pederobien.communication.event.ConnectionLostEvent;
import fr.pederobien.communication.event.ConnectionUnstableEvent;
import fr.pederobien.communication.impl.connection.Message;
import fr.pederobien.communication.interfaces.connection.IConnection;
import fr.pederobien.communication.interfaces.connection.IMessage;
import fr.pederobien.messenger.event.ProtocolConnectionDisposedEvent;
import fr.pederobien.messenger.event.ProtocolConnectionEnableChangeEvent;
import fr.pederobien.messenger.event.ProtocolConnectionLostEvent;
import fr.pederobien.messenger.event.ProtocolConnectionUnstableEvent;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class ProtocolConnection implements IProtocolConnection, IEventListener {
    private final IConnection connection;

    /**
     * Creates a connection associated to a protocol.
     *
     * @param connection The connection used to send message to the remote.
     */
    public ProtocolConnection(IConnection connection) {
        this.connection = connection;

        EventManager.registerListener(this);
    }

    @Override
    public boolean isEnabled() {
        return connection.isEnabled();
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        connection.setEnabled(isEnabled);
    }

    @Override
    public void dispose() {
        connection.dispose();
    }

    @Override
    public boolean isDisposed() {
        return connection.isDisposed();
    }

    @Override
    public void send(IRequestMessage request) {
        connection.send(createMessage(request));
    }

    @Override
    public void answer(int messageID, IRequestMessage response) {
        connection.answer(messageID, createMessage(response));
    }

    @Override
    public String toString() {
        return connection.toString();
    }

    /**
     * Creates a Message to send to the remote.
     *
     * @param request The message that contains data to create the message to send.
     * @return The message to send.
     */
    private IMessage createMessage(IRequestMessage request) {
        return new Message(request.getBytes(), request.isSync(), request.getTimeout(), request.getCallback());
    }

    @EventHandler
    private void onConnectionLost(ConnectionLostEvent event) {
        if (event.getConnection() != connection)
            return;

        EventManager.callEvent(new ProtocolConnectionLostEvent(this));
    }

    @EventHandler
    private void onConnectionEnableChange(ConnectionEnableChangedEvent event) {
        if (event.getConnection() != connection)
            return;

        EventManager.callEvent(new ProtocolConnectionEnableChangeEvent(this, event.isEnabled()));
    }

    @EventHandler
    private void onConnectionDisposedEvent(ConnectionDisposedEvent event) {
        if (event.getConnection() != connection)
            return;

        EventManager.callEvent(new ProtocolConnectionDisposedEvent(this));
    }

    @EventHandler
    private void onConnectionUnstableEvent(ConnectionUnstableEvent event) {
        if (event.getConnection() != connection)
            return;

        EventManager.callEvent(new ProtocolConnectionUnstableEvent(this));
    }
}
