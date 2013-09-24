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
package org.neomatrix369.examples.twitter.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterInterfaceEngine {

    private static final String ISO_CHARSET = "ISO-8859-1";
    private static final String ERROR_WHILE_FETCHING_TWEETS_CONNECTING_TO_SERVER = "Error while fetching tweets (connecting to server)";
    private static final String ERROR_WHILE_ENCODING_TEXT = "Error while encoding text";
    private static final String ERROR_WHILE_CONVERTING_AN_URL_STRING_INTO_AN_URI = "Error while converting an URL string into an URI: %s. %n";
    private static final String ERROR_WHILE_ACCESSING_THE_HTTP_SERVER = "Error while accessing the http server:%s. %n";
    private static final String HTTP_GET_METHOD_TOKEN = "GET";
    private static final String TWITTER_SEARCH_API_TEMPLATE = "https://twitter.com/#!/search/%s";

    private static final Logger COMMON_LOGGER = Logger.getLogger(TwitterInterfaceEngine.class.getName());

    public String searchTweets(String usingSearchTerms) {
	String encodedTerms = "";
	String result = "";
	try {
	    try {
		encodedTerms = java.net.URLEncoder.encode(usingSearchTerms, ISO_CHARSET);
	    } catch (UnsupportedEncodingException ex) {
		COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_ENCODING_TEXT, ex.getMessage()));
	    }
	    result = getResponseFromTwitter(TWITTER_SEARCH_API_TEMPLATE, encodedTerms);
	} catch (IOException ex) {
	    COMMON_LOGGER.log(Level.SEVERE,
		    String.format(ERROR_WHILE_FETCHING_TWEETS_CONNECTING_TO_SERVER, ex.getMessage()));
	}
	return result;
    }

    public String getResponseFromTwitter(String usingURL, String usingEncodedTerms) throws IOException {
	URL url = null;
	URI usingURI = null;
	try {
	    usingURI = new URI(String.format(usingURL, usingEncodedTerms));
	} catch (URISyntaxException ex) {
	    COMMON_LOGGER.log(Level.SEVERE,
		    String.format(ERROR_WHILE_CONVERTING_AN_URL_STRING_INTO_AN_URI, ex.getMessage()));
	}
	HttpURLConnection httpURLConnection;
	BufferedReader bufferReader;
	String eachBufferLine;
	String result = "";
	try {
	    url = usingURI.toURL();

	    httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod(HTTP_GET_METHOD_TOKEN);
	    bufferReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	    while ((eachBufferLine = bufferReader.readLine()) != null) {
		result += eachBufferLine;
	    }
	    bufferReader.close();
	} catch (Exception e) {
	    COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_ACCESSING_THE_HTTP_SERVER, e.getMessage()));
	}
	return result;
    }
}