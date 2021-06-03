package co.aurasphere.arbiter.model;

/**
 * Represents the platforms supported by this bot. Used to discriminate how to
 * insert a {@link RegistryEntry} into the {@link GlobalRegistry}.
 * 
 * @author Donato Rimenti
 * 
 */
public enum Platform {

	/**
	 * The Kraken platform.
	 */
	KRAKEN,
	/**
	 * The Rock Trading platform.
	 */
	THE_ROCK_TRADING;

}
