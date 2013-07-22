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
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilityFunctions.class);

	public static final String OPENING_BOX_BRACKET = "[";
	public static final String CLOSING_BOX_BRACKET = "]";
    
    public static final String PARAM_START = "?";
    public static final String COMMAND_URL_SEPARATOR = "/";
    public static final String PARAM_SEPARATOR = "&";

    /**
     * Hide Utility Class Constructor - Utility classes should not have a
     * public or default constructor.
     */
    private UtilityFunctions() {
        
    }

    

    /**
     * .
     * @param urlParameterTokens String
     * @param paramSeparator String
     * @return String
     */
    public static String dropTrailingSeparator(String urlParameterTokens,
            String paramSeparator) {
        int lastCharIndex = urlParameterTokens.length()
                - paramSeparator.length();
        if (lastCharIndex > 0) {
            String trailingString = urlParameterTokens.substring(lastCharIndex,
                    lastCharIndex + paramSeparator.length());
            if (trailingString.equals(paramSeparator)) {
                return urlParameterTokens.substring(0, lastCharIndex);
            }
        }
        return urlParameterTokens;
    }

    /**
     * .
     * @param urlString String
     * @param commandUrlSeparator String
     * @return booelan
     */
    public static boolean doesHaveSeparator(String urlString,
            String commandUrlSeparator) {
        
        int lastCharIndex = urlString.length() - commandUrlSeparator.length();
        if (lastCharIndex > 0) {
            String trailingString = urlString.substring(lastCharIndex,
                    lastCharIndex + commandUrlSeparator.length());
            return trailingString.equals(commandUrlSeparator);
        }
        return false;
    }
    

    /**
     * Converts the token to the application/x-www-form-urlencoded MIME format.
     * @param value String
     * @return String
     */
    public static String encodeToken(String token) {
        
        if (token == null) {
            throw new IllegalArgumentException("The token cannot be null.");
        }
        
        String encodedToken = token;
        
        try {
            encodedToken = URLEncoder.encode(token, "UTF-8");
        } catch(UnsupportedEncodingException uee) {
            LOGGER.warn("Invalid token.");
        }
        
        return encodedToken;
    }

    /**
     * .
     * @param resultAsString String
     * @return boolean
     */
    public static boolean isAValidJSONText(String resultAsString) {
        boolean valid = false;
        try {
            new JSONObject(resultAsString);
            valid = true;
        }
        catch(JSONException ex) { 
            valid = false;
        }
        return valid;
    }

    /**
     * .
     * @param propertyFilename String
     * @param propertyName String
     * @return String
     * @throws IOException exception
     */
    public static String readPropertyFrom(String propertyFilename,
            String propertyName) throws IOException {
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

	public static Document stringToXML(String string) {
		String localString = string.substring(1, string.length() - 1);
		localString = localString.replaceAll(">,", ">");
		return Jsoup.parse(localString);
	}
	
	public static Boolean isAValidXML(String result) {
		stringToXML(result);
		return true;
	}



	public static String dropStartAndEndDelimeters(String inputString, String startsWith, String endsWith) {
		String result = inputString;
		if (result.startsWith(startsWith)) {
			if (result.length() == 1) {
				result = "";
			} else {
				result = result.substring(1, result.length());
			}	
		}
		
		if (result.endsWith(endsWith)) {
			result = result.substring(0, result.length() - 1); 
		}
		
		return result;
	}
}