package fr.pederobien.messenger.interfaces.server;

public interface IEthernetProtocolServer extends IProtocolServer {

	/**
	 * @return The port number this server is using. -1 if the server is not yet opened or has been closed.
	 */
	int getPort();
}
