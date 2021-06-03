package co.aurasphere.arbiter.model.trt;

import com.google.gson.annotations.SerializedName;

/**
 * Type of offers supported by The Rock Trading API.
 * 
 * @author Donato Rimenti
 * 
 */
public enum OfferType {

	/**
	 * An ask.
	 */
	@SerializedName("ask")
	ASK,

	/**
	 * A bid.
	 */
	@SerializedName("bid")
	BID;
}