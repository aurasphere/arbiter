package co.aurasphere.arbiter.model.kraken;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response for {@link KrakenOrderbookClient} used by the /Depth path on the
 * Kraken API to retrieve one record from the Orderbook.
 * 
 * @author Donato Rimenti
 * 
 */
public class KrakenOrderbookResult extends KrakenResult {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A list of values representing the last n ask prices, volumes and timestamps.
	 */
	private List<List<BigDecimal>> asks;

	/**
	 * A list of values representing the last n bid prices, volumes and timestamps.
	 */
	private List<List<BigDecimal>> bids;

	/**
	 * Gets the {@link #asks}.
	 *
	 * @return the {@link #asks}.
	 */
	public List<List<BigDecimal>> getAsks() {
		return asks;
	}

	/**
	 * Sets the {@link #asks}.
	 *
	 * @param asks
	 *            the new {@link #asks}.
	 */
	public void setAsks(List<List<BigDecimal>> asks) {
		this.asks = asks;
	}

	/**
	 * Gets the {@link #bids}.
	 *
	 * @return the {@link #bids}.
	 */
	public List<List<BigDecimal>> getBids() {
		return bids;
	}

	/**
	 * Sets the {@link #bids}.
	 *
	 * @param bids
	 *            the new {@link #bids}.
	 */
	public void setBids(List<List<BigDecimal>> bids) {
		this.bids = bids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.arbitraggi.model.json.kraken.response.KrakenResult#hashCode
	 * ()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asks == null) ? 0 : asks.hashCode());
		result = prime * result + ((bids == null) ? 0 : bids.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.arbitraggi.model.json.kraken.response.KrakenResult#equals(
	 * java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrakenOrderbookResult other = (KrakenOrderbookResult) obj;
		if (asks == null) {
			if (other.asks != null)
				return false;
		} else if (!asks.equals(other.asks))
			return false;
		if (bids == null) {
			if (other.bids != null)
				return false;
		} else if (!bids.equals(other.bids))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.arbitraggi.model.json.kraken.response.KrakenResult#toString
	 * ()
	 */
	@Override
	public String toString() {
		return "KrakenOrderbookResult [asks=" + asks + ", bids=" + bids + "]";
	}

}
