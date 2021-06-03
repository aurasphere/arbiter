package co.aurasphere.arbiter.model.trt;

import lombok.Data;

@Data
public class TrtNewOfferResponse {
	
	private String symbol;
	
	private String value;
	
	private OfferType type;

}
