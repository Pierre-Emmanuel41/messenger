package fr.pederobien.messenger.impl;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import fr.pederobien.messenger.interfaces.IHeader;
import fr.pederobien.messenger.interfaces.IMessage;
import fr.pederobien.messenger.interfaces.InterpretersFactory;
import fr.pederobien.utils.ByteWrapper;

public class Message<T extends IHeader<T>> implements IMessage<T> {
	public static final byte[] SYNC_WORD = new byte[] { 98, 105, 110, 27 };
	public static final byte[] END_WORD = new byte[] { 13, 10 };
	private static final AtomicInteger IDENTIFIER = new AtomicInteger(0);

	private int identifier;
	private InterpretersFactory<T> interpreters;
	private T header;
	private Object[] payload;
	private byte[] bytes;
	private String toString;

	public Message(InterpretersFactory<T> interpreters, T header, Object... payload) {
		this.interpreters = interpreters;
		this.header = header;
		this.payload = payload;

		identifier = IDENTIFIER.getAndIncrement();

		initialize(true);
	}

	private Message(InterpretersFactory<T> interpreters, int identifier, T header, byte[] bytes, Object... payload) {
		this.interpreters = interpreters;
		this.identifier = identifier;
		this.header = header;
		this.bytes = bytes;
		this.payload = payload;

		initialize(false);
	}

	private Message(InterpretersFactory<T> interpreters, int identifier, T header, Object... payload) {
		this.interpreters = interpreters;
		this.identifier = identifier;
		this.header = header;
		this.payload = payload;

		initialize(true);
	}

	public static <T extends IHeader<T>> IMessage<T> parse(InterpretersFactory<T> interpreters, T header, byte[] buffer) {
		ByteWrapper wrapper = ByteWrapper.wrap(buffer);
		int identifier = wrapper.getInt(4);

		// 8 = SYNC_WORD.Length + identifier's byte array length.
		T parsedHeader = header.parse(wrapper.extract(8, buffer.length - 8));

		int payloadLengthIndex = SYNC_WORD.length + 4 + parsedHeader.getLength();
		int payloadLength = wrapper.getInt(payloadLengthIndex);
		Object[] payload = interpreters.get(parsedHeader).interprete(payloadLength == 0 ? new byte[0] : wrapper.extract(payloadLengthIndex + 4, payloadLength));
		return new Message<T>(interpreters, identifier, parsedHeader, buffer, payload);
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public Object[] getPayload() {
		return payload;
	}

	@Override
	public T getHeader() {
		return header;
	}

	@Override
	public IMessage<T> answer(Object... payload) {
		return new Message<T>(interpreters, identifier, header, payload);
	}

	@Override
	public IMessage<T> answer(T header, Object... payload) {
		return new Message<T>(interpreters, identifier, header, payload);
	}

	@Override
	public String toString() {
		return toString;
	}

	private void initialize(boolean generateBytes) {
		if (generateBytes)
			bytes = ByteWrapper.wrap(SYNC_WORD).putInt(identifier).put(header.getBytes()).put(interpreters.get(header).generate(payload), true).put(END_WORD).get();

		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add(new String(SYNC_WORD));
		joiner.add("identifier={" + identifier + "}");
		joiner.add("header={" + header.toString() + "}");

		StringJoiner joinerBis = new StringJoiner(",", "{", "}");
		for (Object info : payload)
			joinerBis.add(info.toString());
		joiner.add("Payload=" + joinerBis.toString());

		toString = joiner.toString();
	}
}
