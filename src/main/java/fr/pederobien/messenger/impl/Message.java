package fr.pederobien.messenger.impl;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.utils.ByteWrapper;

public abstract class Message implements IMessage {
	public static final byte[] BEGIN_WORD = new byte[] { 98, 105, 110, 27 };
	public static final byte[] END_WORD = new byte[] { 13, 10 };

	private String name;
	private IHeader header;
	protected byte[] bytes;
	private Object[] properties;

	/**
	 * Creates a message represented by a name and a header. The message name is used for storage only but is never used during the
	 * bytes generation.
	 * 
	 * @param name   The message name.
	 * @param header The message header.
	 */
	public Message(String name, IHeader header) {
		this.name = name;
		this.header = header;

		properties = new Object[0];
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public IHeader getHeader() {
		return header;
	}

	@Override
	public byte[] generate() {
		ByteWrapper wrapper = ByteWrapper.create().put(BEGIN_WORD).put(header.generate());
		byte[] properties = generateProperties();
		return bytes = wrapper.put(properties, true).put(END_WORD).get();
	}

	@Override
	public Object[] getProperties() {
		return properties;
	}

	@Override
	public void setProperties(Object... properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("header=" + getHeader());

		StringJoiner propertiesJoiner = new StringJoiner(", ", "{", "}");
		for (Object property : properties)
			propertiesJoiner.add(property.toString());
		joiner.add("properties=" + propertiesJoiner);

		return joiner.toString();
	}

	/**
	 * Generates a byte array containing additional header properties.
	 * 
	 * @return The bytes array representing only header additional properties.
	 */
	protected abstract byte[] generateProperties();
}
