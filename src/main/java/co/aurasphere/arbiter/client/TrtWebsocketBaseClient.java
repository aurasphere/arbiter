package co.aurasphere.arbiter.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

/**
 * Base class for a The Rock Trading API client with shared logic. This API is
 * websocket-based.
 * 
 * @author Donato Rimenti
 * 
 */
public abstract class TrtWebsocketBaseClient implements ConnectionEventListener, SubscriptionEventListener {

	/**
	 * The Gson object used for conversions.
	 */
	protected static final Gson GSON = new Gson();

	/**
	 * The logger.
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(TrtWebsocketBaseClient.class);

	/**
	 * Instantiates a new TrtBaseClient.
	 *
	 * @param channelName the name of the channel to which connect.
	 * @param eventName   the name of the event to which register for updates.
	 * @param trtApiKey
	 */
	protected TrtWebsocketBaseClient(String channelName, String eventName, String apiKey, String websocketApiKey) {
		// Create a new Pusher instance
		HttpAuthorizer authorizer = new HttpAuthorizer("https://api.therocktrading.com/ws/auth/pusher");
		Map<String, String> headers = new HashMap<>();
		headers.put("X-AUTH-Token", websocketApiKey);
		authorizer.setHeaders(headers);
		PusherOptions options = new PusherOptions();
		options.setAuthorizer(authorizer);
		options.setCluster("eu");
		Pusher pusher = new Pusher(apiKey, options);
		pusher.connect(this, ConnectionState.ALL);
		// Subscribe to a channel
		Channel channel = pusher.subscribe(channelName);
		// Bind to listen for events.
		channel.bind(eventName, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pusher.client.connection.ConnectionEventListener#
	 * onConnectionStateChange(com.pusher.client.connection. ConnectionStateChange)
	 */
	public void onConnectionStateChange(ConnectionStateChange change) {
		LOGGER.debug("State changed from [{}] to [{}]", change.getPreviousState(), change.getCurrentState());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pusher.client.connection.ConnectionEventListener#onError(java.lang.
	 * String, java.lang.String, java.lang.Exception)
	 */
	public void onError(String message, String code, Exception e) {
		LOGGER.error("Error during connection [code: {}, message: {}]", message, code, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pusher.client.channel.SubscriptionEventListener#onEvent(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public void onEvent(String channel, String event, String data) {
		LOGGER.debug("Received event [channel: {}, event: {}, data: {}]", channel, event, data);
	}

}
