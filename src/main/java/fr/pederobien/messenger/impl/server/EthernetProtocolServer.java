package fr.pederobien.messenger.impl.server;

import fr.pederobien.communication.event.NewClientEvent;
import fr.pederobien.communication.event.ServerCloseEvent;
import fr.pederobien.communication.event.ServerDisposeEvent;
import fr.pederobien.communication.event.ServerOpenEvent;
import fr.pederobien.communication.event.ServerUnstableEvent;
import fr.pederobien.communication.impl.Communication;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.interfaces.server.IEthernetServer;
import fr.pederobien.communication.interfaces.server.IEthernetServerImpl;
import fr.pederobien.communication.interfaces.server.IServerEthernetEndPoint;
import fr.pederobien.communication.interfaces.server.IServerImpl;
import fr.pederobien.messenger.event.NewProtocolClientEvent;
import fr.pederobien.messenger.event.ProtocolServerCloseEvent;
import fr.pederobien.messenger.event.ProtocolServerDisposeEvent;
import fr.pederobien.messenger.event.ProtocolServerOpenEvent;
import fr.pederobien.messenger.event.ProtocolServerUnstableEvent;
import fr.pederobien.messenger.interfaces.server.IEthernetProtocolServer;
import fr.pederobien.messenger.interfaces.server.IEthernetProtocolServerConfig;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class EthernetProtocolServer implements IEthernetProtocolServer, IEventListener {
	private final IProtocolServerConfig<IServerEthernetEndPoint, IEthernetEndPoint> config;
	private final IEthernetServer server;

	/**
	 * Creates an ethernet server associated to a protocol.
	 *
	 * @param config The configuration that contains server parameters.
	 * @param impl   The server implementation.
	 */
	public EthernetProtocolServer(IProtocolServerConfig<IServerEthernetEndPoint, IEthernetEndPoint> config,
			IServerImpl<IServerEthernetEndPoint, IEthernetEndPoint> impl) {
		this.config = config;

		server = Communication.createEthernetServer(config, impl);
		EventManager.registerListener(this);
	}

	/**
	 * Creates an ethernet server associated to a protocol.
	 *
	 * @param config The configuration that contains server parameters.
	 * @param impl   The server implementation.
	 */
	public EthernetProtocolServer(IEthernetProtocolServerConfig config, IEthernetServerImpl impl) {
		this.config = config;

		server = Communication.createEthernetServer(config, impl);
		EventManager.registerListener(this);
	}

	@Override
	public boolean open() {
		return server.open();
	}

	@Override
	public boolean close() {
		return server.close();
	}

	@Override
	public boolean dispose() {
		return server.dispose();
	}

	@Override
	public boolean isOpened() {
		return server.isOpened();
	}

	@Override
	public int getPort() {
		return server.getPort();
	}

	@Override
	public boolean isDisposed() {
		return server.isDisposed();
	}

	@Override
	public String toString() {
		return server.toString();
	}

	@EventHandler
	private void onNewClientConnected(NewClientEvent event) {
		if (event.getServer() != server)
			return;

		IProtocolClient client = new ProtocolClient<IServerEthernetEndPoint, IEthernetEndPoint>(config, event.getConnection());
		EventManager.callEvent(new NewProtocolClientEvent(this, client));
	}

	@EventHandler
	private void onServerOpen(ServerOpenEvent event) {
		if (event.getServer() != server)
			return;

		EventManager.callEvent(new ProtocolServerOpenEvent(this));
	}

	@EventHandler
	private void onServerClose(ServerCloseEvent event) {
		if (event.getServer() != server)
			return;

		EventManager.callEvent(new ProtocolServerCloseEvent(this));
	}

	@EventHandler
	private void onServerUnstable(ServerUnstableEvent event) {
		if (event.getServer() != server)
			return;

		EventManager.callEvent(new ProtocolServerUnstableEvent(this));
	}

	@EventHandler
	private void onServerDisposeEvent(ServerDisposeEvent event) {
		if (event.getServer() != server)
			return;

		EventManager.callEvent(new ProtocolServerDisposeEvent(this));

		// Server disposed, no need to listen for server events
		EventManager.unregisterListener(this);
	}
}
