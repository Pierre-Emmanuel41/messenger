package fr.pederobien.messenger.interfaces;

public interface IProtocolConnection {

	/**
	 * @return True if the connection can send data to the remote or not. It is
	 *         independent of the connection with the remote.
	 */
	boolean isEnabled();

	/**
	 * Set if this connection can send data to the remote or not. If this connection
	 * is not allowed to send data to the remote, then calling method
	 * {@link #send(IRequestMessage)} will not stores the message.
	 * 
	 * @param isEnabled True to allow this connection to send data to remote.
	 */
	void setEnabled(boolean isEnabled);

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
	 * Send the given request to the remote.
	 * 
	 * @param request The request to send to the remote.
	 */
	void send(IRequestMessage request);

	/**
	 * Answer to a message received from the remote.
	 * 
	 * @param messageID The identifier of the message received from the remote.
	 * @param response  The response to send to the remote.
	 */
	void answer(int messageID, IRequestMessage response);
}
