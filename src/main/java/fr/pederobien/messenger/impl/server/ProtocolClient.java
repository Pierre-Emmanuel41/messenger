package fr.pederobien.messenger.impl.server;

import fr.pederobien.communication.event.MessageEvent;
import fr.pederobien.communication.interfaces.connection.IConnection;
import fr.pederobien.messenger.impl.ProtocolConnection;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IIdentifier;
import fr.pederobien.protocol.interfaces.IRequest;

import java.util.HashMap;
import java.util.Map;

public class ProtocolClient implements IProtocolClient {
    private final IProtocolServerConfig<?> config;
    private final IProtocolConnection connection;
    private final Map<IIdentifier, PrivilegeRequestHandler> handlers;
    private int privilege;

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

        handlers = new HashMap<IIdentifier, PrivilegeRequestHandler>();
        privilege = config.getPrivilege();

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
    public void addRequestHandler(int privilege, IIdentifier identifier, IRequestHandler handler) {
        handlers.put(identifier, new PrivilegeRequestHandler(privilege, handler));
    }

    @Override
    public IProtocolConnection getConnection() {
        return connection;
    }

    @Override
    public int getPrivilege() {
        return privilege;
    }

    @Override
    public void setPrivilege(int privilege) {
        this.privilege = privilege;
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
        PrivilegeRequestHandler handler = handlers.get(request.getIdentifier());
        if (handler == null)
            return;

        // Checking client privilege
        if (privilege < handler.privilege()) {
            // Request is denied, client's privilege are not high enough
            config.getDeniedRequestHandler().apply(connection, event.getIdentifier(), this, handler.privilege(), request);
        } else {
            // Applying the action
            handler.apply(connection, event.getIdentifier(), request.getPayload());
        }
    }

    private record PrivilegeRequestHandler(int privilege, IRequestHandler handler) {

        public PrivilegeRequestHandler {

        }

        @Override
        public int privilege() {
            return privilege;
        }

        public void apply(IProtocolConnection connection, int messageID, Object payload) {
            handler.apply(connection, messageID, payload);
        }
    }
}
