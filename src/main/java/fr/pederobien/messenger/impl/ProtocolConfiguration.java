package fr.pederobien.messenger.impl;

import fr.pederobien.communication.interfaces.connection.ICallback.CallbackArgs;
import fr.pederobien.messenger.interfaces.IProtocolConfiguration;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.protocol.interfaces.IProtocolManager;
import fr.pederobien.protocol.interfaces.IRequest;

import java.util.function.Consumer;

public class ProtocolConfiguration implements IProtocolConfiguration {
    private final IProtocolManager manager;

    /**
     * Creates a simple configuration that gather a protocol and a list of supported
     * action for a specific identifier.
     *
     * @param manager The manager that contains supported protocols.
     */
    public ProtocolConfiguration(IProtocolManager manager) {
        this.manager = manager;
    }

    @Override
    public IRequest parse(byte[] data) {
        return manager.parse(data);
    }

    @Override
    public IRequestMessage getRequest(int identifier, int errorCode, Object payload) {
        IRequest request = manager.get(identifier);

        if (request == null)
            return null;

        request.setErrorCode(errorCode);
        request.setPayload(payload);
        return new RequestMessage(request);
    }

    private class RequestMessage implements IRequestMessage {
        private final IRequest request;
        private boolean isSync;
        private int timeout;
        private Consumer<CallbackArgs> callback;

        /**
         * Creates a request message ready to be sent to the remote. By default, the
         * message is sent asynchronously, there is no callback.
         *
         * @param request The request to send to the remote.
         */
        public RequestMessage(IRequest request) {
            this.request = request;

            isSync = false;
            timeout = -1;
            callback = this::doNothing;
        }

        @Override
        public float getVersion() {
            return request.getVersion();
        }

        @Override
        public int getIdentifier() {
            return request.getIdentifier();
        }

        @Override
        public int getErrorCode() {
            return request.getErrorCode();
        }

        @Override
        public Object getPayload() {
            return request.getPayload();
        }

        @Override
        public byte[] getBytes() {
            return request.getBytes();
        }

        @Override
        public boolean isSync() {
            return isSync;
        }

        @Override
        public void setSync(boolean isSync) {
            this.isSync = isSync;
        }

        @Override
        public int getTimeout() {
            return timeout;
        }

        @Override
        public Consumer<CallbackArgs> getCallback() {
            return callback;
        }

        @Override
        public void setCallback(Consumer<CallbackArgs> callback) {
            setCallback(1000, callback);
        }

        @Override
        public void setCallback(int timeout, Consumer<CallbackArgs> callback) {
            this.timeout = timeout;
            this.callback = callback;
        }

        @Override
        public String toString() {
            return (request == null ? this : request).toString();
        }

        private void doNothing(CallbackArgs args) {
            // Do nothing
        }
    }
}
