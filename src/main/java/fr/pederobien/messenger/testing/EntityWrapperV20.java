package fr.pederobien.messenger.testing;

import fr.pederobien.messenger.interfaces.IPayload;
import fr.pederobien.utils.ByteWrapper;
import fr.pederobien.utils.ReadableByteWrapper;

public class EntityWrapperV20 implements IPayload {
	private Entity entity;

	@Override
	public byte[] getBytes() {
		if (entity == null)
			return new byte[0];

		ByteWrapper wrapper = ByteWrapper.create();

		wrapper.putString(entity.getType(), true);
		wrapper.putString(entity.getName(), true);
		wrapper.putInt(entity.getAge());
		wrapper.putString(entity.getCity(), true);

		return wrapper.get();
	}

	@Override
	public Object get() {
		return entity;
	}

	@Override
	public void set(Object value) {
		if (value instanceof Entity)
			entity = (Entity) value;
	}

	@Override
	public void parse(ReadableByteWrapper wrapper) {
		String type = wrapper.nextString(wrapper.nextInt());
		String name = wrapper.nextString(wrapper.nextInt());
		int age = wrapper.nextInt();
		String city = wrapper.nextString(wrapper.nextInt());

		entity = new Entity(type, name, age, city);
	}

	@Override
	public String toString() {
		return (entity == null ? this : entity).toString();
	}
}
