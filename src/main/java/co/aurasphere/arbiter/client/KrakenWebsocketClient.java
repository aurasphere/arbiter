package co.aurasphere.arbiter.client;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import co.aurasphere.arbiter.model.Currency;
import co.aurasphere.arbiter.model.FromToPair;
import co.aurasphere.arbiter.model.GlobalRegistry;
import co.aurasphere.arbiter.model.Platform;
import co.aurasphere.arbiter.model.RegistryEntry;
import co.aurasphere.arbiter.model.kraken.KrakenCurrencyPair;
import co.aurasphere.arbiter.model.kraken.KrakenWssAuthenticationRequest;

public class KrakenWebsocketClient extends WebSocketClient {

	private static final Logger LOG = LoggerFactory.getLogger(KrakenWebsocketClient.class);

	private String token;

	private Gson gson = new Gson();

	private GlobalRegistry registry;

	public KrakenWebsocketClient(URI krakenWssUrl, String token, GlobalRegistry registry) {
		super(krakenWssUrl);
		this.registry = registry;
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		LOG.info("new connection opened");
		List<String> pairs = new ArrayList<>();
		for (KrakenCurrencyPair pair : KrakenCurrencyPair.values()) {
			String pairString = pair.toString().substring(0, 3) + "/" + pair.toString().substring(3);
			pairs.add(pairString);
		}
		send(gson.toJson(new KrakenWssAuthenticationRequest("ticker", token, pairs)));
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		LOG.info("Closed with exit code [{}] and reason [{}]", code, reason);
	}

	@Override
	public void onMessage(String message) {
		JsonElement object = gson.fromJson(message, JsonElement.class);
		if (object.isJsonArray()) {
			JsonArray response = object.getAsJsonArray();
			String fromToPair = response.get(3).getAsString();
			JsonObject data = response.get(1).getAsJsonObject();
			JsonArray ask = data.getAsJsonArray("a");
			JsonArray bid = data.getAsJsonArray("b");

			RegistryEntry entry = new RegistryEntry();
			entry.setFromTo(krakenCurrencyStringToFromToPair(fromToPair));

			// Sets the average values of ask and bid.
			entry.setAskKraken(ask.get(0).getAsBigDecimal());
			entry.setBidKraken(bid.get(0).getAsBigDecimal());
			registry.updateEntry(entry, Platform.KRAKEN);
		}

	}

	@Override
	public void onMessage(ByteBuffer message) {
		LOG.info("received ByteBuffer");
	}

	@Override
	public void onError(Exception ex) {
		LOG.error("an error occurred:", ex);
	}

	private static FromToPair krakenCurrencyStringToFromToPair(String currency) {
		String[] fromTo = currency.split("/");
		return new FromToPair(fixKrakenBtcFormat(fromTo[0]), fixKrakenBtcFormat(fromTo[1]));
	}

	/**
	 * Converts a string to a {@link Currency} object, converting the
	 * {@value #KRAKEN_BITCOIN_CURRENCY} to {@link Currency#BTC}.
	 * 
	 * @param cur the currency string to convert.
	 * @return a {@link Currency} object.
	 */
	private static Currency fixKrakenBtcFormat(String cur) {
		if (cur.equals("XBT")) {
			return Currency.BTC;
		}
		return Currency.valueOf(cur);
	}

}