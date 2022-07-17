package fr.pederobien.messenger.interfaces;

public interface IErrorCode {

	/**
	 * @return The bytes array associated to the code of this error code.
	 */
	byte[] getBytes();

	/**
	 * @return The integer value of this error code.
	 */
	int getCode();

	/**
	 * @return The message associated to this error code.
	 */
	String getMessage();
}
