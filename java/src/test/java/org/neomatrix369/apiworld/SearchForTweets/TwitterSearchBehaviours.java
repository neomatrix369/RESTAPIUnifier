package org.neomatrix369.apiworld.SearchForTweets;

import org.junit.Test;
import org.neomatrix369.examples.search_tweets.TwitterInterfaceEngine;
import static org.junit.Assert.*;

public class TwitterSearchBehaviours {
	private static final String SEARCH_TERMS_FAILURE_ERROR_MESSAGE = "Search with two terms fails.";
	private static final String EMPTY_SEARCH = "";
	private static final String ONE_TERM = "openjdk";
	private static final String TWO_TERMS = "adopt openjdk";

	@Test(expected = Exception.class)  
	public void shouldReturnNoTweetsForSearchWhenNoTermsAreSupplied() throws Exception {
		String noSearchTermsSupplied = EMPTY_SEARCH;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(noSearchTermsSupplied);
		assertTrue(searchResult.isEmpty());
	}
	
	@Test
	public void shouldReturnOneTweetForSearchWhenAnyTerm() throws Exception {
		String oneSearchTermSupplied = ONE_TERM;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(oneSearchTermSupplied);
		assertFalse(searchResult.isEmpty());
	}
	
	@Test
	public void shouldReturnAnyTweetForAnotherSearchTerm() throws Exception {
		String anySearchTermsSupplied = TWO_TERMS;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(anySearchTermsSupplied);
		assertEquals(SEARCH_TERMS_FAILURE_ERROR_MESSAGE, false, searchResult.isEmpty());
	}
}