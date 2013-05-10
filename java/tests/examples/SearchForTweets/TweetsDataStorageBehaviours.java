package com.seedcloud;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.json.JSONArray;
import com.seedcloud.TweetsDataStorage;

public class TweetsDataStorageBehaviours {
	private static final String READ_WRITE_MISMATCH_ERROR_MESSAGE = "Written and read messages do not match! Data storage error";
	private static final String TWEETS_FILENAME = "testSaveTweets.json";
	TweetsDataStorage storage;
	
	@Before
	public void setup() {
		storage = new TweetsDataStorage(TWEETS_FILENAME);
	}
			
	@Test @Ignore
	public void shouldBeAbleToWriteAndReadBackMessages() {
		final String tweetMessageWritten = "{'results':[{'from_user_name':'someUser', 'from_user_id':'‏@someonesTwitterHandle', 'text':'Body of the twitter message #hashtag1 #hashtag2 #hashtag3'}]}";
		storage.saveTweetMessage(tweetMessageWritten);
		final JSONArray tweetMessageRead = storage.loadTweetMessage();
		assertEquals(READ_WRITE_MISMATCH_ERROR_MESSAGE, tweetMessageWritten, tweetMessageRead);
	}
}