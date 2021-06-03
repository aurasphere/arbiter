package co.aurasphere.arbiter.service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.aurasphere.arbiter.client.KrakenWebsocketClient;
import co.aurasphere.arbiter.model.GlobalRegistry;
import co.aurasphere.arbiter.model.kraken.KrakenResponse;

/**
 * Centralized service which starts all The Rock Trading clients.
 * 
 * @author Donato Rimenti
 * 
 */
@Service
public class KrakenService {

	private int nonceCounter = 0;

	@Autowired
	public KrakenService(GlobalRegistry globalRegistry, @Value("${kraken.wss.url}") URI krakenWssUrl,
			@Value("${kraken.private.key}") String apiPrivateKey, @Value("${kraken.api.url}") String krakenApiBaseUrl,
			@Value("${kraken.api.key}") String apiKey) throws InvalidKeyException, NoSuchAlgorithmException {
		// Starts the orderbook clients, one for each supported currency pair.
		String nonce = createNonce();
		String getWsTokenUrl = "/0/private/GetWebSocketsToken";
		RestTemplate restTemplate = new RestTemplate();
		String postData = "nonce=" + nonce;
		System.out.println(apiKey);
		System.out.println(apiPrivateKey);
		RequestEntity<String> request = RequestEntity.post(URI.create(krakenApiBaseUrl + getWsTokenUrl))
				.header("API-Key", apiKey).header("API-Sign", signMessage(getWsTokenUrl, nonce, postData, apiPrivateKey))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.header(HttpHeaders.USER_AGENT, "AR/1.0").body(postData);
		System.out.println(request);
		KrakenResponse response = restTemplate.exchange(request, KrakenResponse.class).getBody();
		System.out.println(response);

		KrakenWebsocketClient wss = new KrakenWebsocketClient(krakenWssUrl, response.getResult().getToken(),
				globalRegistry);
		wss.connect();
	}

	// Signs a message
	private String signMessage(String endpoint, String nonce, String postData, String apiPrivateKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		// Step 1: concatenate postData, nonce + endpoint
		String message = nonce + postData;

		// Step 2: hash the result of step 1 with SHA256
		byte[] hash = (endpoint
				+ new String(MessageDigest.getInstance("SHA-256").digest(message.getBytes(StandardCharsets.UTF_8))))
						.getBytes();

		// step 3: base64 decode apiPrivateKey
		byte[] secretDecoded = Base64.getDecoder().decode(apiPrivateKey);

		// step 4: use result of step 3 to hash the result of step 2 with
		// HMAC-SHA512
		Mac hmacsha512 = Mac.getInstance("HmacSHA512");
		hmacsha512.init(new SecretKeySpec(secretDecoded, "HmacSHA512"));
		byte[] hash2 = hmacsha512.doFinal(hash);

		// step 5: base64 encode the result of step 4 and return
		return Base64.getEncoder().encodeToString(hash2);
	}

	// Returns a unique nonce
	private String createNonce() {
		nonceCounter += 1;
		long timestamp = (new Date()).getTime();
		return timestamp + String.format("%04d", nonceCounter);
	}

}