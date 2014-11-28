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

import org.neomatrix369.apiworld.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Mani Sarkar
 */
public class APIReader {

    private static final String MSG_ERROR_CONNECTING = ">>> Error connecting to the website: %s";
    private static final String MSG_ERROR_DUE_TO = "Error due to: %s";
    private static final String MSG_INPUT_URL_STRING = ">>> Input URL String: %s";
    private static final String MSG_READING_COMPLETED = ">>> Reading completed...";
    private static final String MSG_CONNECTING_TO_URL = ">>> Connecting to URL: <%s>, this may take a moment.";
    private static final String MSG_READING_RESULTS_RETURNED = ">>> Reading results returned, this may take a moment...";

    private static final Logger logger = LoggerFactory.getLogger(APIReader.class);
    private URL url;

    private Map<String, String> headers = new HashMap<String, String>();

    public APIReader(UriBuilder uriBuilder) {
        constructUrl(uriBuilder.getFinalURL());
    }

    public APIReader(String url) {
        constructUrl(url);
    }

    //FIXME This constructor is used only in APIReaderTest, but it should probably be the only constructor for this class if we want the class to be easy to test
    public APIReader(URL url) {
        this.url = url;
    }

    public APIReader setHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public String executeGetUrl() throws IOException {
        Map<String, String> requestProperties = new HashMap<String, String>();
        requestProperties.put("User-Agent", "RestAPIUnifier");
        return executeGetUrl(requestProperties);
    }

    public String executePostUrl() throws IOException {
        return executePostUrl("");
    }

    public String executePostUrl(String urlParameters) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        preparePostUrl(urlConnection);

        // TODO: work with null
        // urlConnection.setRequestProperty("Content-Length", "" +
        // Integer.toString(urlParameters.getBytes().length));

        setUrlParameters(urlConnection, urlParameters);
        return fireRequest(urlConnection);
    }

    public String executeGetUrl(Map<String, String> requestProperties) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        prepareGetRequest(urlConnection, requestProperties);
        return fireRequest(urlConnection);
    }

    private void preparePostUrl(HttpURLConnection urlConnection) throws ProtocolException {
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setInstanceFollowRedirects(false);
        urlConnection.setUseCaches(false);

        Map<String, String> requestProperties = new HashMap<String, String>();
        requestProperties.put("Content-Type", "application/x-www-form-urlencoded");
        requestProperties.put("charset", "utf-8");
        requestProperties.putAll(headers);
        setRequestProperties(urlConnection, requestProperties);
    }

    private void setUrlParameters(HttpURLConnection urlConnection, String urlParameters) throws IOException {
        if (isNotBlank(urlParameters)) {
            writeUrlParameters(urlParameters, urlConnection);
        }
    }

    private void prepareGetRequest(HttpURLConnection urlConnection, Map<String, String> requestProperties) throws ProtocolException {
        urlConnection.setRequestMethod("GET");
        setRequestProperties(urlConnection, requestProperties);
    }

    private void setRequestProperties(HttpURLConnection urlConnection, Map<String, String> requestProperties) {
        if (requestProperties != null) {
            for (Map.Entry<String, String> property : requestProperties.entrySet()) {
                urlConnection.setRequestProperty(property.getKey(), property.getValue());
            }
        }
    }

    private void constructUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            logger.error(String.format(MSG_INPUT_URL_STRING, url));
            logger.error(String.format(MSG_ERROR_DUE_TO, e.getMessage()));
            throw new IllegalArgumentException("Final URL does not exist.");
        }
    }

    private String fireRequest(HttpURLConnection urlConnection) throws IOException {
        try {
            logger.info(String.format(MSG_CONNECTING_TO_URL, url));
            InputStream inputStream = urlConnection.getInputStream();
            return getResponse(new InputStreamReader(inputStream));
        } catch (IOException ioException) {
            showMessageDueToIOException(url.toString(), ioException);
            throw ioException;
        } finally {
            urlConnection.disconnect();
        }
    }

    private void writeUrlParameters(String urlParameters, HttpURLConnection urlConnection) throws IOException {
        logger.info("url parameter: " + urlParameters);
        Writer wr = createWriter(urlConnection.getOutputStream());
        wr.write(urlParameters);
        wr.flush();
        wr.close();
    }

    //access level package for testing purposes
    Writer createWriter(OutputStream outputStream) {
        return new PrintWriter(outputStream);
    }

    private String getResponse(InputStreamReader isr) throws IOException {
        return dropDelimitersFromResponse(readResponse(isr).toString());
    }

    private String dropDelimitersFromResponse(String result) {
        while (result.startsWith(Utils.OPENING_BOX_BRACKET) && result.endsWith(Utils.CLOSING_BOX_BRACKET)) {
            result = Utils.dropStartAndEndDelimiters(result);
        }
        return result;
    }

    private List<String> readResponse(InputStreamReader inputStreamReader) throws IOException {
        BufferedReader httpResponse = null;
        List<String> response = new ArrayList<String>();
        try {
            httpResponse = new BufferedReader(inputStreamReader);
            logger.info(MSG_READING_RESULTS_RETURNED);

            String inputLine;
            while ((inputLine = httpResponse.readLine()) != null) {
                response.add(inputLine);
            }

            logger.info(MSG_READING_COMPLETED);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            logger.info(">>> Connection closed!");
        }
        return response;
    }

    private void showMessageDueToIOException(String urlText, IOException ioe) {
        logger.error(String.format(MSG_ERROR_CONNECTING, urlText));
        logger.error(String.format(MSG_ERROR_DUE_TO, ioe.getMessage()));
    }

}