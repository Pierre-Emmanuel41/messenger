package fr.pederobien.messenger.impl.server;

import fr.pederobien.communication.event.MessageEvent;
import fr.pederobien.communication.interfaces.connection.IConnection;
import fr.pederobien.messenger.impl.ProtocolConnection;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IError;
import fr.pederobien.protocol.interfaces.IIdentifier;
import fr.pederobien.protocol.interfaces.IRequest;

import java.util.HashMap;
import java.util.Map;

public class ProtocolClient implements IProtocolClient {
    private final IProtocolServerConfig<?> config;
    private final IProtocolConnection connection;
    private final Map<IIdentifier, IRequestHandler> handlers;

    /**
     * Creates a client, on server side, associated to a protocol and connected to a
     * remote.
     *
     * @param config     The server configuration that gather protocol and supported
     *                   requests.
     * @param connection The connection to with the remote.
     */
    protected ProtocolClient(IProtocolServerConfig<?> config, IConnection connection) {
        this.config = config;
        this.connection = new ProtocolConnection(connection);

        handlers = new HashMap<IIdentifier, IRequestHandler>();

        connection.setMessageHandler(this::onMessageReceived);
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
    public void addRequestHandler(IIdentifier identifier, IRequestHandler handler) {
        handlers.put(identifier, handler);
    }

    @Override
    public IRequest parse(byte[] data) {
        return config.parse(data);
    }

    @Override
    public IRequestMessage getRequest(IIdentifier identifier, IError error, Object payload) {
        return config.getRequest(identifier, error, payload);
    }

    @Override
    public IProtocolConnection getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        return connection.toString();
    }

    /**
     * Method called when an unexpected request has been received from the remote.
     *
     * @param event The event that contains the unexpected request.
     */
    private void onMessageReceived(MessageEvent event) {
        // Parsing client request
        IRequest request = config.parse(event.getData());
        if (request == null)
            return;

        // Getting the handler to execute for the specific identifier
        IRequestHandler handler = handlers.get(request.getIdentifier());
        if (handler == null)
            return;

        // Applying the action
        handler.apply(connection, event.getIdentifier(), request.getPayload());
    }
}
