package co.aurasphere.arbiter.model.kraken;

import java.util.Iterator;

/**
 * Represents the currency pair values handled and supported by Kraken platform
 * and this bot. This enum implements an iterator that allows an infinite loop
 * through its entries.
 * 
 * @author Donato Rimenti
 *
 */
public enum KrakenCurrencyPair implements Iterator<KrakenCurrencyPair>, Iterable<KrakenCurrencyPair> {

	/**
	 * Litecoin - Euro.
	 */
	LTCEUR,

	/**
	 * Ethereum - Euro.
	 */
	ETHEUR,

	/**
	 * Bitcoin - Euro.
	 */
	BTCEUR,

	/**
	 * Zcash - Euro.
	 */
	ZECEUR,

	/**
	 * Zcash - Bitcoin.
	 */
	ZECXBT,

	/**
	 * Litecoin - Bitcoin.
	 */
	LTCXBT,

	/**
	 * Bitcoin - United States Dollar.
	 */
	XBTUSD,

	/**
	 * Ethereum - Bitcoin.
	 */
	ETHXBT;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<KrakenCurrencyPair> iterator() {
		// Returns this object which is also an iterator.
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		// Always has next since it's an infinite loop iterator.
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	public KrakenCurrencyPair next() {
		// If the end is reached, restarts.
		if (ordinal() == ETHXBT.ordinal()) {
			return LTCEUR;
		}
		// Returns the next element.
		return values()[ordinal() + 1];
	}

	/**
	 * Equivalent implementation of a static {@link #toString()} method, used to
	 * quickly get a string with all the values of this enumeration separed by a
	 * comma.
	 *
	 * @return a string with all the values of this enum, comma separed.
	 */
	public static String valuesToString() {
		StringBuilder builder = new StringBuilder();
		for (KrakenCurrencyPair pair : values()) {
			builder.append(pair.name()).append(",");
		}
		// Removes the last comma.
		int length = builder.length();
		return builder.delete(length - 1, length).toString();
	}

}
