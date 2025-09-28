package fr.pederobien.messenger.example.wrappers;

public record Player(String name, int level) {
	/**
	 * Creates a new player with the specific name and level.
	 *
	 * @param name  The player name.
	 * @param level The player experience level.
	 */
	public Player {
	}

	/**
	 * @return The player name.
	 */
	@Override
	public String name() {
		return name;
	}

	/**
	 * @return The player experience level.
	 */
	@Override
	public int level() {
		return level;
	}

	@Override
	public String toString() {
		return String.format("{name=%s,level=%s}", name, level);
	}
}
