package fr.pederobien.messenger.impl;

import fr.pederobien.utils.ByteWrapper;

public class RequestMalFormedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private byte[] bytes;

	public RequestMalFormedException(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String getMessage() {
		String format = "The request associated to the following bytes array is mal formed:%n%s";
		return String.format(format, ByteWrapper.wrap(bytes));
	}
}
