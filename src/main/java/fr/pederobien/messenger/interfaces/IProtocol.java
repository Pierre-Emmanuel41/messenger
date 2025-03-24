package fr.pederobien.messenger.interfaces;

public interface IProtocol {

	/**
	 * @return The version of this protocol.
	 */
	float getVersion();

	/**
	 * Register internally a request configuration to easily generate a request.
	 * 
	 * @param identifier The request identifier.
	 * @param payload    the request payload.
	 */
	void register(int identifier, IPayloadWrapper payload);

	/**
	 * Creates a new request to send to the remote if the given identifier is
	 * supported by the protocol.
	 * 
	 * @param identifier The identifier of the request to create.
	 * 
	 * @return The created request if the identifier is supported, false otherwise.
	 */
	IRequest get(int identifier);
}
