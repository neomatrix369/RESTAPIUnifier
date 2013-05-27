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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.neomatrix369.examples.search_tweets.TweetsDataStorage;

public class SearchTermFilterBehaviours {
	TweetsDataStorage storage;
	
	@Before
	public void setup() {
		final String tweetMessageWritten = "{'results':[{'from_user_name':'someUserName', 'from_user_id':'‏@someonesTwitterHandle', 'text':'Body of the twitter message #hashtag1 #hashtag2 #hashtag3'}]}";
		storage = new TweetsDataStorage("testSavedTweets.json");
		storage.saveTweetMessage(tweetMessageWritten);		
	}
			
	// - test for term in name 
	@Test
	public void shouldFindTermInTheUserID() {
		String usingTerm = "Handle";
		Boolean foundTerm = storage.searchTermInUserIDInTweetMessage(usingTerm);
		assertEquals("Term not found in the userid", true, foundTerm);
	}
	
	// - test for term in username 
	@Test
	public void shouldFindTermInTheUsername() {
		String usingTerm = "someUserName";
		boolean foundTerm = storage.searchTermInUserNameInTweetMessage(usingTerm);
		assertEquals("Term not found in the username", true, foundTerm);		
	}
	
	// - test for term found in tweet message (body)
	@Test
	public void shouldFindTermInTheBodyOfTheTweetMessage() {
		String usingTerm = "Body";
		boolean foundTerm = storage.searchTermInBodyOfTheTweetMessage(usingTerm);
		assertEquals("Term not found in the body of the message", true, foundTerm);
	}
	
	// - test for term found in tweet message (body)
	@Test
	public void shouldFindHashtagInTheBodyOfTheTweetMessage() {
		String usingTerm = "hashtag2";
		boolean foundTerm = storage.searchTermInBodyOfTheTweetMessage(usingTerm);
		assertEquals("Term not found in the body of the message", true, foundTerm);
	}
	
	// - test for term found in any part of the tweet message 
	@Test
	public void shouldFindTermInAnyPartOfTheTweetMessage() {
		String usingTerm = "hashtag";
		boolean foundTerm = storage.searchTermAnyPartOfTheTweetMessage(usingTerm);
		assertEquals("Term not found in the whole message", true, foundTerm);
	}	
}