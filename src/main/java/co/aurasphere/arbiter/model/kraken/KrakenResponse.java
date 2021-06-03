package co.aurasphere.arbiter.model.kraken;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Base Kraken API response.
 * 
 * @author Donato Rimenti
 * 
 */
@Data
public class KrakenResponse implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The errors, if any.
	 */
	private List<String> error;

	/**
	 * The results of the query, if any.
	 */
	private KrakenResult result;

}