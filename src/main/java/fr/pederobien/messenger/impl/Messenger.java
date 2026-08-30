package fr.pederobien.messenger.impl;

import fr.pederobien.communication.impl.EthernetEndPoint;
import fr.pederobien.communication.impl.client.ethernet.TcpClientImpl;
import fr.pederobien.communication.impl.client.ethernet.UdpClientImpl;
import fr.pederobien.communication.impl.server.ethernet.ServerEthernetEndPoint;
import fr.pederobien.communication.impl.server.ethernet.TcpServerImpl;
import fr.pederobien.communication.impl.server.ethernet.UdpServerImpl;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.interfaces.client.IClientImpl;
import fr.pederobien.communication.interfaces.client.IEthernetClientImpl;
import fr.pederobien.communication.interfaces.server.IEthernetServerImpl;
import fr.pederobien.communication.interfaces.server.IServerEthernetEndPoint;
import fr.pederobien.communication.interfaces.server.IServerImpl;
import fr.pederobien.messenger.impl.client.EthernetProtocolClientConfig;
import fr.pederobien.messenger.impl.client.ProtocolClient;
import fr.pederobien.messenger.impl.client.ProtocolClientConfig;
import fr.pederobien.messenger.impl.server.EthernetProtocolServer;
import fr.pederobien.messenger.impl.server.EthernetProtocolServerConfig;
import fr.pederobien.messenger.impl.server.ProtocolServer;
import fr.pederobien.messenger.impl.server.ProtocolServerConfig;
import fr.pederobien.messenger.interfaces.client.IEthernetProtocolClientConfig;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.messenger.interfaces.client.IProtocolClientConfig;
import fr.pederobien.messenger.interfaces.server.IEthernetProtocolServer;
import fr.pederobien.messenger.interfaces.server.IEthernetProtocolServerConfig;
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
	public static final <T> ProtocolClientConfig<T> createClientConfig(IProtocolManager manager, String name, T endPoint) {
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
	 * Creates an Ethernet client configuration associated to a protocol manager.
	 *
	 * @param manager  The manager that contains supported protocols.
	 * @param name     The client name. Essentially used for logging.
	 * @param endPoint The object that gather remote information.
	 */
	public static final EthernetProtocolClientConfig createEthernetClientConfig(IProtocolManager manager, String name, IEthernetEndPoint endPoint) {
		return new EthernetProtocolClientConfig(manager, name, endPoint);
	}

	/**
	 * Creates an Ethernet client associated to a protocol.
	 *
	 * @param config The client configuration.
	 * @param impl   The client implementation.
	 */
	public static final IProtocolClient createEthernetClient(IEthernetProtocolClientConfig config, IEthernetClientImpl impl) {
		return createClient(config, impl);
	}

	/**
	 * Create a client with a TCP connection ready to be connected to a remote.
	 *
	 * @param config The object that holds the client configuration.
	 */
	public static final IProtocolClient createTcpClient(IEthernetProtocolClientConfig config) {
		return createClient(config, new TcpClientImpl());
	}

	/**
	 * Create a client with a UDP connection ready to be connected to a remote.
	 *
	 * @param config The object that holds the client configuration.
	 */
	public static final IProtocolClient createUdpClient(IEthernetProtocolClientConfig config) {
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
		return createTcpClient(createEthernetClientConfig(manager, "TCP client", new EthernetEndPoint(address, port)));
	}

	/**
	 * Creates a client with a UDP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param address The server's IP address.
	 * @param port    The server's port number.
	 */
	public static final IProtocolClient createDefaultUdpClient(IProtocolManager manager, String address, int port) {
		return createUdpClient(createEthernetClientConfig(manager, "UDP Client", new EthernetEndPoint(address, port)));
	}

	/**
	 * Creates a server configuration associated to a protocol manager.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param name    The server name. Essentially used for logging.
	 * @param point   The object that gather server communication point.
	 */
	public static final <T, U> ProtocolServerConfig<T, U> createServerConfig(IProtocolManager manager, String name, T point) {
		return new ProtocolServerConfig<T, U>(manager, name, point);
	}

	/**
	 * Creates a server associated to a protocol.
	 *
	 * @param config The server configuration.
	 * @param impl   The server implementation.
	 */
	public static final <T, U> IProtocolServer createServer(IProtocolServerConfig<T, U> config, IServerImpl<T, U> impl) {
		return new ProtocolServer<T, U>(config, impl);
	}

	/**
	 * Creates a server configuration associated to a protocol manager.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param name    The server name. Essentially used for logging.
	 * @param point   The object that gather server communication point.
	 */
	public static final EthernetProtocolServerConfig createEthernetServerConfig(IProtocolManager manager, String name, IServerEthernetEndPoint point) {
		return new EthernetProtocolServerConfig(manager, name, point);
	}

	/**
	 * Creates an ethernet server associated to a protocol.
	 *
	 * @param config The server configuration.
	 * @param impl   The server implementation.
	 */
	public static final IEthernetProtocolServer createEthernetServer(IEthernetProtocolServerConfig config, IEthernetServerImpl impl) {
		return new EthernetProtocolServer(config, impl);
	}

	/**
	 * Create a server with a TCP connection ready to be connected to a remote.
	 *
	 * @param config The object that holds the server configuration.
	 */
	public static final IEthernetProtocolServer createTcpServer(IEthernetProtocolServerConfig config) {
		return createEthernetServer(config, new TcpServerImpl());
	}

	/**
	 * Create a server with a UDP connection ready to be connected to a remote.
	 *
	 * @param config The object that holds the server configuration.
	 */
	public static final IEthernetProtocolServer createUdpServer(IEthernetProtocolServerConfig config) {
		return createEthernetServer(config, new UdpServerImpl());
	}

	/**
	 * Creates a server with a TCP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param address The server's IP address.
	 * @param port    The server's port number.
	 */
	public static final IEthernetProtocolServer createDefaultTcpServer(IProtocolManager manager, String address, int port) {
		return createTcpServer(createEthernetServerConfig(manager, "TCP Server", new ServerEthernetEndPoint(address, port)));
	}

	/**
	 * Creates a server with a TCP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param port    The server's port number.
	 */
	public static final IEthernetProtocolServer createDefaultTcpServer(IProtocolManager manager, int port) {
		return createTcpServer(createEthernetServerConfig(manager, "TCP Server", new ServerEthernetEndPoint(port)));
	}

	/**
	 * Creates a server with a TCP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param address The server's IP address.
	 * @param min     The minimum value of the port number of the server.
	 * @param max     The maximum value of the port number of the server.
	 */
	public static final IEthernetProtocolServer createDefaultTcpServer(IProtocolManager manager, String address, int min, int max) {
		return createTcpServer(createEthernetServerConfig(manager, "TCP Server", new ServerEthernetEndPoint(address, min, max)));
	}

	/**
	 * Creates a server with a TCP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param min     The minimum value of the port number of the server.
	 * @param max     The maximum value of the port number of the server.
	 */
	public static final IEthernetProtocolServer createDefaultTcpServer(IProtocolManager manager, int min, int max) {
		return createTcpServer(createEthernetServerConfig(manager, "TCP Server", new ServerEthernetEndPoint(min, max)));
	}

	/**
	 * Creates a server with a UDP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param address The server's IP address.
	 * @param port    The server's port number.
	 */
	public static final IEthernetProtocolServer createDefaultUdpServer(IProtocolManager manager, String address, int port) {
		return createUdpServer(createEthernetServerConfig(manager, "UDP server", new ServerEthernetEndPoint(address, port)));
	}

	/**
	 * Creates a server with a UDP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param port    The server's port number.
	 */
	public static final IEthernetProtocolServer createDefaultUdpServer(IProtocolManager manager, int port) {
		return createUdpServer(createEthernetServerConfig(manager, "UDP server", new ServerEthernetEndPoint(port)));
	}

	/**
	 * Creates a server with a UDP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param address The server's IP address.
	 * @param min     The minimum value of the port number of the server.
	 * @param max     The maximum value of the port number of the server.
	 */
	public static final IEthernetProtocolServer createDefaultUdpServer(IProtocolManager manager, String address, int min, int max) {
		return createUdpServer(createEthernetServerConfig(manager, "UDP server", new ServerEthernetEndPoint(address, min, max)));
	}

	/**
	 * Creates a server with a UDP connection ready to be connected to a remote.
	 *
	 * @param manager The manager that contains supported protocols.
	 * @param min     The minimum value of the port number of the server.
	 * @param max     The maximum value of the port number of the server.
	 */
	public static final IEthernetProtocolServer createDefaultUdpServer(IProtocolManager manager, int min, int max) {
		return createUdpServer(createEthernetServerConfig(manager, "UDP server", new ServerEthernetEndPoint(min, max)));
	}
}
