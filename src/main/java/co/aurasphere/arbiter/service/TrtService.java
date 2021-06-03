package co.aurasphere.arbiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.aurasphere.arbiter.client.TrtOrderbookClient;
import co.aurasphere.arbiter.model.GlobalRegistry;

/**
 * Centralized service which starts all The Rock Trading clients.
 * 
 * @author Donato Rimenti
 * 
 */
@Service
public class TrtService {

	@Autowired
	public TrtService(GlobalRegistry globalRegistry, @Value("${trt.api.key}") String trtApiKey,
			@Value("${trt.websocket.key}") String websocketApiKey) {
		// Starts the orderbook clients, one for each supported currency pair.
		new TrtOrderbookClient("currency", globalRegistry, trtApiKey, websocketApiKey);
	}

}