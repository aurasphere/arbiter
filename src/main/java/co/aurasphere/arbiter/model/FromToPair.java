package co.aurasphere.arbiter.model;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Object which represents a pair of {@link Currency} between which to perform a
 * conversion.
 * 
 * @author Donato Rimenti
 *
 */
@Value
@AllArgsConstructor
public class FromToPair {

	/**
	 * The start currency for conversion.
	 */
	private Currency from;

	/**
	 * The end currency for convertion.
	 */
	private Currency to;

	public FromToPair(String from, String to) {
		this(Currency.valueOf(from), Currency.valueOf(to));
	}

	@Override
	public String toString() {
		return from + "-" + to;
	}

}
