package fr.pederobien.messenger.interfaces.client;

import fr.pederobien.communication.interfaces.connection.IConnection.Mode;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.messenger.interfaces.IProtocolConfiguration;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.protocol.interfaces.IIdentifier;

import java.util.function.Supplier;

public interface IProtocolClientConfig<T> extends IProtocolConfiguration {

    /**
     * @return The direction of the communication.
     */
    default Mode getMode() {
        return Mode.CLIENT_TO_SERVER;
    }

    /**
     * @return The object that gather remote information.
     */
    T getEndPoint();

    /**
     * @return The client's name. Essentially used for logging.
     */
    String getName();

    /**
     * Register a request handler to execute when an unexpected request is received from the
     * server.
     *
     * @param identifier The request identifier.
     * @param handler    The action to execute when a message has been received.
     */
    void addRequestHandler(IIdentifier identifier, IRequestHandler handler);

    /**
     * Get the action associated to the given identifier.
     *
     * @param identifier The identifier used to get its associated action.
     * @return The handler to execute, if it exists, null otherwise.
     */
    IRequestHandler getHandler(IIdentifier identifier);

    /**
     * @return An object that specify how a layer must be initialized.
     */
    Supplier<ILayerInitializer> getLayerInitializer();

    /**
     * The connection to the remote is monitored so that if an error is happening, a
     * counter is incremented automatically. The connection max counter value is the
     * maximum value the unstable counter can reach before throwing a connection
     * unstable event.
     *
     * @return The maximum value the connection's unstable counter can reach.
     */
    int getConnectionMaxUnstableCounter();

    /**
     * The connection to the remote is monitored so that if an error is happening, a
     * counter is incremented automatically. During the connection lifetime, it is
     * likely possible that the connection become unstable. However, if the
     * connection is stable the counter value should be 0 as no error happened for a
     * long time. The heal time, in milliseconds, is the time after which the
     * connection's error counter is decremented.
     *
     * @return The time, in ms, after which the connection's error counter is
     * decremented.
     */
    int getConnectionHealTime();

    /**
     * @return The value considered as a timeout in ms the client tries to connect
     * to a server. The default value is 1000 ms.
     */
    int getConnectionTimeout();

    /**
     * @return True if the client should try to reconnect automatically with the
     * server if an error occurred. The default value is true.
     */
    boolean isAutomaticReconnection();

    /**
     * @return The delay in ms before trying to reconnect to the server. The default
     * value is 1000 ms.
     */
    int getReconnectionDelay();

    /**
     * The connection to the remote is monitored so that if an error is happening, a
     * counter is incremented automatically. The client max counter value is the
     * maximum value the unstable counter can reach before throwing a client
     * unstable event. This counter is incremented each time a connection unstable
     * event is thrown.
     *
     * @return The maximum value the client's unstable counter can reach.
     */
    int getClientMaxUnstableCounter();

    /**
     * The connection to the remote is monitored so that if an error is happening, a
     * counter is incremented automatically. During the connection lifetime, it is
     * likely possible that the connection become unstable. However, if the
     * connection is stable the counter value should be 0 as no error happened for a
     * long time. The heal time, in milliseconds, is the time after which the
     * client's error counter is decremented.
     *
     * @return The time, in ms, after which the client's error counter is
     * decremented.
     */
    int getClientHealTime();
}
