package fr.pederobien.messenger.example.wrappers;

import fr.pederobien.protocol.interfaces.IWrapper;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class PlayerWrapperV20 implements IWrapper {

	@Override
	public byte[] getBytes(Object value) {
		if (value == null || !(value instanceof Player))
			return new byte[0];

		Player player = (Player) value;

		ByteWrapper wrapper = ByteWrapper.create();
		wrapper.putString(player.getName(), true);
		wrapper.putInt(player.getLevel());
		return wrapper.get();
	}

	@Override
	public Object parse(byte[] bytes) {
		ReadableByteWrapper wrapper = ReadableByteWrapper.wrap(bytes);

		// Player name
		String name = wrapper.nextString(wrapper.nextInt());

		// Player experience level
		int level = wrapper.nextInt();

		return new Player(name, level);
	}
}
