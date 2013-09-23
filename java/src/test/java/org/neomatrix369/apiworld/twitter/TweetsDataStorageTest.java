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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.examples.twitter.search.TweetsDataStorage;

public class TweetsDataStorageTest {
    private static final String READ_WRITE_MISMATCH_ERROR_MESSAGE = "Written and read messages do not match! Data storage error";
    private static final String TWEETS_FILENAME = "testSaveTweets.json";
    TweetsDataStorage storage;

    @Before
    public void setup() {
	storage = new TweetsDataStorage(TWEETS_FILENAME);
    }

    @Test
    public void shouldBeAbleToWriteAndReadBackMessages() throws JSONException {
	String arrayPartOfTweetAsString = "[{'from_user_name':'someUser', 'from_user_id':'‏@someonesTwitterHandle', 'text':'Body of the twitter message #hashtag1 #hashtag2 #hashtag3'}]";
	final String tweetMessageWrittenAsString = "{results: " + arrayPartOfTweetAsString + "}";
	storage.saveTweetMessage(tweetMessageWrittenAsString);

	final JSONArray tweetMessageRead = storage.loadTweetMessage();

	final JSONArray tweetMessageWrittenAsArray = new JSONArray(arrayPartOfTweetAsString);
	assertThat(READ_WRITE_MISMATCH_ERROR_MESSAGE, tweetMessageRead.toString(),
		is(tweetMessageWrittenAsArray.toString()));
    }
}