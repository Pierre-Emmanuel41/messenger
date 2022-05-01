package fr.pederobien.messenger.impl;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.utils.ByteWrapper;

public abstract class Header implements IHeader {
	/**
	 * The index at which there is the first byte of the communication protocol version.
	 */
	public static final int VERSION_INDEX = 4;

	/**
	 * The index at which there is the first byte of the sequence number.
	 */
	public static final int SEQUENCE_NUMBER_INDEX = 8;

	private int sequence;
	private float version;
	private byte[] bytes;
	private Object[] properties;

	/**
	 * Creates a new header initialized with an empty bytes array and a sequence number equals to -1.
	 * 
	 * @param version The protocol version used to create this header.
	 */
	public Header(float version) {
		this.version = version;
		bytes = new byte[0];
		sequence = -1;

		properties = new Object[0];
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	@Override
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public float getVersion() {
		return version;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public IHeader parse(byte[] buffer) {
		ByteWrapper wrapper = ByteWrapper.wrap(buffer);

		// +0: Version
		version = wrapper.getFloat(VERSION_INDEX - Message.BEGIN_WORD.length);

		// +4: Sequence
		sequence = wrapper.getInt(SEQUENCE_NUMBER_INDEX - Message.BEGIN_WORD.length);
		return this;
	}

	@Override
	public byte[] generate() {
		return bytes = ByteWrapper.create().putFloat(getVersion()).putInt(getSequence()).put(generateProperties()).get();
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof IHeader))
			return false;

		IHeader other = (IHeader) obj;
		return getSequence() == other.getSequence();
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("version=" + getVersion());
		joiner.add("sequence=" + getSequence());

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
