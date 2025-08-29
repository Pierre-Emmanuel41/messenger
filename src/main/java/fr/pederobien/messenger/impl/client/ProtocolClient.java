package fr.pederobien.messenger.impl.client;

import fr.pederobien.communication.event.ClientConnectedEvent;
import fr.pederobien.communication.event.ClientUnstableEvent;
import fr.pederobien.communication.event.MessageEvent;
import fr.pederobien.communication.impl.ClientConfig;
import fr.pederobien.communication.impl.Communication;
import fr.pederobien.communication.interfaces.client.IClient;
import fr.pederobien.communication.interfaces.client.IClientImpl;
import fr.pederobien.messenger.event.ProtocolClientUnstableEvent;
import fr.pederobien.messenger.impl.ProtocolConnection;
import fr.pederobien.messenger.interfaces.IAction;
import fr.pederobien.messenger.interfaces.IAction.ActionArgs;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.protocol.interfaces.IRequest;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class ProtocolClient<T> implements IProtocolClient, IEventListener {
	private final IProtocolClientConfig<T> config;
	private final IClient client;
	private IProtocolConnection connection;

	/**
	 * Creates a client associated to a protocol.
	 * 
	 * @param config The client configuration.
	 * @param impl   The client implementation.
	 */
	public ProtocolClient(IProtocolClientConfig<T> config, IClientImpl<T> impl) {
		this.config = config;

		// Creating a configuration for the client to connect to the server
		ClientConfig<T> clientConfig = Communication.createClientConfig(config.getName(), config.getEndPoint());
		clientConfig.setLayerInitializer(config.getLayerInitializer());
		clientConfig.setConnectionMaxUnstableCounter(config.getConnectionMaxUnstableCounter());
		clientConfig.setConnectionHealTime(config.getConnectionHealTime());
		clientConfig.setConnectionTimeout(config.getConnectionTimeout());
		clientConfig.setAutomaticReconnection(config.isAutomaticReconnection());
		clientConfig.setReconnectionDelay(config.getReconnectionDelay());
		clientConfig.setClientMaxUnstableCounter(config.getClientMaxUnstableCounter());
		clientConfig.setMessageHandler(this::onMessageReceived);

		client = Communication.createClient(clientConfig, impl);
	}

	@Override
	public void connect() {
		EventManager.registerListener(this);

		client.connect();
	}

	@Override
	public void disconnect() {
		client.disconnect();
	}

	@Override
	public void dispose() {
		client.dispose();
	}

	@Override
	public boolean isDisposed() {
		return client.isDisposed();
	}

	@Override
	public IProtocolConnection getConnection() {
		return connection;
	}

	@Override
	public String toString() {
		return client.toString();
	}

	@EventHandler
	private void onConnectionComplete(ClientConnectedEvent event) {
		if (event.getClient() != client)
			return;

		connection = new ProtocolConnection(client.getConnection());
	}

	@EventHandler
	private void onClientUnstable(ClientUnstableEvent event) {
		if (event.getClient() != client)
			return;

		EventManager.callEvent(new ProtocolClientUnstableEvent(this));
	}

	/**
	 * Method called when an unexpected message has been received from the server.
	 * 
	 * @param event The event that contains the data.
	 */
	private void onMessageReceived(MessageEvent event) {
		// Parsing server request
		IRequest request = config.parse(event.getData());
		if (request == null)
			return;

		// Getting action to execute for the specific identifier
		IAction action = config.getAction(request.getIdentifier());
		if (action == null)
			return;

		// Applying the action
		action.apply(new ActionArgs(connection, event.getIdentifier(), action));
	}
}
