package co.aurasphere.arbiter.model.trt;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Entry of a {@link TrtOrderbookResponse}.
 * 
 * @author Donato Rimenti
 * 
 */
@Data
public class TrtOrderbookEntry {

	/**
	 * The price of the entry.
	 */
	private BigDecimal price;

	/**
	 * The amount of the entry.
	 */
	private BigDecimal amount;

}