package fr.pederobien.messenger.impl;

import fr.pederobien.messenger.interfaces.IErrorCode;
import fr.pederobien.messenger.interfaces.IResponse;

public class Response implements IResponse {
	private IErrorCode errorCode;

	/**
	 * Constructs a response when an error occurs.
	 * 
	 * @param errorCode The error code returned by the server when an error occurs.
	 */
	public Response(IErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public IErrorCode getErrorCode() {
		return errorCode;
	}
}
