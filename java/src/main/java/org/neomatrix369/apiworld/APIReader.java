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

import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.neomatrix369.apiworld.util.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Mani Sarkar
 *
 */
public class APIReader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(APIReader.class);

    private List<String> lastHttpResult = new ArrayList<String>();
    private String urlText;

    /**
     * Parametric constructor.
     * @param apiBuilder APIBuilder
     */
    public APIReader(APIBuilder apiBuilder) {
        this.urlText = apiBuilder.getFinalURL();
    }

    /**
     * Parametric constructor.
     * @param urlText String
     */
    public APIReader(String urlText) {
        this.urlText = urlText;
    }

    /**
     * .
     * @throws FinalURLNotGeneratedException exception
     */
    public final void executeURL() throws FinalURLNotGeneratedException {
        clearAllLastHttpResults();
        if (urlText == null) {
            throw new FinalURLNotGeneratedException();
        }
        try {
            URL webSite = new URL(urlText);
            try {
                /**
                 * http://docs.oracle.com/javase/7/docs/api/java/net/
                 * HttpURLConnection.html
                 */
                URLConnection urlConnection = webSite.openConnection();
                showMessageWhileMakingConnection(urlText);
                fetchDataFromURL(new InputStreamReader(
                        urlConnection.getInputStream()));
            } catch (IOException ioe) {
                showMessageDueToIOException(urlText, ioe);
            }
        } catch (MalformedURLException me) {
            showMessageDueToMalformedURLException(urlText, me);
        }
    }

    /**
     * .
     * @param requestMethod String
     * @param requestProperties Map<String, String> 
     * @return List<String>
     * @throws FinalURLNotGeneratedException exception
     * @throws IOException 
     */
    public List<String> executeURL(String requestMethod,
            Map<String, String> requestProperties)
            throws FinalURLNotGeneratedException, IOException {
        clearAllLastHttpResults();
        if (urlText == null) {
            throw new FinalURLNotGeneratedException();
        }
        try {
            URL webSite = new URL(urlText);
            try {
                /**
                 * http://docs.oracle.com/javase/7/docs/api/java/net/
                 * HttpURLConnection.html
                 */
                HttpURLConnection urlConnection = (HttpURLConnection) webSite
                        .openConnection();
                urlConnection.setRequestMethod(requestMethod);
                if (requestProperties != null) {
                    for (Map.Entry<String, String> eachProperty : requestProperties
                            .entrySet()) {
                        urlConnection.setRequestProperty(eachProperty.getKey(),
                                eachProperty.getValue());
                    }
                }
                showMessageWhileMakingConnection(urlText);
                return fetchDataFromURL(new InputStreamReader(
                        urlConnection.getInputStream()));
            } catch (IOException ioe) {
                showMessageDueToIOException(urlText, ioe);
                throw new IOException();
            }
        } catch (MalformedURLException me) {
            showMessageDueToMalformedURLException(urlText, me);
            throw new MalformedURLException();
        }
        //return new ArrayList<String>();
    }

    /**
     * .
     * @param urlText String
     */
    private void showMessageWhileMakingConnection(String urlText) {
        LOGGER.info(String.format(Keys.INSTANCE.getKey("CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT"),
                urlText));
    }

    /**
     * .
     * @param isr InputStreamReader
     * @return List<String>
     * @throws IOException exception
     */
    private List<String> fetchDataFromURL(InputStreamReader isr)
            throws IOException {
        BufferedReader httpResult = null;
        try {
            httpResult = new BufferedReader(isr);
            LOGGER.info(Keys.INSTANCE.getKey("READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT"));

            String inputLine;
            while ((inputLine = httpResult.readLine()) != null) {
                addToLastHttpResults(inputLine);
            }

            LOGGER.info(Keys.INSTANCE.getKey("READING_COMPLETED"));
        } finally {
            if (httpResult != null) {
                httpResult.close();
            }
            LOGGER.info(Keys.INSTANCE.getKey("CONNECTION_CLOSED"));
        }

        return lastHttpResult;
    }

    /**
     * .
     * @param urlText String
     * @param me MalformedURLException
     */
    private void showMessageDueToMalformedURLException(String urlText,
            MalformedURLException me) {
        LOGGER.error(String.format(Keys.INSTANCE.getKey("INPUT_URL_STRING"), urlText));
        LOGGER.error(String.format(Keys.INSTANCE.getKey("ERROR_DUE_TO"), me.getMessage()));
    }

    /**
     * .
     * @param urlText String
     * @param ioe IOException
     */
    private void showMessageDueToIOException(String urlText, IOException ioe) {
        LOGGER.error(String.format(Keys.INSTANCE.getKey("ERROR_CONNECTING_TO_THE_WEBSITE"), urlText));
        LOGGER.error(String.format(Keys.INSTANCE.getKey("ERROR_DUE_TO"), ioe.getMessage()));
    }

    /**
     * .
     */
    private void clearAllLastHttpResults() {
        if (lastHttpResult != null) {
            lastHttpResult.clear();
        }
    }

    /**
     * .
     */
    public void displayResult() {
        if (urlText == null) {
            LOGGER.warn(Keys.INSTANCE.getKey("NO_HTTP_CONNECTIONS_MADE"));
            return;
        }

        displayResults();
    }

    /**
     * .
     */
    private void displayResults() {
        displayMessageAboutLastRetrieval(urlText);
        LOGGER.info(lastHttpResult.toString());
    }
    
    /**
     * .
     * @param urlText String
     */
    private void displayMessageAboutLastRetrieval(String urlText) {
        LOGGER.info(String.format(Keys.INSTANCE.getKey("DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL"),
                urlText));
    }

    /**
     * .
     * @param inputLine String
     */
    private void addToLastHttpResults(String inputLine) {
        lastHttpResult.add(inputLine);
    }

    /**
     * .
     * @return String
     */
    public String getFetchedResults() {
		String result = lastHttpResult.toString();
		while (result.startsWith(Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"))
				&& result.endsWith(Keys.INSTANCE.getKey("CLOSING_BOX_BRACKET"))) {
			result = dropStartAndEndDelimeters(result, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
			        Keys.INSTANCE.getKey("CLOSING_BOX_BRACKET"));
		}
		return result;
    }
}