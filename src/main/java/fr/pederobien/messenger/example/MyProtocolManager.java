package fr.pederobien.messenger.example;

import fr.pederobien.messenger.example.wrappers.FloatWrapperV20;
import fr.pederobien.messenger.example.wrappers.IntWrapperV10;
import fr.pederobien.messenger.example.wrappers.PlayerWrapperV20;
import fr.pederobien.messenger.example.wrappers.StringWrapperV10;
import fr.pederobien.protocol.impl.ProtocolManager;
import fr.pederobien.protocol.interfaces.IProtocol;
import fr.pederobien.protocol.interfaces.IProtocolManager;

public class MyProtocolManager {

    private MyProtocolManager() {
        // Do nothing
    }

    /**
     * @return The protocol manager that gather supported protocols / requests.
     */
    public static IProtocolManager getInstance() {
        return ProtocolManagerSingleton.INSTANCE;
    }

    private static class ProtocolManagerSingleton {
        private static final IProtocolManager INSTANCE = new ProtocolManager();

        static {
            IProtocol protocol = INSTANCE.getOrCreate(1.0f);
            protocol.register(Identifiers.STRING_ID.getValue(), new StringWrapperV10());
            protocol.register(Identifiers.INT_ID.getValue(), new IntWrapperV10());

            protocol = INSTANCE.getOrCreate(2.0f);
            protocol.register(Identifiers.FLOAT_ID.getValue(), new FloatWrapperV20());
            protocol.register(Identifiers.PLAYER_ID.getValue(), new PlayerWrapperV20());
        }
    }
}
