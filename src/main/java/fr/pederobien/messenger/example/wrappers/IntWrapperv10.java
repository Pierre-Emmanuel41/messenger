package fr.pederobien.messenger.example.wrappers;

import fr.pederobien.protocol.interfaces.IWrapper;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class IntWrapperv10 implements IWrapper {

	@Override
	public byte[] getBytes(Object value) {
		if (value == null || !(value instanceof Integer))
			return new byte[0];

		return ByteWrapper.create().putInt((int) value).get();
	}

	@Override
	public Object parse(byte[] bytes) {
		return ReadableByteWrapper.wrap(bytes).nextInt();
	}
}
