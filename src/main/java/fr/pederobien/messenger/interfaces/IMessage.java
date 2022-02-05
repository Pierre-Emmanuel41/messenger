package fr.pederobien.messenger.interfaces;

public interface IMessage {

	/**
	 * @return The name of this message.
	 */
	String getName();

	/**
	 * @return The bytes array associated to this message.
	 */
	byte[] getBytes();

	/**
	 * @return The header associated to this message.
	 */
	IHeader getHeader();

	/**
	 * Interpret the buffer in order to recreate message informations.
	 * 
	 * @param buffer An array that contains message informations.
	 * 
	 * @return This parsed message.
	 */
	IMessage parse(byte[] payload);

	/**
	 * Generates the bytes array according to the message properties. Once generated, the method {@link #getBytes()} returns the bytes
	 * array resulting to the last call of this method.
	 * 
	 * @return The bytes array corresponding to the properties of this message.
	 */
	byte[] generate();

	/**
	 * @return An array that contains additional properties attached to this message.
	 */
	Object[] getProperties();

	/**
	 * The properties associated to this message. It correspond to additional information that can be specified by each implementation
	 * of this interface. Changing the properties of this message does not regenerate the bytes array, the method {@link #generate()}
	 * should be called.
	 * 
	 * @param properties The additional message properties.
	 */
	void setProperties(Object... properties);
}
