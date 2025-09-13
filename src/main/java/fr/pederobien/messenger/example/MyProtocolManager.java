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
            protocol.register(Identifiers.STRING_ID, new StringWrapperV10());
            protocol.register(Identifiers.INT_ID, new IntWrapperV10());

            protocol = INSTANCE.getOrCreate(2.0f);
            protocol.register(Identifiers.FLOAT_ID, new FloatWrapperV20());
            protocol.register(Identifiers.PLAYER_ID, new PlayerWrapperV20());

            INSTANCE.registerErrors(Errors.values());
        }
    }
}
