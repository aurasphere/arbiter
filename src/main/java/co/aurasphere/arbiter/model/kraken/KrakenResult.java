package co.aurasphere.arbiter.model.kraken;

import lombok.Data;

/**
 * Abstract class used to abstract the common data of a {@link KrakenResponse}.
 * 
 * @author Donato Rimenti
 * 
 */
@Data
public class KrakenResult{

	/**
	 * The currency pair for this response as string in Kraken format.
	 */
	private String currency;
	private String token;
}