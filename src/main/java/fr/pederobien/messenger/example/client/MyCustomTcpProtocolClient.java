package fr.pederobien.messenger.example.client;

import fr.pederobien.communication.impl.EthernetEndPoint;
import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.messenger.example.Identifiers;
import fr.pederobien.messenger.example.MyProtocols;
import fr.pederobien.messenger.example.wrappers.Player;
import fr.pederobien.messenger.impl.Messenger;
import fr.pederobien.messenger.impl.client.ProtocolClientConfig;
import fr.pederobien.messenger.interfaces.IAction.ActionArgs;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.messenger.interfaces.client.IProtocolClient;
import fr.pederobien.utils.event.Logger;

public class MyCustomTcpProtocolClient {
	private IProtocolClient client;
	private ProtocolClientConfig<IEthernetEndPoint> config;

	public MyCustomTcpProtocolClient() {
		IEthernetEndPoint endPoint = new EthernetEndPoint("127.0.0.1", 12345);

		config = Messenger.createClientConfig(MyProtocols.getManager(), "My TCP client", endPoint);

		// Setting the layer to use to pack/unpack data.
		// A new layer is defined each time a new client is connected
		// config.setLayerInitializer(() -> new AesLayerInitializer(new
		// SimpleCertificate()));

		// If the connection unstable counter reach 10, the connection will be
		// closed automatically
		config.setConnectionMaxUnstableCounter(10);

		// Decrement the value of the connection unstable counter each 100 ms
		config.setConnectionHealTime(100);

		// Time in ms after which a timeout occurs when trying to connect to the server
		config.setConnectionTimeout(5000);

		// The connection wait 1000 ms before retrying to connect with the server
		config.setReconnectionDelay(1000);

		// Value by default is true
		config.setAutomaticReconnection(false);

		// If the client unstable counter reach 2, the connection will be
		// closed automatically and the client will close it self.
		config.setClientMaxUnstableCounter(2);

		// Decrement the value of the client unstable counter each 5 ms
		config.setClientHealTime(5);

		// Adding action to execute when a request has been received
		config.register(Identifiers.STRING_ID.getValue(), args -> onStringReceived(args));
		config.register(Identifiers.INT_ID.getValue(), args -> onIntegerReceived(args));
		config.register(Identifiers.FLOAT_ID.getValue(), args -> onFloatReceived(args));
		config.register(Identifiers.PLAYER_ID.getValue(), args -> onPlayerReceived(args));

		// Creating the client
		client = Messenger.createTcpClient(config);
	}

	/**
	 * Attempt the connection with the remote asynchronously
	 */
	public void connect() {
		client.connect();
	}

	/**
	 * Close the connection with the remote, by calling {@link #connect()} the
	 * client can be connected again with the server.
	 */
	public void disconnect() {
		client.disconnect();
	}

	/**
	 * Definitely close the connection with the server.
	 */
	public void dispose() {
		client.dispose();
	}

	/**
	 * Send a string to the server.
	 * 
	 * @param payload The string to send to the server
	 */
	public void send(String payload) {
		client.getConnection().send(config.getRequest(Identifiers.STRING_ID.getValue(), 0, payload));
	}

	public void send(int payload) {
		IRequestMessage request = config.getRequest(Identifiers.INT_ID.getValue(), 0, payload);

		// The request is sent synchronously
		request.setSynch(true);

		// Sending request to server
		client.getConnection().send(request);
	}

	/**
	 * Send a float to the server.
	 * 
	 * @param payload The float to send to the server.
	 */
	public void send(float payload) {
		IRequestMessage request = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, payload);

		// Callback to execute when a response is received from the server
		request.setCallback(1000, args -> {
			if (!args.isTimeout())
				Logger.info("Client received %s", config.parse(args.getResponse()).getPayload());
			else
				Logger.error("[Client] Unexpected timeout occured");
		});

		// Sending request to server
		client.getConnection().send(request);
	}

	public void send(Player player) {
		IRequestMessage request = config.getRequest(Identifiers.PLAYER_ID.getValue(), 0, player);

		// The request is sent synchronously
		request.setSynch(true);

		// Callback to execute when a response is received from the server
		request.setCallback(1000, args -> {
			if (!args.isTimeout()) {
				Logger.info("Client received %s", config.parse(args.getResponse()).getPayload());

				// Answering to server's answer
				IRequestMessage response = config.getRequest(Identifiers.STRING_ID.getValue(), 0, "OK");
				client.getConnection().answer(args.getIdentifier(), response);
			} else
				Logger.error("[Client] Unexpected timeout occured");
		});

		// Sending request to server
		client.getConnection().send(request);
	}

	private void onStringReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof String)) {
			Logger.error("Technical error happened, expecting String but got %s", args.getPayload().getClass());
			return;
		}

		Logger.print("Client received the following String: %s", args.getPayload());
	}

	private void onIntegerReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Integer)) {
			Logger.error("Technical error happened, expecting Integer but got %s", args.getPayload().getClass());
			return;
		}

		Logger.print("Client received the following Integer: %s", args.getPayload());
	}

	private void onFloatReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Float)) {
			Logger.error("Technical error happened, expecting Float but got %s", args.getPayload().getClass());
			return;
		}

		Logger.print("Client received the following Float: %s", args.getPayload());
	}

	private void onPlayerReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Player)) {
			Logger.error("Technical error happened, expecting Player but got %s", args.getPayload().getClass());
			return;
		}

		Logger.print("Client received the following Player: %s", args.getPayload());
	}
}
