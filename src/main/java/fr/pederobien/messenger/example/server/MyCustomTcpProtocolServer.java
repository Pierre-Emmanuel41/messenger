package fr.pederobien.messenger.example.server;

import fr.pederobien.communication.impl.EthernetEndPoint;
import fr.pederobien.communication.impl.layer.AesSafeLayerInitializer;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.testing.tools.SimpleCertificate;
import fr.pederobien.messenger.event.NewProtocolClientEvent;
import fr.pederobien.messenger.example.MyProtocolManager;
import fr.pederobien.messenger.impl.Messenger;
import fr.pederobien.messenger.impl.server.ProtocolServerConfig;
import fr.pederobien.messenger.interfaces.server.IProtocolServer;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyCustomTcpProtocolServer implements IEventListener {
    private final IProtocolServer server;
    private final List<MyCustomTcpProtocolClient> clients;
    private final ProtocolServerConfig<IEthernetEndPoint> config;

    public MyCustomTcpProtocolServer() {
        clients = new ArrayList<MyCustomTcpProtocolClient>();

        config = Messenger.createServerConfig(MyProtocolManager.getInstance(), "My TCP server", new EthernetEndPoint(12345));

        // Setting the layer to use to pack/unpack data.
        // A new layer is defined each time a new client is connected
        config.setLayerInitializer(() -> new AesSafeLayerInitializer(new SimpleCertificate()));

        // If the unstable counter reach 10, the connection will be automatically closed
        config.setConnectionMaxUnstableCounter(10);

        // Decrement the value of the unstable counter each 100 ms
        config.setConnectionHealTime(100);

        // Validate or not if a client is allowed to be connected to the server
        config.setClientValidator(this::validateClient);

        // If the server unstable counter reach 2, the server will be
        // closed automatically as well as each client currently connected.
        config.setServerMaxUnstableCounter(2);

        // Decrement the value of the server unstable counter each 5 ms
        config.setServerHealTime(5);

        server = Messenger.createTcpServer(config);

        EventManager.registerListener(this);
    }

    /**
     * Start the server and wait for a client to be connected.
     *
     * @return true if the server is in correct state to be opened, false otherwise.
     */
    public boolean open() {
        return server.open();
    }

    /**
     * Stop the server, dispose the connection with each client.
     *
     * @return true if the server is in correct state to be closed, false otherwise.
     */
    public boolean close() {
        return server.close();
    }

    /**
     * Dispose this server. It cannot be used anymore.
     *
     * @return true if the has been disposed, false otherwise.
     */
    public boolean dispose() {
        return server.dispose();
    }

    @EventHandler
    private void onNewClientConnected(NewProtocolClientEvent event) {
        if (event.getServer() != server)
            return;

        clients.add(new MyCustomTcpProtocolClient(config, event.getClient()));
    }

    /**
     * Indicates if the client defined by the given end point is allowed to connect
     * with the server.
     *
     * @param endPoint The remote end-point.
     * @return True if the client is allowed, false otherwise.
     */
    private boolean validateClient(IEthernetEndPoint endPoint) {
        // Dummy criteria to check client end-point
        return !Objects.equals(endPoint.getAddress(), "127.0.0.2");
    }
}
