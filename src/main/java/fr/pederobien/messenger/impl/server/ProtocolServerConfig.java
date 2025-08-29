package fr.pederobien.messenger.impl.server;

import java.util.function.Supplier;

import fr.pederobien.communication.impl.layer.LayerInitializer;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.communication.interfaces.server.IClientValidator;
import fr.pederobien.messenger.impl.ProtocolConfiguration;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class ProtocolServerConfig<T> extends ProtocolConfiguration implements IProtocolServerConfig<T> {
	private final String name;
	private final T point;
	private Supplier<ILayerInitializer> layerInitializer;
	private int connectionMaxUnstableCounter;
	private int connectionHealTime;
	private IClientValidator<T> clientValidator;
	private int serverMaxUnstableCounter;
	private int serverHealTime;

	/**
	 * Creates a server configuration associated to a protocol manager.
	 * 
	 * @param manager The manager that contains supported protocols.
	 * @param name    The server name. Essentially used for logging.
	 * @param point   The properties of the server communication point.
	 */
	public ProtocolServerConfig(IProtocolManager manager, String name, T point) {
		super(manager);

		this.name = name;
		this.point = point;

		layerInitializer = LayerInitializer::new;
		connectionMaxUnstableCounter = 10;
		connectionHealTime = 1000;
		clientValidator = _ -> true;
		serverMaxUnstableCounter = 5;
		serverHealTime = 1000;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public T getPoint() {
		return point;
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
	public IClientValidator<T> getClientValidator() {
		return clientValidator;
	}

	/**
	 * Set the server client validator.
	 * 
	 * @param clientValidator The validator to authorize a client to be connected to
	 *                        the server.
	 */
	public void setClientValidator(IClientValidator<T> clientValidator) {
		this.clientValidator = clientValidator;
	}

	@Override
	public int getServerMaxUnstableCounter() {
		return serverMaxUnstableCounter;
	}

	/**
	 * The server is monitored when waiting for a new client, validating client
	 * end-point and initialising the connection with the remote. During the server
	 * lifetime, it is likely possible that the server become unstable. The
	 * server's max counter is the maximum value the unstable counter can reach
	 * before throwing a server unstable event and closing the server. This counter
	 * is incremented each time an exception is happening.
	 * 
	 * @param serverMaxUnstableCounter The maximum value the server's unstable
	 *                                 counter can reach.
	 */
	public void setServerMaxUnstableCounter(int serverMaxUnstableCounter) {
		this.serverMaxUnstableCounter = serverMaxUnstableCounter;
	}

	@Override
	public int getServerHealTime() {
		return serverHealTime;
	}

	/**
	 * The server is monitored when waiting for a new client, validating client
	 * end-point and initialising the connection with the remote. During the server
	 * lifetime, it is likely possible that the server become unstable. However, if
	 * the server is stable the unstable counter value should be 0 as no error
	 * happened for a long time. The heal time, in milliseconds, is the time after
	 * which the server's error counter is decremented.
	 * 
	 * @param serverHealTime The time, in ms, after which the server's error counter
	 *                       is decremented.
	 */
	public void setServerHealTime(int serverHealTime) {
		this.serverHealTime = serverHealTime;
	}
}
