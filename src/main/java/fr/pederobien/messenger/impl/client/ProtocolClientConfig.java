package fr.pederobien.messenger.impl.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import fr.pederobien.communication.impl.layer.LayerInitializer;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.messenger.impl.ProtocolConfiguration;
import fr.pederobien.messenger.interfaces.IAction;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class ProtocolClientConfig<T> extends ProtocolConfiguration implements IProtocolClientConfig<T> {
	private final String name;
	private final T endPoint;
	private final Map<Integer, IAction> actions;
	private Supplier<ILayerInitializer> layerInitializer;
	private int connectionMaxUnstableCounter;
	private int connectionHealTime;
	private int connectionTimeout;
	private boolean automaticReconnection;
	private int reconnectionDelay;
	private int clientMaxUnstableCounter;
	private int clientHealTime;

	/**
	 * Creates a client configuration associated to a protocol manager.
	 * 
	 * @param manager  The manager that contains supported protocols.
	 * @param name     The client name. Essentially used for logging.
	 * @param endPoint The object that gather remote information.
	 */
	public ProtocolClientConfig(IProtocolManager manager, String name, T endPoint) {
		super(manager);

		this.name = name;
		this.endPoint = endPoint;

		actions = new HashMap<Integer, IAction>();
		layerInitializer = () -> new LayerInitializer();
		connectionMaxUnstableCounter = 10;
		connectionHealTime = 1000;
		connectionTimeout = 500;
		automaticReconnection = true;
		reconnectionDelay = 500;
		clientMaxUnstableCounter = 5;
		clientHealTime = 1000;
	}

	@Override
	public T getEndPoint() {
		return endPoint;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void register(int identifier, IAction action) {
		actions.put(identifier, action);
	}

	@Override
	public IAction getAction(int identifier) {
		return actions.get(identifier);
	}

	@Override
	public Supplier<ILayerInitializer> getLayerInitializer() {
		return layerInitializer;
	}

	/**
	 * Set how a layer must be initialized.
	 * 
	 * @param layerInitializer The initialisation sequence.
	 */
	public void setLayerInitializer(Supplier<ILayerInitializer> layerInitializer) {
		this.layerInitializer = layerInitializer;
	}

	@Override
	public int getConnectionMaxUnstableCounter() {
		return connectionMaxUnstableCounter;
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a
	 * counter is incremented automatically. The connection max counter value is the
	 * maximum value the unstable counter can reach before throwing a connection
	 * unstable event.
	 * 
	 * @param connectionMaxUnstableCounter The maximum value the connection's
	 *                                     unstable counter can reach.
	 */
	public void setConnectionMaxUnstableCounter(int connectionMaxUnstableCounter) {
		this.connectionMaxUnstableCounter = connectionMaxUnstableCounter;
	}

	@Override
	public int getConnectionHealTime() {
		return connectionHealTime;
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a
	 * counter is incremented automatically. During the connection lifetime, it is
	 * likely possible that the connection become unstable. However, if the
	 * connection is stable the counter value should be 0 as no error happened for a
	 * long time. The heal time, in milliseconds, is the time after which the
	 * connection's error counter is decremented.
	 * 
	 * @param connectionHealTime The time, in ms, after which the connection's error
	 *                           counter is decremented.
	 */
	public void setConnectionHealTime(int connectionHealTime) {
		this.connectionHealTime = connectionHealTime;
	}

	@Override
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Set the timeout value, in ms, when client attempt to connect to the remote.
	 * The default value 500ms
	 * 
	 * @param connectionTimeout The timeout in ms.
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public boolean isAutomaticReconnection() {
		return automaticReconnection;
	}

	/**
	 * Set if the client should automatically reconnect if a network error occurs.
	 * The default value is true.
	 * 
	 * @param automaticReconnection True to automatically reconnect, false
	 *                              otherwise.
	 */
	public void setAutomaticReconnection(boolean automaticReconnection) {
		this.automaticReconnection = automaticReconnection;
	}

	@Override
	public int getReconnectionDelay() {
		return reconnectionDelay;
	}

	/**
	 * Set the time, in ms, to wait before the client should try to reconnect with
	 * the server. The default value is 500ms
	 * 
	 * @param reconnectionDelay The time in ms.
	 */
	public void setReconnectionDelay(int reconnectionDelay) {
		this.reconnectionDelay = reconnectionDelay;
	}

	@Override
	public int getClientMaxUnstableCounter() {
		return clientMaxUnstableCounter;
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a
	 * counter is incremented automatically. The client max counter value is the
	 * maximum value the unstable counter can reach before throwing a client
	 * unstable event. This counter is incremented each time a connection unstable
	 * event is thrown.
	 * 
	 * @param clientMaxUnstableCounter The maximum value the client's unstable
	 *                                 counter can reach.
	 */
	public void setClientMaxUnstableCounter(int clientMaxUnstableCounter) {
		this.clientMaxUnstableCounter = clientMaxUnstableCounter;
	}

	@Override
	public int getClientHealTime() {
		return clientHealTime;
	}

	/**
	 * The connection to the remote is monitored so that if an error is happening, a
	 * counter is incremented automatically. During the connection lifetime, it is
	 * likely possible that the connection become unstable. However, if the
	 * connection is stable the counter value should be 0 as no error happened for a
	 * long time. The heal time, in milliseconds, is the time after which the
	 * client's error counter is decremented.
	 */
	public void setClientHealTime(int clientHealTime) {
		this.clientHealTime = clientHealTime;
	}
}
