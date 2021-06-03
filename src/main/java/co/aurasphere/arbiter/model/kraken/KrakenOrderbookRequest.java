package co.aurasphere.arbiter.model.kraken;

import java.io.Serializable;

/**
 * Request for {@link KrakenOrderbookClient} used by the /Depth path on the
 * Kraken API to retrieve one record from the Orderbook.
 * 
 * @author Donato Rimenti
 * 
 */
public class KrakenOrderbookRequest implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The currency pair whose data needs to be retrieved.
	 */
	private KrakenCurrencyPair pair;

	/**
	 * Instantiates a new KrakenOrderbookRequest.
	 */
	public KrakenOrderbookRequest() {
	}

	/**
	 * Instantiates a new KrakenOrderbookRequest.
	 *
	 * @param pair
	 *            the {@link #pair}.
	 */
	public KrakenOrderbookRequest(KrakenCurrencyPair pair) {
		this.pair = pair;
	}

	/**
	 * Gets the {@link #pair}.
	 *
	 * @return the {@link #pair}.
	 */
	public KrakenCurrencyPair getPair() {
		return pair;
	}

	/**
	 * Sets the {@link #pair}.
	 *
	 * @param pair
	 *            the new {@link #pair}.
	 */
	public void setPair(KrakenCurrencyPair pair) {
		this.pair = pair;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrakenOrderbookRequest other = (KrakenOrderbookRequest) obj;
		if (pair != other.pair)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KrakenOrderbookRequest [pair=" + pair + "]";
	}

}
