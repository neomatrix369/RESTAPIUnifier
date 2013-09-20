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
package org.neomatrix369.examples.search_tweets;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FetchTweetsPeriodically {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchTweetsPeriodically.class);

    private static final String SERVER_STARTUP_MESSAGE = "Started fetching tweets every hour!%n";
    private static final int SECONDS_IN_AN_HOUR = 3600;
    private static final int MILLISECONDS_IN_A_SECOND = 1000;
    private final TwitterInterfaceEngine twitterSearch = new TwitterInterfaceEngine();
    private static final String SOME_FIXED_TERM = "openjdk";

    private FetchTweetsPeriodically(int seconds) {
	final Timer timer;
	timer = new Timer();
	timer.schedule(new FetchTweetsPeriodicallyTask(), 0, seconds * MILLISECONDS_IN_A_SECOND);
    }

    final class FetchTweetsPeriodicallyTask extends TimerTask {
	public void run() {
	    String returnedTweets;
	    try {
		returnedTweets = twitterSearch.searchTweets(SOME_FIXED_TERM);
		// this is only to show that the system is receiving feeds, not
		// initial requirements
		// TODO: can be removed before production-ising
		LOGGER.info(returnedTweets + "%n");
		saveThe(returnedTweets);
	    } catch (Exception e) {
		LOGGER.error(e.getMessage());
	    }
	}
    }

    public static void main(String args[]) {
	new FetchTweetsPeriodically(SECONDS_IN_AN_HOUR);
	// change this value ^^^^ to another lower value i.e. 10 and re-run the
	// program
	// to see what it does for a shorted duration

	LOGGER.info(SERVER_STARTUP_MESSAGE);
    }

    private void saveThe(String receivedTweets) {
	TweetsDataStorage tweetsStorage = new TweetsDataStorage();
	tweetsStorage.saveTweetMessage(receivedTweets);
    }
}