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
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.neomatrix369.apiworld.APIReader;

import com.google.gson.Gson;


/**
 * Util class UtilityFunctions.
 * 
 * @author Mani Sarkar
 *
 */
public final class UtilityFunctions {
    
    private static final Logger LOGGER = Logger.getLogger(UtilityFunctions.class.getName());
    
    private static final String CURRENT_PATH_MSG = "Current path: ";
    private static final String ERROR_DUE_TO_MSG = "Error due to: %s%n";
    
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
            LOGGER.warning("Invalid token.");
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
            System.out.format(ERROR_DUE_TO_MSG, e.getMessage());
            String currentPath = new File(".").getCanonicalPath();
            System.out.format(CURRENT_PATH_MSG + currentPath);
        } catch (IOException e) {
            System.out.format(ERROR_DUE_TO_MSG, e.getMessage());
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
}