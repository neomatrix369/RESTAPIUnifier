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
package org.neomatrix369.apiworld;

import static org.neomatrix369.apiworld.util.UtilityFunctions.dropStartAndEndDelimeters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neomatrix369.apiworld.util.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mani Sarkar
 */
public class APIReader {

    private static final String ERROR_CONNECTING = ">>> Error connecting to the website: %s";
    private static final String ERROR_DUE_TO = "Error due to: %s";
    private static final String INPUT_URL_STRING = ">>> Input URL String: %s";

    private static final String KEY_CLOSING_BOX_BRACKET = Keys.INSTANCE.getKey("CLOSING_BOX_BRACKET");
    private static final String KEY_OPENING_BOX_BRACKET = Keys.INSTANCE.getKey("OPENING_BOX_BRACKET");
    private static final String KEY_READING_COMPLETED = Keys.INSTANCE.getKey("READING_COMPLETED");
    private static final String KEY_READING_RESULTS_RETURNED = Keys.INSTANCE
	    .getKey("READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT");
    private static final String KEY_CONNECTING_TO_URL = Keys.INSTANCE
	    .getKey("CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT");

    private static final Logger LOGGER = LoggerFactory.getLogger(APIReader.class);
    private List<String> lastHttpResult = new ArrayList<String>();
    private URL url;

    public APIReader(UriBuilder apiBuilder) {
	constructUrl(apiBuilder.getFinalURL());
    }

    public APIReader(String url) {
	constructUrl(url);
    }

    private void constructUrl(String url) {
	try {
	    this.url = new URL(url);
	} catch (MalformedURLException e) {
	    showMessageDueToMalformedURLException(url.toString(), e);
	    throw new IllegalArgumentException("Final URL does not exist.");
	}
    }

    /**
     * open new GET connection and store in lasthttpresult.
     */
    public final void executeUrl() {
	clearHttpResults();
	try {
	    URLConnection urlConnection = url.openConnection();
	    showMessageWhileMakingConnection(url.toString());
	    fetchDataFromURL(new InputStreamReader(urlConnection.getInputStream()));
	} catch (IOException ioe) {
	    showMessageDueToIOException(url.toString(), ioe);
	}
    }

    public void executeUrl(String requestMethod, Map<String, String> requestProperties) throws IOException {
	clearHttpResults();
	try {
	    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    urlConnection.setRequestMethod(requestMethod);
	    if (requestProperties != null) {
		for (Map.Entry<String, String> eachProperty : requestProperties.entrySet()) {
		    urlConnection.setRequestProperty(eachProperty.getKey(), eachProperty.getValue());
		}
	    }
	    showMessageWhileMakingConnection(url.toString());
	    fetchDataFromURL(new InputStreamReader(urlConnection.getInputStream()));
	} catch (IOException ioe) {
	    showMessageDueToIOException(url.toString(), ioe);
	    throw ioe;
	}
    }

    public String getFetchedResults() {
	String result = lastHttpResult.toString();
	while (result.startsWith(KEY_OPENING_BOX_BRACKET) && result.endsWith(KEY_CLOSING_BOX_BRACKET)) {
	    result = dropStartAndEndDelimeters(result, KEY_OPENING_BOX_BRACKET, KEY_CLOSING_BOX_BRACKET);
	}
	return result;
    }

    private void showMessageWhileMakingConnection(String urlText) {
	LOGGER.info(String.format(KEY_CONNECTING_TO_URL, urlText));
    }

    private List<String> fetchDataFromURL(InputStreamReader isr) throws IOException {
	BufferedReader httpResult = null;
	try {
	    httpResult = new BufferedReader(isr);
	    LOGGER.info(KEY_READING_RESULTS_RETURNED);

	    String inputLine;
	    while ((inputLine = httpResult.readLine()) != null) {
		addToLastHttpResults(inputLine);
	    }

	    LOGGER.info(KEY_READING_COMPLETED);
	} finally {
	    if (httpResult != null) {
		httpResult.close();
	    }
	    LOGGER.info(Keys.INSTANCE.getKey("CONNECTION_CLOSED"));
	}

	return lastHttpResult;
    }

    private void showMessageDueToMalformedURLException(String urlText, MalformedURLException me) {
	LOGGER.error(String.format(INPUT_URL_STRING, urlText));
	LOGGER.error(String.format(ERROR_DUE_TO, me.getMessage()));
    }

    private void showMessageDueToIOException(String urlText, IOException ioe) {
	LOGGER.error(String.format(ERROR_CONNECTING, urlText));
	LOGGER.error(String.format(ERROR_DUE_TO, ioe.getMessage()));
    }

    private void clearHttpResults() {
	if (lastHttpResult != null) {
	    lastHttpResult.clear();
	}
    }

    private void addToLastHttpResults(String inputLine) {
	lastHttpResult.add(inputLine);
    }

}