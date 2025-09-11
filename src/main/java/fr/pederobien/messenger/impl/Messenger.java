package fr.pederobien.messenger.impl;

import fr.pederobien.communication.impl.EthernetEndPoint;
import fr.pederobien.communication.impl.client.TcpClientImpl;
import fr.pederobien.communication.impl.client.UdpClientImpl;
import fr.pederobien.communication.impl.server.TcpServerImpl;
import fr.pederobien.communication.impl.server.UdpServerImpl;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.interfaces.client.IClientImpl;
import fr.pederobien.communication.interfaces.server.IServerImpl;
import fr.pederobien.messenger.impl.client.ProtocolClient;
import fr.pederobien.messenger.impl.client.ProtocolClientConfig;
import fr.pederobien.messenger.impl.server.ProtocolServer;
import fr.pederobien.messenger.impl.server.ProtocolServerConfig;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.messenger.interfaces.server.IProtocolServer;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class Messenger {

    /**
     * Creates a client configuration associated to a protocol manager.
     *
     * @param manager  The manager that contains supported protocols.
     * @param name     The client name. Essentially used for logging.
     * @param endPoint The object that gather remote information.
     */
    public static final <T> ProtocolClientConfig<T> createClientConfig(IProtocolManager manager, String name,
                                                                       T endPoint) {
        return new ProtocolClientConfig<T>(manager, name, endPoint);
    }

    /**
     * Creates a client associated to a protocol.
     *
     * @param config The client configuration.
     * @param impl   The client implementation.
     */
    public static final <T> IProtocolClient createClient(IProtocolClientConfig<T> config, IClientImpl<T> impl) {
        return new ProtocolClient<T>(config, impl);
    }

    /**
     * Create a client with a TCP connection ready to be connected to a remote.
     *
     * @param config The object that holds the client configuration.
     */
    public static final IProtocolClient createTcpClient(IProtocolClientConfig<IEthernetEndPoint> config) {
        return createClient(config, new TcpClientImpl());
    }

    /**
     * Create a client with a UDP connection ready to be connected to a remote.
     *
     * @param config The object that holds the client configuration.
     */
    public static final IProtocolClient createUdpClient(IProtocolClientConfig<IEthernetEndPoint> config) {
        return createClient(config, new UdpClientImpl());
    }

    /**
     * Creates a client with a TCP connection ready to be connected to a remote.
     *
     * @param manager The manager that contains supported protocols.
     * @param address The server's IP address.
     * @param port    The server's port number.
     */
    public static final IProtocolClient createDefaultTcpClient(IProtocolManager manager, String address, int port) {
        return createTcpClient(createClientConfig(manager, "TCP client", new EthernetEndPoint(address, port)));
    }

    /**
     * Creates a client with a UDP connection ready to be connected to a remote.
     *
     * @param manager The manager that contains supported protocols.
     * @param address The server's IP address.
     * @param port    The server's port number.
     */
    public static final IProtocolClient createDefaultUdpClient(IProtocolManager manager, String address, int port) {
        return createUdpClient(createClientConfig(manager, "UDP Client", new EthernetEndPoint(address, port)));
    }

    /**
     * Creates a server configuration associated to a protocol manager.
     *
     * @param manager The manager that contains supported protocols.
     * @param name    The server name. Essentially used for logging.
     * @param point   The object that gather server communication point.
     */
    public static final <T> ProtocolServerConfig<T> createServerConfig(IProtocolManager manager, String name, T point) {
        return new ProtocolServerConfig<T>(manager, name, point);
    }

    /**
     * Creates a server associated to a protocol.
     *
     * @param config The server configuration.
     * @param impl   The server implementation.
     */
    public static final <T> IProtocolServer createServer(IProtocolServerConfig<T> config, IServerImpl<T> impl) {
        return new ProtocolServer<T>(config, impl);
    }

    /**
     * Create a server with a TCP connection ready to be connected to a remote.
     *
     * @param config The object that holds the server configuration.
     */
    public static final IProtocolServer createTcpServer(IProtocolServerConfig<IEthernetEndPoint> config) {
        return createServer(config, new TcpServerImpl());
    }

    /**
     * Create a server with a UDP connection ready to be connected to a remote.
     *
     * @param config The object that holds the server configuration.
     */
    public static final IProtocolServer createUdpServer(IProtocolServerConfig<IEthernetEndPoint> config) {
        return createServer(config, new UdpServerImpl());
    }

    /**
     * Creates a server with a TCP connection ready to be connected to a remote.
     *
     * @param manager The manager that contains supported protocols.
     * @param port    The server's port number.
     */
    public static final IProtocolServer createDefaultTcpServer(IProtocolManager manager, int port) {
        return createTcpServer(createServerConfig(manager, "TCP Server", new EthernetEndPoint(port)));
    }

    /**
     * Creates a server with a UDP connection ready to be connected to a remote.
     *
     * @param manager The manager that contains supported protocols.
     * @param port    The server's port number.
     */
    public static final IProtocolServer createDefaultUdpServer(IProtocolManager manager, int port) {
        return createUdpServer(createServerConfig(manager, "UDP server", new EthernetEndPoint(port)));
    }
}
