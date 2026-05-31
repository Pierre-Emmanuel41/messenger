package fr.pederobien.messenger.impl.server;

import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.communication.interfaces.server.IServerEthernetEndPoint;
import fr.pederobien.messenger.interfaces.server.IEthernetProtocolServerConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class EthernetProtocolServerConfig extends ProtocolServerConfig<IServerEthernetEndPoint, IEthernetEndPoint> implements IEthernetProtocolServerConfig {

	/**
	 * Creates an Ethernet protocol server configuration.
	 * 
	 * @param manager The manager that contains supported protocols.
	 * @param name    The server name. Essentially used for logging.
	 * @param point   The properties of the server communication point.
	 */
	public EthernetProtocolServerConfig(IProtocolManager manager, String name, IServerEthernetEndPoint point) {
		super(manager, name, point);
	}

}
