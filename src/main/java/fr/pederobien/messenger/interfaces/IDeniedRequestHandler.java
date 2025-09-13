package fr.pederobien.messenger.interfaces;

import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.protocol.interfaces.IRequest;

public interface IDeniedRequestHandler {

    /**
     * Handler to execute when the client's privilege are not high enough to process the request.
     *
     * @param connection       The connection used to answer to the remote.
     * @param messageID        The identifier of the message sent by the remote.
     * @param client           The client, on server side, that received the request.
     * @param requestPrivilege The privilege level of the request.
     * @param request          The request sent by the remote.
     */
    void apply(IProtocolConnection connection, int messageID, IProtocolClient client, int requestPrivilege, IRequest request);
}
