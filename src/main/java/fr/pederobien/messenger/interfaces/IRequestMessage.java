package fr.pederobien.messenger.interfaces;

import fr.pederobien.communication.interfaces.connection.ICallback.CallbackArgs;

import java.util.function.Consumer;

public interface IRequestMessage {

    /**
     * @return The version of the communication protocol
     */
    float getVersion();

    /**
     * @return The request identifier.
     */
    int getIdentifier();

    /**
     * @return The request error code value.
     */
    int getErrorCode();

    /**
     * @return The payload object of this request.
     */
    Object getPayload();

    /**
     * @return The bytes array of the message.
     */
    byte[] getBytes();

    /**
     * @return True if this message shall be sent synchronously, false to send it
     * asynchronously.
     */
    boolean isSync();

    /**
     * Set if the request shall be sent synchronously.
     *
     * @param isSynch True to send the request synchronously, false otherwise.
     */
    void setSync(boolean isSynch);

    /**
     * @return The maximum time, in ms, to wait for remote response.
     */
    int getTimeout();

    /**
     * @return The callback to execute once a response has been received from the
     * remote.
     */
    Consumer<CallbackArgs> getCallback();

    /**
     * Set the request callback to execute once a response has been received from
     * the server. The timeout value is 1000ms.
     *
     * @param callback The code to execute once a response has been received or a
     *                 timeout occurs.
     */
    void setCallback(Consumer<CallbackArgs> callback);

    /**
     * Set the request callback to execute once a response has been received from
     * the server.
     *
     * @param timeout  The maximum time, in ms, to wait for remote response.
     * @param callback The code to execute once a response has been received or a
     *                 timeout occurs.
     */
    void setCallback(int timeout, Consumer<CallbackArgs> callback);
}
