package fr.pederobien.messenger.interfaces.server;

import fr.pederobien.communication.interfaces.server.IServerConfig;
import fr.pederobien.messenger.interfaces.IProtocolConfiguration;

public interface IProtocolServerConfig<T, U> extends IServerConfig<T, U>, IProtocolConfiguration {

}
