package fr.pederobien.messenger.example.wrappers;

import fr.pederobien.protocol.interfaces.IWrapper;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class StringWrapperV10 implements IWrapper {

    @Override
    public byte[] getBytes(Object value) {
        if (!(value instanceof String))
            return new byte[0];

        return ByteWrapper.create().putString((String) value, true).get();
    }

    @Override
    public Object parse(byte[] bytes) {
        ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(bytes);

        return wrapper.nextString(wrapper.nextInt());
    }
}
