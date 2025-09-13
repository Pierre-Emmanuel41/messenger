package fr.pederobien.messenger.interfaces;

public interface IRequestHandler {

    /**
     * Action to execute when a request has been received from the remote. The input
     * identifier does not correspond to the
     *
     * @param connection The connection that has received the unexpected message from the remote.
     * @param messageID  The identifier of the message received from the remote.
     * @param payload    The payload received from the remote.
     */
    void apply(IProtocolConnection connection, int messageID, Object payload);
}
