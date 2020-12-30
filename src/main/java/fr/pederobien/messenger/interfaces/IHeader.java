package fr.pederobien.messenger.interfaces;

public interface IHeader<T> {

	/**
	 * @return The bytes associated to this header.
	 */
	byte[] getBytes();

	/**
	 * @return The length of the bytes array.
	 */
	int getLength();

	/**
	 * Interpret the buffer in order to recreate header informations.
	 * 
	 * @param buffer An array that contains header informations.
	 * 
	 * @return This parsed header.
	 */
	T parse(byte[] buffer);
}
