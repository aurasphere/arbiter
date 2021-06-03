package co.aurasphere.arbiter.model.kraken;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class KrakenWssAuthenticationRequest {
	
	private String event = "subscribe";
	
	private Subscription subscription;
	
	private List<String> pair;

	public KrakenWssAuthenticationRequest(String name, String token, List<String> pair) {
		this.subscription = new Subscription(name, token);
		this.pair = pair;
	}
	
	@Data
	@AllArgsConstructor
	public static class Subscription {
		
		private String name;
		
		private String token;
		
	}
	
}
