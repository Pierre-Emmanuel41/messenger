package fr.pederobien.messenger.impl.server;

import java.util.HashMap;
import java.util.Map;

import fr.pederobien.communication.event.MessageEvent;
import fr.pederobien.communication.interfaces.connection.IConnection;
import fr.pederobien.messenger.impl.ProtocolConnection;
import fr.pederobien.messenger.interfaces.IAction;
import fr.pederobien.messenger.interfaces.IAction.ActionArgs;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IRequest;

public class ProtocolClient implements IProtocolClient {
	private final IProtocolServerConfig<?> config;
	private final IProtocolConnection connection;
	private final Map<Integer, IAction> actions;

	/**
	 * Creates a client, on server side, associated to a protocol and connected to a
	 * remote.
	 * 
	 * @param config     The server configuration that gather protocol and supported
	 *                   requests.
	 * @param connection The connection to with the remote.
	 */
	protected ProtocolClient(IProtocolServerConfig<?> config, IConnection connection) {
		this.config = config;
		this.connection = new ProtocolConnection(connection);

		connection.setMessageHandler(this::onMessageReceived);

		actions = new HashMap<Integer, IAction>();
	}

	@Override
	public void dispose() {
		connection.dispose();
	}

	@Override
	public boolean isDisposed() {
		return connection.isDisposed();
	}

	@Override
	public void register(int identifier, IAction action) {
		actions.put(identifier, action);
	}

	@Override
	public IProtocolConnection getConnection() {
		return connection;
	}

	@Override
	public String toString() {
		return connection.toString();
	}

	/**
	 * Method called when an unexpected request has been received from the remote.
	 * 
	 * @param event The event that contains the unexpected request.
	 */
	private void onMessageReceived(MessageEvent event) {
		// Parsing client request
		IRequest request = config.parse(event.getData());
		if (request == null)
			return;

		// Getting action to execute for the specific identifier
		IAction action = actions.get(request.getIdentifier());
		if (action == null)
			return;

		// Applying the action
		action.apply(new ActionArgs(connection, event.getIdentifier(), request.getPayload()));
	}
}
