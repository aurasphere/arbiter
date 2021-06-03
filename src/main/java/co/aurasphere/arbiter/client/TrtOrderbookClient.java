package co.aurasphere.arbiter.client;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.arbiter.model.FromToPair;
import co.aurasphere.arbiter.model.GlobalRegistry;
import co.aurasphere.arbiter.model.Platform;
import co.aurasphere.arbiter.model.RegistryEntry;
import co.aurasphere.arbiter.model.trt.TrtNewOfferResponse;

/**
 * The Rock Trading client for registering to the orderbook service which
 * returns which returns a list of the last asks and bids values for a currency
 * pair each time there's an update.
 * 
 * @author Donato Rimenti
 * 
 */
public class TrtOrderbookClient extends TrtWebsocketBaseClient {

	private static final Logger LOG = LoggerFactory.getLogger(TrtOrderbookClient.class);

	private GlobalRegistry globalRegistry;

	/**
	 * The orderbook event.
	 */
	private static final String ORDERBOOK_EVENT = "new_offer";

	/**
	 * Instantiates a new TrtOrderbookClient.
	 *
	 * @param channel   the channel to which register for updates.
	 * @param trtApiKey
	 */
	public TrtOrderbookClient(String channel, GlobalRegistry globalRegistry, String trtApiKey, String websocketApiKey) {
		super(channel, ORDERBOOK_EVENT, trtApiKey, websocketApiKey);
		this.globalRegistry = globalRegistry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.aurasphere.arbitraggi.network.trt.TrtBaseClient#onEvent(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	public void onEvent(String channel, String event, String data) {
		super.onEvent(channel, event, data);
		// Parses the message and adds it to the registry.
		TrtNewOfferResponse response = GSON.fromJson(data, TrtNewOfferResponse.class);
		String currency = response.getSymbol();
		if (currency.length() != 6) {
			LOG.debug("Ignoring currency [{}]", currency);
			return;
		}
		RegistryEntry entry = new RegistryEntry();
		entry.setFromTo(trtCurrencyStringToFromToPair(currency));
		switch (response.getType()) {
		case ASK:
			entry.setAskTrt(new BigDecimal(response.getValue()));
			break;
		case BID:
			entry.setBidTrt(new BigDecimal(response.getValue()));
			break;
		}
		globalRegistry.updateEntry(entry, Platform.THE_ROCK_TRADING);
	}

	/**
	 * Generates a {@link FromToPair} object from a TheRockTrading API currency
	 * String. The method splits the string in half and uses each substring as an
	 * individual currency.
	 * 
	 * @param currency the currency string to convert.
	 * @return a {@link FromToPair} object.
	 */
	private static FromToPair trtCurrencyStringToFromToPair(String currency) {
		String from = currency.substring(0, 3);
		String to = currency.substring(3, 6);
		return new FromToPair(from, to);
	}

}