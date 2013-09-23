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
package org.neomatrix369.apiworld.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class UtilityFunctions.
 * 
 * @author Mani Sarkar
 * 
 */
public final class UtilityFunctions {

    private static final String GREATER_THAN = ">";
    private static final String GREATER_THAN_AND_COMMA = ">,";
    private static final String INVALID_TOKEN_WARNING = "Invalid token.";
    private static final String UTF_8 = "UTF-8";
    private static final String THE_TOKEN_CANNOT_BE_NULL_MSG = "The token cannot be null.";
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilityFunctions.class);

    /**
     * Hide Utility Class Constructor - Utility classes should not have a public
     * or default constructor.
     */
    private UtilityFunctions() {

    }

    /**
     * Removes the trailing separator using apache commons lang.
     * 
     * @param urlParameterTokens
     *            String
     * @param paramSeparator
     *            String
     * @return String
     */
    public static String dropTrailingSeparator(String urlParameterTokens, String paramSeparator) {
	return StringUtils.substringBeforeLast(urlParameterTokens, paramSeparator);
    }

    /**
     * Checks if url has a separator using apache commons lang.
     * 
     * @param urlString
     *            String
     * @param commandUrlSeparator
     *            String
     * @return boolean
     */
    public static boolean doesHaveSeparator(String urlString, String commandUrlSeparator) {
	return StringUtils.endsWith(urlString, commandUrlSeparator);
    }

    /**
     * Converts the token to the application/x-www-form-urlencoded MIME format.
     * 
     * @param value
     *            String
     * @return String
     */
    public static String encodeToken(String token) {

	if (token == null) {
	    throw new IllegalArgumentException(THE_TOKEN_CANNOT_BE_NULL_MSG);
	}

	String encodedToken = token;

	try {
	    encodedToken = URLEncoder.encode(token, UTF_8);
	} catch (UnsupportedEncodingException uee) {
	    LOGGER.warn(INVALID_TOKEN_WARNING);
	}

	return encodedToken;
    }

    public static boolean isAValidJSONText(String resultAsString) {
	try {
	    new JSONObject(resultAsString);
	    return true;
	} catch (JSONException ex) {
	    return false;
	}
    }

    public static String readMandatoryPropertyFrom(String propertyFilename, String propertyName) throws IOException {
	Properties prop = new Properties();
	try {
	    prop.load(new FileReader(new File(propertyFilename)));
	    return prop.getProperty(propertyName);
	} catch (FileNotFoundException e) {
	    LOGGER.info("Current path: " + new File(".").getCanonicalPath());
	    LOGGER.error(e.getMessage());

	} catch (IOException e) {
	    LOGGER.error(e.getMessage());
	}
	return "";
    }

    public static String readPropertyFrom(String propertyFilename, String propertyName) throws IOException {
	Properties prop = new Properties();
	try {
	    prop.load(new FileReader(new File(propertyFilename)));
	    return prop.getProperty(propertyName);
	} catch (IOException exeption) {
	    LOGGER.info("Current path: " + new File(".").getCanonicalPath());
	    LOGGER.error(exeption.getMessage());
	    throw exeption;
	}
    }

    public static Document stringToXML(String string) {
	String localString = string.substring(1, string.length() - 1);
	localString = localString.replaceAll(GREATER_THAN_AND_COMMA, GREATER_THAN);
	return Jsoup.parse(localString);
    }

    public static Boolean isAValidXML(String result) {
	stringToXML(result);
	return true;
    }

    public static String dropStartAndEndDelimeters(String inputString, String startDelimiter, String endDelimiter) {
	String result = inputString;
	if (result.startsWith(startDelimiter)) {
	    if (result.length() == 1) {
		result = "";
	    } else {
		result = result.substring(1, result.length());
	    }
	}

	if (result.endsWith(endDelimiter)) {
	    result = result.substring(0, result.length() - 1);
	}

	return result;
    }
}