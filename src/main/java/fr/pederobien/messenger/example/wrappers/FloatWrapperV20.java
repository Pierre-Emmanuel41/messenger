package fr.pederobien.messenger.example.wrappers;

import fr.pederobien.protocol.interfaces.IWrapper;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class FloatWrapperV20 implements IWrapper {

	@Override
	public byte[] getBytes(Object value) {
		if (value == null || !(value instanceof Float))
			return new byte[0];

		return ByteWrapper.create().putFloat((float) value).get();
	}

	@Override
	public Object parse(byte[] bytes) {
		return ReadableByteWrapper.wrap(bytes).nextFloat();
	}
}
