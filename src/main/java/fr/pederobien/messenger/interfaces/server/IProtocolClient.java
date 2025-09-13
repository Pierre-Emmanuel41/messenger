package fr.pederobien.messenger.interfaces.server;

import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.protocol.interfaces.IIdentifier;

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
     * @param privilege  The minimum privilege level the client shall have to process the request.
     * @param identifier The request identifier.
     * @param handler    The handler to execute.
     */
    void addRequestHandler(int privilege, IIdentifier identifier, IRequestHandler handler);

    /**
     * @return The connection to send /receive requests from the server.
     */
    IProtocolConnection getConnection();

    /**
     * @return The privilege level of this client. The higher it is, the less restriction there are to process requests.
     */
    int getPrivilege();

    /**
     * Set the privilege level of this client. By modifying this level, the client may be restricted to process some
     * requests.
     *
     * @param privilege The privilege level of this client.
     */
    void setPrivilege(int privilege);
}
