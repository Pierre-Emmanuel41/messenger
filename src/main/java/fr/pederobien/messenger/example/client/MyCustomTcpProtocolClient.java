package fr.pederobien.messenger.example.client;

import fr.pederobien.communication.impl.EthernetEndPoint;
import fr.pederobien.communication.impl.layer.AesSafeLayerInitializer;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.testing.tools.SimpleCertificate;
import fr.pederobien.messenger.example.Identifiers;
import fr.pederobien.messenger.example.MyProtocolManager;
import fr.pederobien.messenger.example.wrappers.Player;
import fr.pederobien.messenger.impl.Messenger;
import fr.pederobien.messenger.impl.client.ProtocolClientConfig;
import fr.pederobien.messenger.interfaces.IProtocolConnection;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.utils.event.Logger;

public class MyCustomTcpProtocolClient {
    private final IProtocolClient client;
    private final ProtocolClientConfig<IEthernetEndPoint> config;

    public MyCustomTcpProtocolClient() {
        IEthernetEndPoint endPoint = new EthernetEndPoint("127.0.0.1", 12345);

        config = Messenger.createClientConfig(MyProtocolManager.getInstance(), "My TCP client", endPoint);

        // Setting the layer to use to pack/unpack data.
        config.setLayerInitializer(() -> new AesSafeLayerInitializer(new SimpleCertificate()));

        // If the connection unstable counter reach 10, the connection will be
        // closed automatically
        config.setConnectionMaxUnstableCounter(10);

        // Decrement the value of the connection unstable counter each 100 ms
        config.setConnectionHealTime(100);

        // Time in ms after which a timeout occurs when trying to connect to the server
        config.setConnectionTimeout(5000);

        // The connection wait 1000 ms before retrying to connect with the server
        config.setReconnectionDelay(1000);

        // Value by default is true
        config.setAutomaticReconnection(false);

        // If the client unstable counter reach 2, the connection will be
        // closed automatically and the client will close itself.
        config.setClientMaxUnstableCounter(2);

        // Decrement the value of the client unstable counter each 5 ms
        config.setClientHealTime(5);

        // Adding action to execute when a request has been received
        config.addRequestHandler(Identifiers.STRING_ID.getValue(), this::onStringReceived);
        config.addRequestHandler(Identifiers.INT_ID.getValue(), this::onIntegerReceived);
        config.addRequestHandler(Identifiers.FLOAT_ID.getValue(), this::onFloatReceived);
        config.addRequestHandler(Identifiers.PLAYER_ID.getValue(), this::onPlayerReceived);

        // Creating the client
        client = Messenger.createTcpClient(config);
    }

    /**
     * Attempt the connection with the remote asynchronously
     */
    public void connect() {
        client.connect();
    }

    /**
     * Close the connection with the remote, by calling {@link #connect()} the
     * client can be connected again with the server.
     */
    public void disconnect() {
        client.disconnect();
    }

    /**
     * Definitely close the connection with the server.
     */
    public void dispose() {
        client.dispose();
    }

    /**
     * Send a string to the server.
     *
     * @param payload The string to send to the server
     */
    public void send(String payload) {
        client.getConnection().send(config.getRequest(Identifiers.STRING_ID.getValue(), 0, payload));
    }

    public void send(int payload) {
        IRequestMessage request = config.getRequest(Identifiers.INT_ID.getValue(), 0, payload);

        // The request is sent synchronously
        request.setSync(true);

        // Sending request to server
        client.getConnection().send(request);
    }

    /**
     * Send a float to the server.
     *
     * @param payload The float to send to the server.
     */
    public void send(float payload) {
        IRequestMessage request = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, payload);

        // Callback with default timeout to execute when a response is received from the
        // server
        request.setCallback(args -> {
            if (!args.isTimeout())
                Logger.info("Client received %s", config.parse(args.response()).getPayload());
            else
                Logger.error("[Client] Unexpected timeout occurred");
        });

        // Sending request to server
        client.getConnection().send(request);
    }

    public void send(Player player) {
        IRequestMessage request = config.getRequest(Identifiers.PLAYER_ID.getValue(), 0, player);

        // The request is sent synchronously
        request.setSync(true);

        // Callback to execute when a response is received from the server
        request.setCallback(2000, args -> {
            if (!args.isTimeout()) {
                Logger.info("Client received %s", config.parse(args.response()).getPayload());

                // Answering to server's answer
                IRequestMessage response = config.getRequest(Identifiers.STRING_ID.getValue(), 0, "OK");
                client.getConnection().answer(args.identifier(), response);
            } else
                Logger.error("[Client] Unexpected timeout occurred");
        });

        // Sending request to server
        client.getConnection().send(request);
    }

    private void onStringReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof String)) {
            Logger.error("Technical error happened, expecting String but got %s", payload.getClass());
            return;
        }

        Logger.print("Client received the following String: %s", payload);
    }

    private void onIntegerReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Integer)) {
            Logger.error("Technical error happened, expecting Integer but got %s", payload.getClass());
            return;
        }

        Logger.print("Client received the following Integer: %s", payload);
    }

    private void onFloatReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Float)) {
            Logger.error("Technical error happened, expecting Float but got %s", payload.getClass());
            return;
        }

        Logger.print("Client received the following Float: %s", payload);
    }

    private void onPlayerReceived(IProtocolConnection connection, int messageID, Object payload) {
        if (!(payload instanceof Player)) {
            Logger.error("Technical error happened, expecting Player but got %s", payload.getClass());
            return;
        }

        Logger.print("Client received the following Player: %s", payload);
    }
}
