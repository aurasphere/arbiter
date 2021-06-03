package co.aurasphere.arbiter.model.trt;

import java.util.List;

import co.aurasphere.arbiter.client.TrtOrderbookClient;
import lombok.Data;

/**
 * Response for {@link TrtOrderbookClient} used by the orderbook channel
 * websocket on The Rock Trading API to retrieve a record and the set of asks
 * and bids associated with it.
 * 
 * @author Donato Rimenti
 * 
 */
@Data
public class TrtOrderbookResponse {

	/**
	 * The asks of the currency pair.
	 */
	private List<TrtOrderbookEntry> asks;

	/**
	 * The bids of the currency pair.
	 */
	private List<TrtOrderbookEntry> bids;

}