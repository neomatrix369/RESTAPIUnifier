package org.neomatrix369.apiworld.SearchForTweets;

import org.junit.Before;
import org.junit.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neomatrix369.examples.search_tweets.TweetsDataStorage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TweetsDataStorageBehaviours {
	private static final String READ_WRITE_MISMATCH_ERROR_MESSAGE = "Written and read messages do not match! Data storage error";
	private static final String TWEETS_FILENAME = "testSaveTweets.json";
	TweetsDataStorage storage;
	
	@Before
	public void setup() {
		storage = new TweetsDataStorage(TWEETS_FILENAME);
	}
			
	@Test
	public void shouldBeAbleToWriteAndReadBackMessages() throws JSONException {
		String arrayPartOfTweetAsString =  "[{'from_user_name':'someUser', 'from_user_id':'‏@someonesTwitterHandle', 'text':'Body of the twitter message #hashtag1 #hashtag2 #hashtag3'}]";
		final String tweetMessageWritten = "{results: "+ arrayPartOfTweetAsString + "}";		
		storage.saveTweetMessage(tweetMessageWritten);
		
		final JSONArray tweetMessageRead = storage.loadTweetMessage();
		
		final JSONArray arrayPartOfTweet = new JSONArray(arrayPartOfTweetAsString);		
		assertThat(READ_WRITE_MISMATCH_ERROR_MESSAGE, tweetMessageRead, is(arrayPartOfTweet));
	}
}