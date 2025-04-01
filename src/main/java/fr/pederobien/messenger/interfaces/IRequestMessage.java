package fr.pederobien.messenger.interfaces;

import java.util.function.Consumer;

import fr.pederobien.communication.interfaces.connection.ICallback.CallbackArgs;
import fr.pederobien.protocol.interfaces.IRequest;

public interface IRequestMessage {

	/**
	 * @return The bytes to send to a remote. The identifier does not need to be
	 *         present in the array as it is used for internal purpose.
	 */
	IRequest getRequest();

	/**
	 * @return The bytes array of the message. Equivalent to call
	 *         getRequest().getBytes();
	 */
	byte[] getBytes();

	/**
	 * @return True if this message shall be sent synchronously, false to send it
	 *         asynchronously.
	 */
	boolean isSync();

	/**
	 * Set if the request shall be sent synchronously.
	 * 
	 * @param isSynch True to send the request synchronously, false otherwise.
	 */
	void setSynch(boolean isSynch);

	/**
	 * @return The maximum time, in ms, to wait for remote response.
	 */
	int getTimeout();

	/**
	 * @return The callback to execute once a response has been received from the
	 *         remote.
	 */
	Consumer<CallbackArgs> getCallback();

	/**
	 * Set the request callback to execute once a response has been received from
	 * the server.
	 * 
	 * @param timeout  The maximum time, in ms, to wait for remote response.
	 * @param callback The code to execute once a response has been received or a
	 *                 timeout occurs.
	 */
	void setCallback(int timeout, Consumer<CallbackArgs> callback);

	/**
	 * Set the request callback to execute once a response has been received from
	 * the server. The timeout value is 1000ms.
	 * 
	 * @param callback The code to execute once a response has been received or a
	 *                 timeout occurs.
	 */
	void setCallback(Consumer<CallbackArgs> callback);
}
