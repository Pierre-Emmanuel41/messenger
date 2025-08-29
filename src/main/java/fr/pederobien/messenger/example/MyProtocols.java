package fr.pederobien.messenger.example;

import fr.pederobien.messenger.example.wrappers.FloatWrapperV20;
import fr.pederobien.messenger.example.wrappers.IntWrapperV10;
import fr.pederobien.messenger.example.wrappers.PlayerWrapperV20;
import fr.pederobien.messenger.example.wrappers.StringWrapperV10;
import fr.pederobien.protocol.impl.ProtocolManager;
import fr.pederobien.protocol.interfaces.IProtocolManager;
import fr.pederobien.protocol.interfaces.IProtocol;

public class MyProtocols {
	private static final IProtocolManager MANAGER = new ProtocolManager();

	static {
		addProtocolV10(MANAGER);
		addProtocolV20(MANAGER);
	}

	/**
	 * @return The protocol manager that gather supported protocols / requests.
	 */
	public static IProtocolManager getManager() {
		return MANAGER;
	}

	/**
	 * Register a protocol associated to version 1.0 and register two requests.
	 * 
	 * @param manager The protocol manager to update.
	 */
	private static void addProtocolV10(IProtocolManager manager) {
		IProtocol protocol = manager.getOrCreate(1.0f);
		protocol.register(Identifiers.STRING_ID.getValue(), new StringWrapperV10());
		protocol.register(Identifiers.INT_ID.getValue(), new IntWrapperV10());
	}

	/**
	 * Register a protocol associated to version 2.0 and register two requests.
	 * 
	 * @param manager The protocol manager to update.
	 */
	private static void addProtocolV20(IProtocolManager manager) {
		IProtocol protocol = manager.getOrCreate(2.0f);
		protocol.register(Identifiers.FLOAT_ID.getValue(), new FloatWrapperV20());
		protocol.register(Identifiers.PLAYER_ID.getValue(), new PlayerWrapperV20());
	}
}
