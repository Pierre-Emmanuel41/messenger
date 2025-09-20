package fr.pederobien.messenger.interfaces.server;

import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.protocol.interfaces.IError;
import fr.pederobien.protocol.interfaces.IIdentifier;
import fr.pederobien.protocol.interfaces.IRequest;

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
    void addRequestHandler(IIdentifier identifier, IRequestHandler handler);

    /**
     * Parse the given bytes array. The input array shall have the following
     * format:<br>
     * <p>
     * Byte 0 -> 3: Protocol version<br>
     * Byte 4 -> 7: message identifier<br>
     * Byte 8 -> 11: Error code<br>
     * Byte 12 -> 15: Payload length<br>
     * Byte 15 -> end: Payload
     *
     * @param data The bytes array that contains message information.
     * @return The message corresponding to the content of the bytes array, null if
     * the protocol version is not supported or if the message identifier is
     * not supported for the protocol version.
     */
    IRequest parse(byte[] data);

    /**
     * Creates a request associated to the given identifier, if supported by at
     * least one protocol, and set its error code and payload.
     *
     * @param identifier The request identifier.
     * @param error      The request error.
     * @param payload    The request payload.
     * @return The request ready to be sent to the server or null if the identifier
     * is not supported.
     */
    IRequestMessage getRequest(IIdentifier identifier, IError error, Object payload);

    /**
     * @return The connection to send /receive requests from the server.
     */
    IProtocolConnection getConnection();
}
