package fr.pederobien.messenger.impl;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IErrorCodeFactory;
import fr.pederobien.messenger.interfaces.IPayload;
import fr.pederobien.messenger.interfaces.IRequest;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class Request implements IRequest {
	private float version;
	private IErrorCodeFactory factory;
	private int identifier;
	private int errorCode;
	private IPayload payload;

	/**
	 * Creates a message to send to the remote.
	 * 
	 * @param version    The protocol version.
	 * @param name       The request name.
	 * @param identifier The request identifier.
	 * @param errorCode  The request error code.
	 * @param payload    The request payload.
	 */
	public Request(float version, IErrorCodeFactory factory, int identifier, int errorCode, IPayload payload) {
		this.version = version;
		this.factory = factory;
		this.identifier = identifier;
		this.errorCode = errorCode;
		this.payload = payload;
	}

	@Override
	public float getVersion() {
		return version;
	}

	@Override
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public IPayload getPayload() {
		return payload;
	}

	@Override
	public IRequest parse(ReadableByteWrapper wrapper) {
		// Byte 0 -> 3: Error code
		errorCode = wrapper.nextInt();

		// Byte 4 -> end: payload
		payload.parse(wrapper);

		return this;
	}

	@Override
	public byte[] getBytes() {
		ByteWrapper wrapper = ByteWrapper.create();

		// Byte 0 -> 3: Protocol version
		wrapper.putFloat(version);

		// Byte 4 -> 7: Request identifier
		wrapper.putInt(identifier);

		// Byte 8 -> 11: Error code
		wrapper.putInt(errorCode);

		// Byte 12 -> end: Request payload
		wrapper.put(payload.getBytes());

		return wrapper.get();
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("identifier=" + getIdentifier());

		String formatter = "errorCode=[value=%s,message=%s]";
		joiner.add(String.format(formatter, getErrorCode(), factory.getMessage(getErrorCode())));

		joiner.add("payload=" + payload.toString());
		return joiner.toString();
	}
}
