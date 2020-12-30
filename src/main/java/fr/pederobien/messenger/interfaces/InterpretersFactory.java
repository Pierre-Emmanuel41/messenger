package fr.pederobien.messenger.interfaces;

public interface InterpretersFactory<T extends IHeader<T>> {

	/**
	 * Get the interpreter associated to the specified header.
	 * 
	 * @param header The header used to get the interpreter.
	 * 
	 * @return The interpreter if found, null otherwise.
	 */
	IMessageInterpreter get(T header);
}
