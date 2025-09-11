package fr.pederobien.messenger.example.server;

import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.messenger.example.Identifiers;
import fr.pederobien.messenger.example.wrappers.Player;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.utils.event.Logger;

public class MyCustomTcpProtocolClient {
    private final IProtocolServerConfig<IEthernetEndPoint> config;
    private final IProtocolClient client;

    public MyCustomTcpProtocolClient(IProtocolServerConfig<IEthernetEndPoint> config, IProtocolClient client) {
        this.config = config;
        this.client = client;

        // Adding action to execute when a request has been received
        client.addRequestHandler(Identifiers.STRING_ID.getValue(), this::onStringReceived);
        client.addRequestHandler(Identifiers.INT_ID.getValue(), this::onIntegerReceived);
        client.addRequestHandler(Identifiers.FLOAT_ID.getValue(), this::onFloatReceived);
        client.addRequestHandler(Identifiers.PLAYER_ID.getValue(), this::onPlayerReceived);
    }

    private void onStringReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof String)) {
            Logger.error("Technical error happened, expecting String but got %s", payload.getClass());
            return;
        }

        Logger.info("Server received the following String: %s", payload);
    }

    private void onIntegerReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Integer)) {
            Logger.error("Technical error happened, expecting Integer but got %s", payload.getClass());
            return;
        }

        Logger.info("Server received the following Integer: %s", payload);
    }

    private void onFloatReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Float)) {
            Logger.error("Technical error happened, expecting Float but got %s", payload.getClass());
            return;
        }

        Logger.info("Server received the following Float: %s", payload);

        // Sending a response to the client
        IRequestMessage response = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, 1.0f);

        // Response sent synchronously
        response.setSync(true);

        // Answering to client request
        client.getConnection().answer(messageID, response);
    }

    private void onPlayerReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Player)) {
            Logger.error("Technical error happened, expecting Player but got %s", payload.getClass());
            return;
        }

        Logger.info("Server received the following Player: %s", payload);

        // Sending a response to the client
        IRequestMessage response = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, 3.56f);

        // Response sent synchronously
        response.setSync(true);

        // Callback to execute when a response is received from the client
        response.setCallback(2000, arguments -> {
            if (!arguments.isTimeout()) {
                Object data = config.parse(arguments.response()).getPayload();
                Logger.info("Server received the following response: %s", data);
            } else
                Logger.error("[Server] Unexpected timeout occurred");
        });

        // Answering to client request
        client.getConnection().answer(messageID, response);
    }
}
