package fr.pederobien.messenger.interfaces.server;

import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;

public interface IProtocolClient {

    /**
     * Dispose this connection. After this, it is impossible to send data to the
     * remote using this connection.
     */
    void dispose();

    /**
     * @return True if the connection is disposed and cannot be used anymore.
     */
    boolean isDisposed();

    /**
     * Register a request handler to execute when an unexpected request is received from the
     * server.
     *
     * @param identifier The request identifier.
     * @param handler    The handler to execute.
     */
    void addRequestHandler(int identifier, IRequestHandler handler);

    /**
     * @return The connection to send /receive requests from the server.
     */
    IProtocolConnection getConnection();
}
