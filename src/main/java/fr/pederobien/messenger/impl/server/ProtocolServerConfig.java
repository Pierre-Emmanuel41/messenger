package fr.pederobien.messenger.impl.server;

import java.util.function.Supplier;

import fr.pederobien.communication.impl.Communication;
import fr.pederobien.communication.impl.ServerConfig;
import fr.pederobien.communication.interfaces.connection.IConnection.Mode;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.communication.interfaces.server.IClientValidator;
import fr.pederobien.messenger.impl.ProtocolConfiguration;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class ProtocolServerConfig<T> extends ProtocolConfiguration implements IProtocolServerConfig<T> {
	private final ServerConfig<T> config;

	/**
	 * Creates a server configuration associated to a protocol manager.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param name    The server name. Essentially used for logging.
	 * @param point   The properties of the server communication point.
	 */
	public ProtocolServerConfig(IProtocolManager manager, String name, T point) {
		super(manager);

		config = Communication.createServerConfig(name, point);
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
	public String getName() {
		return config.getName();
	}

	/**
	 * Set the name of the server.
	 * 
	 * @param name The new server's name.
	 */
	public void setName(String name) {
		config.setName(name);
	}

	@Override
	public T getPoint() {
		return config.getPoint();
	}

	@Override
	public IClientValidator<T> getClientValidator() {
		return config.getClientValidator();
	}

	/**
	 * Set the server client validator.
	 *
	 * @param clientValidator The validator to authorize a client to be connected to the server.
	 */
	public void setClientValidator(IClientValidator<T> clientValidator) {
		config.setClientValidator(clientValidator);
	}

	@Override
	public int getServerMaxUnstableCounter() {
		return config.getServerMaxUnstableCounter();
	}

	/**
	 * The server is monitored when waiting for a new client, validating client end-point and initialising the connection with the
	 * remote. During the server lifetime, it is likely possible that the server become unstable. The server's max counter is the
	 * maximum value the unstable counter can reach before throwing a server unstable event and closing the server. This counter is
	 * incremented each time an exception is happening.
	 *
	 * @param serverMaxUnstableCounter The maximum value the server's unstable counter can reach.
	 */
	public void setServerMaxUnstableCounter(int serverMaxUnstableCounter) {
		config.setConnectionMaxUnstableCounter(serverMaxUnstableCounter);
	}

	@Override
	public int getServerHealTime() {
		return config.getServerHealTime();
	}

	/**
	 * The server is monitored when waiting for a new client, validating client end-point and initialising the connection with the
	 * remote. During the server lifetime, it is likely possible that the server become unstable. However, if the server is stable the
	 * unstable counter value should be 0 as no error happened for a long time. The heal time, in milliseconds, is the time after
	 * which the server's error counter is decremented.
	 *
	 * @param serverHealTime The time, in ms, after which the server's error counter is decremented.
	 */
	public void setServerHealTime(int serverHealTime) {
		config.setServerHealTime(serverHealTime);
	}
}
