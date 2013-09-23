/**
 *
 *  Copyright (c) 2013. All rights reserved.
 *
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 2 only, as
 *  published by the Free Software Foundation.  Oracle designates this
 *  particular file as subject to the "Classpath" exception as provided
 *  by Oracle in the LICENSE file that accompanied this code.
 *
 *  This code is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  version 2 for more details (a copy is included in the LICENSE file that
 *  accompanied this code).
 *
 *  You should have received a copy of the GNU General Public License version
 *  2 along with this work; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.neomatrix369.apiworld.twitter;

import org.junit.Test;
import org.junit.Ignore;
import org.neomatrix369.examples.twitter.search.TwitterInterfaceEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TwitterSearchTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterSearchTest.class);
    
	private static final String SEARCH_TERM_WITH_SPACES = "  ";
	private static final String SEARCH_TERMS_FAILURE_ERROR_MESSAGE = "Search with two terms fails.";
	private static final String EMPTY_SEARCH = "";
	private static final String ONE_TERM = "openjdk";
	private static final String TWO_TERMS = "adopt openjdk";

	// TODO: fix test, by fixing Twitter API link
	@Test @Ignore("Twitter API is out of sync with the code")
	public void shouldReturnNoTweetsForSearchWhenNoTermsAreSupplied()  {
		String noSearchTermsSupplied = EMPTY_SEARCH;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(noSearchTermsSupplied);
		LOGGER.info(searchResult);
		assertTrue(searchResult.isEmpty());
	}

	// TODO: fix test, by fixing Twitter API link
	@Test @Ignore("Twitter API is out of sync with the code")
	public void shouldReturnNoTweetsForSearchWhenNoTermsContainsSpaces()  {
		String noSearchTermsSupplied = SEARCH_TERM_WITH_SPACES;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(noSearchTermsSupplied);
		LOGGER.info(searchResult);
		assertTrue(searchResult.isEmpty());
	}
	
	@Test @Ignore
	public void shouldReturnOneTweetWhenAnySearchTermIsPassed() throws Exception {
		String oneSearchTermSupplied = ONE_TERM;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(oneSearchTermSupplied);
		LOGGER.info(searchResult);
		assertFalse(searchResult.isEmpty());
	}
	
	@Test @Ignore
	public void shouldReturnAnyTweetWhenAnotherSearchTermIsPassed() throws Exception {
		String anySearchTermsSupplied = TWO_TERMS;
		TwitterInterfaceEngine searchTwitter = new TwitterInterfaceEngine();
		String searchResult = searchTwitter.searchTweets(anySearchTermsSupplied);
		LOGGER.info(searchResult);
		assertEquals(SEARCH_TERMS_FAILURE_ERROR_MESSAGE, false, searchResult.isEmpty());
	}
}