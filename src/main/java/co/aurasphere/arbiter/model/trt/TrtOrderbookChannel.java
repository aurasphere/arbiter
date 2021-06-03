package co.aurasphere.arbiter.model.trt;

import java.util.Iterator;

/**
 * Channels for The Rock Trading API Orderbook event. The channels represents
 * the currency pairs to which register for updates. This enum implements an
 * iterator that allows an infinite loop through its entries.
 * 
 * Apart from the values or the enum there are a couple more channels supported
 * by The Rock Trading but those currencies are not supported by Kraken API and
 * thus unused. For future reference, those are:
 * 
 * <pre>
 * BTCXRP : Bitcoin - Ripple 
 * USDXRP : United States Dollar - Ripple 
 * PPCBTC : Peercoin - Bitcoin 
 * PPCEUR : Peercoin - Euro 
 * EURXRP : Euro - Ripple
 * </pre>
 * 
 * @author Donato Rimenti
 * 
 */
public enum TrtOrderbookChannel implements Iterator<TrtOrderbookChannel>, Iterable<TrtOrderbookChannel> {

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
	 * Bitcoin - United States Dollar.
	 */
	BTCUSD,

	/**
	 * Zcash - Bitcoin.
	 */
	ZECBTC,

	/**
	 * Zcash - Euro.
	 */
	ZECEUR,

	/**
	 * Litecoin - Bitcoin.
	 */
	LTCBTC,

	/**
	 * Ethereum - Bitcoin.
	 */
	ETHBTC;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<TrtOrderbookChannel> iterator() {
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
	public TrtOrderbookChannel next() {
		// If the end is reached, restarts.
		if (ordinal() == ETHBTC.ordinal()) {
			return LTCEUR;
		}
		// Returns the next element.
		return values()[ordinal() + 1];
	}

}
