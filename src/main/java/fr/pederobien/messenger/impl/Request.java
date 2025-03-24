package fr.pederobien.messenger.impl;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IErrorCodeFactory;
import fr.pederobien.messenger.interfaces.IPayloadWrapper;
import fr.pederobien.messenger.interfaces.IRequest;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class Request implements IRequest {
	private float version;
	private IErrorCodeFactory factory;
	private int identifier;
	private int errorCode;
	private Object value;
	private IPayloadWrapper payloadWrapper;

	/**
	 * Creates a message to send to the remote.
	 * 
	 * @param version   The protocol version.
	 * @param factory   The factory to get the message associated to the error code.
	 * @param errorCode The request error code.
	 * @param config    The request configuration that contains the request
	 *                  identifier and the generator/parser to send/parse data.
	 */
	public Request(float version, IErrorCodeFactory factory, int errorCode, RequestConfig config) {
		this.version = version;
		this.factory = factory;
		this.errorCode = errorCode;
		this.identifier = config.getIdentifier();
		this.payloadWrapper = config.getWrapper();
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
	public void setPayload(Object value) {
		this.value = value;
	}

	@Override
	public Object getPayload() {
		return value;
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
		wrapper.put(payloadWrapper.getBytes(value));

		return wrapper.get();
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("identifier=" + getIdentifier());

		String formatter = "errorCode=[value=%s,message=%s]";
		joiner.add(String.format(formatter, getErrorCode(), factory.getMessage(getErrorCode())));

		joiner.add("payload=" + value);
		return joiner.toString();
	}

	/**
	 * Parse the content of the input wrapper. The input array shall have the
	 * following format:<br>
	 * <br>
	 * Byte 0 -> 3: Error code<br>
	 * Byte 4 -> end: Payload<br>
	 * 
	 * @param wrapper The wrapper that contains request information.
	 */
	public IRequest parse(ReadableByteWrapper wrapper) {
		// Byte 0 -> 3: Error code
		errorCode = wrapper.nextInt();

		// Byte 4 -> end: payload
		value = payloadWrapper.parse(wrapper.next(-1));

		return this;
	}
}
