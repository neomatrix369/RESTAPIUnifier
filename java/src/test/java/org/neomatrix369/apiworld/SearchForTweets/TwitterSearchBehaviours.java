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
package org.neomatrix369.apiworld.SearchForTweets;

import java.io.IOException;

import org.junit.Test;
import org.neomatrix369.examples.search_tweets.TwitterInterfaceEngine;
import static org.junit.Assert.*;

public class TwitterSearchBehaviours {
	private static final String SEARCH_TERMS_FAILURE_ERROR_MESSAGE = "Search with two terms fails.";
	private static final String EMPTY_SEARCH = "";
	private static final String ONE_TERM = "openjdk";
	private static final String TWO_TERMS = "adopt openjdk";

	@Test
	public void shouldReturnNoTweetsForSearchWhenNoTermsAreSupplied()  {
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