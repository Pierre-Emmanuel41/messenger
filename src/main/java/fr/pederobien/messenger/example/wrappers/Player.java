package fr.pederobien.messenger.example.wrappers;

public class Player {
	private String name;
	private int level;

	/**
	 * Creates a new player with the specific name and level.
	 * 
	 * @param name  The player name.
	 * @param level The player experience level.
	 */
	public Player(String name, int level) {
		this.name = name;
		this.level = level;
	}

	/**
	 * @return The player name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The player experience level.
	 */
	public int getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return String.format("{name=%s,level=%s}", name, level);
	}
}
