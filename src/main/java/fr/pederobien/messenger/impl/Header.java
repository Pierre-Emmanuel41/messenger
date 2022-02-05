package fr.pederobien.messenger.impl;

import java.util.StringJoiner;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.utils.ByteWrapper;

public abstract class Header implements IHeader {
	private int identifier;
	private float version;
	private byte[] bytes;
	private Object[] properties;

	/**
	 * Creates a new header initialized with an empty bytes array and an identifier equals to -1.
	 * 
	 * @param version The protocol version used to create this header.
	 */
	public Header(float version) {
		this.version = version;
		bytes = new byte[0];
		identifier = -1;

		properties = new Object[0];
	}

	@Override
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
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
		int index = 0;

		// +0: Version
		version = wrapper.getFloat(index);
		index += 4;

		// +4: Identifier
		identifier = wrapper.getInt(index);
		return this;
	}

	@Override
	public byte[] generate() {
		return bytes = ByteWrapper.create().putFloat(getVersion()).putInt(getIdentifier()).put(generateProperties()).get();
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
		return getIdentifier() == other.getIdentifier();
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("version=" + getVersion());
		joiner.add("identifier=" + getIdentifier());

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
