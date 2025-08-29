package fr.pederobien.messenger.interfaces.server;

import fr.pederobien.messenger.interfaces.IAction;
import fr.pederobien.messenger.interfaces.IProtocolConnection;

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
	 * Register an action to execute when an unexpected request is received from the
	 * server.
	 * 
	 * @param identifier The request identifier.
	 * @param action     The action to execute.
	 */
	void register(int identifier, IAction action);

	/**
	 * @return The connection to send /receive requests from the server.
	 */
	IProtocolConnection getConnection();
}
