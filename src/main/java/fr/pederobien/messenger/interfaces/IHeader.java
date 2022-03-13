package fr.pederobien.messenger.interfaces;

public interface IHeader {

	/**
	 * @return The message sequence number.
	 */
	int getSequence();

	/**
	 * Set the sequence number associated to this header. The sequence number represents internally an identifier. It should be unique
	 * and increased each time a message is created.
	 * 
	 * @param sequenceNumber The header sequence number.
	 */
	void setSequence(int sequenceNumber);

	/**
	 * @return The protocol version associated to the message.
	 */
	float getVersion();

	/**
	 * @return The bytes array associated to this header.
	 */
	byte[] getBytes();

	/**
	 * Interpret the buffer in order to recreate header informations.
	 * 
	 * @param buffer An array that contains header informations.
	 * 
	 * @return This parsed header.
	 */
	IHeader parse(byte[] buffer);

	/**
	 * Generates the bytes array according to the header properties. Once generated, the method {@link #getBytes()} returns the bytes
	 * array resulting to the last call of this method.
	 * 
	 * @return The bytes array corresponding to the properties of this header.
	 */
	byte[] generate();

	/**
	 * @return An array that contains additional properties attached to this header.
	 */
	Object[] getProperties();

	/**
	 * The properties associated to this header. It correspond to additional information that can be specified by each implementation
	 * of this interface. Changing the properties of this header does not regenerate the bytes array, the method {@link #generate()}
	 * should be called.
	 * 
	 * @param properties The additional header properties.
	 */
	void setProperties(Object... properties);
}
