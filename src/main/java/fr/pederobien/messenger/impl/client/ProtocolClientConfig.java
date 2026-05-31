package fr.pederobien.messenger.impl.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import fr.pederobien.communication.impl.Communication;
import fr.pederobien.communication.impl.client.ClientConfig;
import fr.pederobien.communication.interfaces.connection.IConnection.Mode;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.messenger.impl.ProtocolConfiguration;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.protocol.interfaces.IIdentifier;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class ProtocolClientConfig<T> extends ProtocolConfiguration implements IProtocolClientConfig<T> {
	private final ClientConfig<T> config;
	private final Map<IIdentifier, IRequestHandler> handlers;

	/**
	 * Creates a client configuration associated to a protocol manager.
	 *
	 * @param manager  The manager that contains supported protocols.
	 * @param name     The client name. Essentially used for logging.
	 * @param endPoint The object that gather remote information.
	 */
	public ProtocolClientConfig(IProtocolManager manager, String name, T endPoint) {
		super(manager);

		config = Communication.createClientConfig(name, endPoint);
		handlers = new HashMap<IIdentifier, IRequestHandler>();
	}

	@Override
	public Mode getMode() {
		return config.getMode();
	}

	@Override
	public String getConnectionName() {
		return config.getConnectionName();
	}

	/**
	 * Set the name of the connection with the remote. Essentially used for logging.
	 * 
	 * @param name The connection name.
	 */
	public void setConnectionName(String name) {
		config.setConnectionName(name);
	}

	@Override
	public int getConnectionMaxUnstableCounter() {
		return config.getConnectionMaxUnstableCounter();
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a counter is incremented automatically. The
	 * connection max counter value is the maximum value the unstable counter can reach before throwing a connection unstable event.
	 *
	 * @param connectionMaxUnstableCounter The maximum value the connection's unstable counter can reach.
	 */
	public void setConnectionMaxUnstableCounter(int connectionMaxUnstableCounter) {
		config.setConnectionMaxUnstableCounter(connectionMaxUnstableCounter);
	}

	@Override
	public int getConnectionHealTime() {
		return config.getConnectionHealTime();
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a counter is incremented automatically. During the
	 * connection lifetime, it is likely possible that the connection become unstable. However, if the connection is stable the
	 * counter value should be 0 as no error happened for a long time. The heal time, in milliseconds, is the time after which the
	 * connection's error counter is decremented.
	 *
	 * @param connectionHealTime The time, in ms, after which the connection's error counter is decremented.
	 */
	public void setConnectionHealTime(int connectionHealTime) {
		config.setConnectionHealTime(connectionHealTime);
	}

	@Override
	public int getConnectionTimeout() {
		return config.getConnectionTimeout();
	}

	/**
	 * Set the timeout value, in ms, when client attempt to connect to the remote. The default value 500ms
	 *
	 * @param connectionTimeout The timeout in ms.
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		config.setConnectionTimeout(connectionTimeout);
	}

	@Override
	public boolean isAutomaticReconnection() {
		return config.isAutomaticReconnection();
	}

	/**
	 * Set if the client should automatically reconnect if a network error occurs. The default value is true.
	 *
	 * @param automaticReconnection True to automatically reconnect, false otherwise.
	 */
	public void setAutomaticReconnection(boolean automaticReconnection) {
		config.setAutomaticReconnection(automaticReconnection);
	}

	@Override
	public int getReconnectionDelay() {
		return config.getReconnectionDelay();
	}

	/**
	 * Set the time, in ms, to wait before the client should try to reconnect with the server. The default value is 500ms
	 *
	 * @param reconnectionDelay The time in ms.
	 */
	public void setReconnectionDelay(int reconnectionDelay) {
		config.setReconnectionDelay(reconnectionDelay);
	}

	@Override
	public ILayerInitializer getLayerInitializer() {
		return config.getLayerInitializer();
	}

	/**
	 * Set how a layer must be initialized.
	 *
	 * @param layerInitializer The initialisation sequence.
	 */
	public void setLayerInitializer(Supplier<ILayerInitializer> layerInitializer) {
		config.setLayerInitializer(layerInitializer);
	}

	@Override
	public T getEndPoint() {
		return config.getEndPoint();
	}

	@Override
	public String getName() {
		return config.getName();
	}

	/**
	 * Set the name of this client.
	 * 
	 * @param name The new client's name.
	 */
	public void setName(String name) {
		config.setName(name);
	}

	@Override
	public int getClientMaxUnstableCounter() {
		return config.getClientMaxUnstableCounter();
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a counter is incremented automatically. The client
	 * max counter value is the maximum value the unstable counter can reach before throwing a client unstable event. This counter is
	 * incremented each time a connection unstable event is thrown.
	 *
	 * @param clientMaxUnstableCounter The maximum value the client's unstable counter can reach.
	 */
	public void setClientMaxUnstableCounter(int clientMaxUnstableCounter) {
		config.setClientMaxUnstableCounter(clientMaxUnstableCounter);
	}

	@Override
	public int getClientHealTime() {
		return config.getClientHealTime();
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a counter is incremented automatically. During the
	 * connection lifetime, it is likely possible that the connection become unstable. However, if the connection is stable the
	 * counter value should be 0 as no error happened for a long time. The heal time, in milliseconds, is the time after which the
	 * client's error counter is decremented.
	 */
	public void setClientHealTime(int clientHealTime) {
		config.setClientHealTime(clientHealTime);
	}

	@Override
	public void addRequestHandler(IIdentifier identifier, IRequestHandler handler) {
		handlers.put(identifier, handler);
	}

	@Override
	public IRequestHandler getHandler(IIdentifier identifier) {
		return handlers.get(identifier);
	}
}
