package fr.pederobien.messenger.impl.client;

import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.messenger.interfaces.client.IEthernetProtocolClientConfig;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class EthernetProtocolClientConfig extends ProtocolClientConfig<IEthernetEndPoint> implements IEthernetProtocolClientConfig {

	/**
	 * Creates an Ethernet protocol client configuration.
	 * 
	 * @param manager  The manager that contains supported protocols.
	 * @param name     The client name. Essentially used for logging.
	 * @param endPoint The object that gather remote information.
	 */
	public EthernetProtocolClientConfig(IProtocolManager manager, String name, IEthernetEndPoint endPoint) {
		super(manager, name, endPoint);
	}

}
