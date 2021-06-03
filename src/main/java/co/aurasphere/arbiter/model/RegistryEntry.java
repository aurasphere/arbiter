package co.aurasphere.arbiter.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * Represents an entry into the {@link GlobalRegistry}.
 * 
 * @author Donato Rimenti
 * 
 */
@Data
public class RegistryEntry {

	/**
	 * The key of the entry, represented by a couple of currencies between which the
	 * conversion needs to be performed.
	 */
	private FromToPair fromTo;

	/**
	 * The ask value from The Rock Trading platform.
	 */
	private BigDecimal askTrt;

	/**
	 * The bid value from The Rock Trading platform.
	 */
	private BigDecimal bidTrt;

	/**
	 * The ask value from Kraken platform.
	 */
	private BigDecimal askKraken;

	/**
	 * The bid value from Kraken platform.
	 */
	private BigDecimal bidKraken;

	/**
	 * The spread calculated using the ask value from The Rock Trading platfrom and
	 * the bid value from Kraken platform.
	 */
	private BigDecimal spreadTrtKra;

	/**
	 * The spread calculated using the ask value from Kraken platfrom and the bid
	 * value from The Rock Trading platform.
	 */
	private BigDecimal spreadKraTrt;

	/**
	 * A timestamp representing the moment the last update has been received from
	 * The Rock Trading platform.
	 */
	private Date lastUpdateTrt;

	/**
	 * A timestamp representing the moment the last update has been received from
	 * Kraken platform.
	 */
	private Date lastUpdateKraken;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Builds a record representation as a table row, used while printing
		// the GlobalRegistry.
		return String.format("%-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-40s ! %-40s", fromTo, askTrt,
				bidTrt, askKraken, bidKraken, spreadTrtKra, spreadKraTrt, lastUpdateTrt, lastUpdateKraken);
	}

}
