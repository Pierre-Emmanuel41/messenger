package fr.pederobien.messenger.example.server;

import fr.pederobien.communication.interfaces.IEthernetEndPoint;
import fr.pederobien.messenger.example.Identifiers;
import fr.pederobien.messenger.example.wrappers.Player;
import fr.pederobien.messenger.interfaces.IAction.ActionArgs;
import fr.pederobien.messenger.interfaces.IRequestMessage;
import fr.pederobien.messenger.interfaces.server.IProtocolClient;
import fr.pederobien.messenger.interfaces.server.IProtocolServerConfig;
import fr.pederobien.utils.event.Logger;

public class MyCustomTcpProtocolClient {
	private IProtocolServerConfig<IEthernetEndPoint> config;
	private IProtocolClient client;

	public MyCustomTcpProtocolClient(IProtocolServerConfig<IEthernetEndPoint> config, IProtocolClient client) {
		this.config = config;
		this.client = client;

		// Adding action to execute when a request has been received
		client.register(Identifiers.STRING_ID.getValue(), args -> onStringReceived(args));
		client.register(Identifiers.INT_ID.getValue(), args -> onIntegerReceived(args));
		client.register(Identifiers.FLOAT_ID.getValue(), args -> onFloatReceived(args));
		client.register(Identifiers.PLAYER_ID.getValue(), args -> onPlayerReceived(args));
	}

	private void onStringReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof String)) {
			Logger.error("Technical error happened, expecting String but got %s", args.getPayload().getClass());
			return;
		}

		Logger.info("Server received the following String: %s", args.getPayload());
	}

	private void onIntegerReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Integer)) {
			Logger.error("Technical error happened, expecting Integer but got %s", args.getPayload().getClass());
			return;
		}

		Logger.info("Server received the following Integer: %s", args.getPayload());
	}

	private void onFloatReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Float)) {
			Logger.error("Technical error happened, expecting Float but got %s", args.getPayload().getClass());
			return;
		}

		Logger.info("Server received the following Float: %s", args.getPayload());

		// Sending a response to the client
		IRequestMessage response = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, 1.0f);

		// Response sent synchronously
		response.setSynch(true);

		// Answering to client request
		client.getConnection().answer(args.getMessageID(), response);
	}

	private void onPlayerReceived(ActionArgs args) {
		if (!(args.getPayload() instanceof Player)) {
			Logger.error("Technical error happened, expecting Player but got %s", args.getPayload().getClass());
			return;
		}

		Logger.info("Server received the following Player: %s", args.getPayload());

		// Sending a response to the client
		IRequestMessage response = config.getRequest(Identifiers.FLOAT_ID.getValue(), 0, 3.56f);

		// Response sent synchronously
		response.setSynch(true);

		// Callback to execute when a response is received from the client
		response.setCallback(2000, arguments -> {
			if (!arguments.isTimeout()) {
				Object payload = config.parse(arguments.getResponse()).getPayload();
				Logger.info("Server received the following response: %s", payload);
			} else
				Logger.error("[Server] Unexpected timeout occured");
		});

		// Answering to client request
		client.getConnection().answer(args.getMessageID(), response);
	}
}
