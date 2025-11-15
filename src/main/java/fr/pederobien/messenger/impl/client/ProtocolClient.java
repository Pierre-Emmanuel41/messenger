package fr.pederobien.messenger.impl.client;

import fr.pederobien.communication.event.ClientConnectedEvent;
import fr.pederobien.communication.event.ClientUnstableEvent;
import fr.pederobien.communication.event.MessageEvent;
import fr.pederobien.communication.impl.Communication;
import fr.pederobien.communication.interfaces.IMessageHandler;
import fr.pederobien.communication.interfaces.client.IClient;
import fr.pederobien.communication.interfaces.client.IClientConfig;
import fr.pederobien.communication.interfaces.client.IClientImpl;
import fr.pederobien.communication.interfaces.connection.IConnection.Mode;
import fr.pederobien.communication.interfaces.layer.ILayerInitializer;
import fr.pederobien.messenger.event.ProtocolClientConnectedEvent;
import fr.pederobien.messenger.event.ProtocolClientUnstableEvent;
import fr.pederobien.messenger.impl.ProtocolConnection;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestHandler;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.protocol.interfaces.IRequest;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;
import fr.pederobien.utils.event.Logger;

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

		ClientConfigWrapper wrapper = new ClientConfigWrapper(config);
		wrapper.setMessageHandler(this::onMessageReceived);

		client = Communication.createClient(wrapper, impl);
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
		EventManager.callEvent(new ProtocolClientConnectedEvent(this));
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
		if (request == null) {
			debug("Received unsupported request: %s", ByteWrapper.wrap(event.getData()));
			return;
		}

		// Getting the request handler to execute for the specific identifier
		IRequestHandler handler = config.getHandler(request.getIdentifier());
		if (handler == null) {
			debug("No request handler defined");
			return;
		}

		// Applying the action
		handler.apply(connection, event.getIdentifier(), request.getPayload());
	}

	/**
	 * Print a log using DEBUG level.
	 *
	 * @param message The message to print.
	 * @param args    The arguments of the message.
	 */
	private void debug(String message, Object... args) {
		Logger.debug("%s - %s", this, String.format(message, args));
	}

	private class ClientConfigWrapper implements IClientConfig<T> {
		private IProtocolClientConfig<T> source;
		private IMessageHandler handler;

		private ClientConfigWrapper(IProtocolClientConfig<T> source) {
			this.source = source;
		}

		@Override
		public Mode getMode() {
			return source.getMode();
		}

		@Override
		public String getConnectionName() {
			return source.getConnectionName();
		}

		@Override
		public ILayerInitializer getLayerInitializer() {
			return source.getLayerInitializer();
		}

		@Override
		public int getConnectionMaxUnstableCounter() {
			return source.getConnectionMaxUnstableCounter();
		}

		@Override
		public int getConnectionHealTime() {
			return source.getConnectionHealTime();
		}

		@Override
		public T getEndPoint() {
			return source.getEndPoint();
		}

		@Override
		public String getName() {
			return source.getName();
		}

		@Override
		public IMessageHandler getMessageHandler() {
			return handler;
		}

		/**
		 * Set the handler to execute when an unexpected request has been received from the remote. The default handler to nothing.
		 *
		 * @param messageHandler The handler to call.
		 */
		public void setMessageHandler(IMessageHandler handler) {
			this.handler = handler;
		}

		@Override
		public int getConnectionTimeout() {
			return source.getConnectionTimeout();
		}

		@Override
		public boolean isAutomaticReconnection() {
			return source.isAutomaticReconnection();
		}

		@Override
		public int getReconnectionDelay() {
			return source.getReconnectionDelay();
		}

		@Override
		public int getClientMaxUnstableCounter() {
			return source.getClientMaxUnstableCounter();
		}

		@Override
		public int getClientHealTime() {
			return source.getClientHealTime();
		}
	}
}
