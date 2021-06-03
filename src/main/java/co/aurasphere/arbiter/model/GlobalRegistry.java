package co.aurasphere.arbiter.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * Main representation of this bot's data about the current market. Each record
 * in this registry is a {@link RegistryEntry} object.
 * 
 * @author Donato Rimenti
 * 
 */
@Component
public class GlobalRegistry {

	/**
	 * A constant used for calculations.
	 */
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	/**
	 * Map which contains all the entry of the registry.
	 */
	private volatile Map<FromToPair, RegistryEntry> entries = new ConcurrentHashMap<FromToPair, RegistryEntry>();

	/**
	 * Gets the {@link #entries}.
	 *
	 * @return the {@link #entries}.
	 */
	public Map<FromToPair, RegistryEntry> getEntries() {
		return entries;
	}

	/**
	 * Gets the {@link #entry}.
	 *
	 * @param fromToPair the key of the entry.
	 * @return the {@link #entry}.
	 */
	public RegistryEntry getEntry(FromToPair fromToPair) {
		return entries.get(fromToPair);
	}

	/**
	 * Updates an existing entry or adds it to the registry.
	 *
	 * @param entry    the entry to add.
	 * @param platform the platform whose entry is coming from.
	 */
	public synchronized void updateEntry(RegistryEntry entry, Platform platform) {
		RegistryEntry toUpdate = null;
		switch (platform) {
		case KRAKEN:
			toUpdate = updateEntryFromKraken(entry);
			break;
		case THE_ROCK_TRADING:
			toUpdate = updateEntryFromTrt(entry);
			break;
		}
		
		boolean wasZero = false;
		if ((toUpdate.getSpreadKraTrt() != null && toUpdate.getSpreadKraTrt().compareTo(BigDecimal.ZERO) > -1)
				|| (toUpdate.getSpreadTrtKra() != null && toUpdate.getSpreadTrtKra().compareTo(BigDecimal.ZERO) > -1)) {
			wasZero = true;
		}

		// Recalculates the spread.
		reloadSpread(toUpdate);

		// Prints the registry.
		if ((toUpdate.getSpreadKraTrt() != null && toUpdate.getSpreadKraTrt().compareTo(BigDecimal.ZERO) > -1)
				|| (toUpdate.getSpreadTrtKra() != null && toUpdate.getSpreadTrtKra().compareTo(BigDecimal.ZERO) > -1)) {
			System.out.println("Zero at " + LocalDateTime.now());
			System.out.println(this);
		} else if (wasZero){
			System.out.println("No more zero at " + LocalDateTime.now());
			System.out.println(this);
		}
		
//		System.out.println(this);
	}

	/**
	 * Updates a list of existing entry or adds them to the registry.
	 *
	 * @param entries  the entries to add.
	 * @param platform the platform whose entry is coming from.
	 */
	public void updateEntry(List<RegistryEntry> entries, Platform platform) {
		for (RegistryEntry entry : entries) {
			updateEntry(entry, platform);
		}
	}

	/**
	 * Recalculates the spread of an entry.
	 *
	 * @param entry the entry whose spread needs to be recalculated.
	 */
	private void reloadSpread(RegistryEntry entry) {
		BigDecimal askKraken = entry.getAskKraken();
		BigDecimal bidKraken = entry.getBidKraken();
		BigDecimal askTheRock = entry.getAskTrt();
		BigDecimal bidTheRock = entry.getBidTrt();

		// If the ask or the bid values are null does nothing, otherwise applies
		// the spread formula.
		if (askKraken != null && bidTheRock != null) {
			entry.setSpreadKraTrt(calculateSpread(askKraken, bidTheRock));
		}
		if (askTheRock != null && bidKraken != null) {
			entry.setSpreadTrtKra(calculateSpread(askTheRock, bidKraken));
		}
	}

	/**
	 * Applies the spread formula. The canonical formula is [(ask - bid) * 100 /
	 * ask] although this method uses [(bid - ask) * 100 / ask] in order to invert
	 * the sign (as asked by Manuel).
	 *
	 * @param ask the ask value.
	 * @param bid the bid value.
	 * @return the spread.
	 */
	private BigDecimal calculateSpread(BigDecimal ask, BigDecimal bid) {
		return bid.subtract(ask).multiply(ONE_HUNDRED).divide(ask, RoundingMode.HALF_UP).setScale(10,
				RoundingMode.HALF_UP);
	}

	/**
	 * Updates an existing entry or adds it to the registry. This method is a
	 * specialization used to handle record coming from TheRockTrading API.
	 *
	 * @param entry the entry to add.
	 */
	private RegistryEntry updateEntryFromTrt(RegistryEntry entry) {
		// If the entry is new, it adds it to the registry, otherwise it updates
		// the existing entry with the new values.
		RegistryEntry currentEntry = getEntry(entry.getFromTo());
		if (currentEntry == null) {
			entries.put(entry.getFromTo(), entry);
			entry.setLastUpdateTrt(new Date());
			return entry;
		}

		// Sets only the values contained by the incoming entry. The entry may
		// be an ASK or a BID, so here we merge them.
		BigDecimal ask = entry.getAskTrt();
		BigDecimal bid = entry.getBidTrt();
		if (ask != null) {
			currentEntry.setAskTrt(ask);
		}
		if (bid != null) {
			currentEntry.setBidTrt(bid);
		}
		currentEntry.setLastUpdateTrt(new Date());
		return currentEntry;
	}

	/**
	 * Updates an existing entry or adds it to the registry. This method is a
	 * specialization used to handle record coming from Kraken API.
	 *
	 * @param entry the entry to add.
	 */
	private RegistryEntry updateEntryFromKraken(RegistryEntry entry) {
		// If the entry is new, it adds it to the registry, otherwise it updates
		// the existing entry with the new values.
		RegistryEntry currentEntry = getEntry(entry.getFromTo());
		if (currentEntry == null) {
			entries.put(entry.getFromTo(), entry);
			entry.setLastUpdateKraken(new Date());
			return entry;
		}

		// Sets only the values contained by the incoming entry. The entry may
		// be an ASK or a BID, so here we merge them.
		BigDecimal ask = entry.getAskKraken();
		BigDecimal bid = entry.getBidKraken();
		if (ask != null) {
			currentEntry.setAskKraken(ask);
		}
		if (bid != null) {
			currentEntry.setBidKraken(bid);
		}
		currentEntry.setLastUpdateKraken(new Date());
		return currentEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		// Builds a table in order to give a visual feedback.
		String hr = "-----------------------------------------------------------------------------------------";
		String newLine = "\n";

		// Adds the headers.
		String headers = String.format("%-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-20s ! %-40s ! %-40s",
				"FROM-TO", "ASK(TRT)", "BID(TRT)", "ASK(KRA)", "BID(KRA)", "SPR(TRT-KRA)", "SPR(KRA-TRT)",
				"LAST UPDATE(TRT)", "LAST UPDATE(KRA)");

		// Builds the table.
		StringBuilder builder = new StringBuilder(hr).append(newLine).append(headers).append(newLine).append(hr)
				.append(newLine);

		// Adds the table entries.
		for (Entry<FromToPair, RegistryEntry> entry : entries.entrySet()) {
			builder.append(entry.getValue().toString()).append(newLine);
		}

		// Bottom table border.
		builder.append(hr).append(newLine);
		return builder.toString();
	}

}
