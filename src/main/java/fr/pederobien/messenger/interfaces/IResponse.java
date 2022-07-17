package fr.pederobien.messenger.interfaces;

public interface IResponse {

	/**
	 * @return The error code returned by the server if an error occurs when sending data to the remote or receiving data from the
	 *         remote.
	 */
	IErrorCode getErrorCode();

	/**
	 * @return If an exception or an error occurs. An error code with value 0 is considered as no error.
	 */
	default boolean hasFailed() {
		return getErrorCode().getCode() != 0;
	}
}
