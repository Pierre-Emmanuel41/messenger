package fr.pederobien.messenger.testing;

import fr.pederobien.messenger.impl.ProtocolManager;
import fr.pederobien.messenger.interfaces.IProtocol;
import fr.pederobien.messenger.interfaces.IRequest;
import fr.pederobien.utils.ByteWrapper;

public class MessengerTestApp {

	public static void main(String[] args) {
		ProtocolManager manager = new ProtocolManager();
		manager.getErrorCodeFactory().register(0, "No Error");

		int identifier = 1;

		// Registering protocol 1.0
		IProtocol protocol10 = manager.getOrCreate(1.0f);
		protocol10.register(identifier, new EntityPayloadV10());

		// Getting the request associated to the latest protocol: 1.0
		IRequest request = manager.get(identifier);

		Entity payload = new Entity("Player", "Jack", 30);
		request.getPayload().set(payload);

		String formatter = "Request with protocol 1.0: %s";
		System.out.println(String.format(formatter, request));

		// Simulating a request being sent to the remote
		byte[] data = request.getBytes();

		formatter = "Bytes with protocol 1.0: %s";
		System.out.println(String.format(formatter, ByteWrapper.wrap(data)));

		// Simulating e request being received from the remote
		IRequest received = manager.parse(data);
		if (received.getIdentifier() == identifier && received.getPayload().get().equals(payload)) {
			System.out.println("Received request match the sent request for protocol 1.0");
		} else
			System.out.println("An issue occured");

		// Simulating an evolution of the Entity properties (field city added)
		IProtocol protocol20 = manager.getOrCreate(2.0f);
		protocol20.register(1, new EntityPayloadV20());

		// Getting the request associated to the latest protocol: 2.0
		request = manager.get(1);

		payload = new Entity("PNJ", "Davy", 60, "Sea");
		request.getPayload().set(payload);

		formatter = "Request with protocol 1.0: %s";
		System.out.println(String.format(formatter, request));

		// Simulating e request being sent to the remote
		data = request.getBytes();

		formatter = "Bytes with protocol 1.0: %s";
		System.out.println(String.format(formatter, ByteWrapper.wrap(data)));

		// Simulating e request being received from the remote
		received = manager.parse(data);
		if (received.getIdentifier() == identifier && received.getPayload().get().equals(payload)) {
			System.out.println("Received request match the sent request for protocol 2.0");
		} else
			System.out.println("An issue occured");
	}
}
