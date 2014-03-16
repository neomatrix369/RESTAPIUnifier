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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.neomatrix369.apiworld.exception.PropertyNotDefinedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Util class Utils.
 *
 * @author Mani Sarkar
 */
public final class Utils {

    public static final String OPENING_BOX_BRACKET = "[";
    public static final String CLOSING_BOX_BRACKET = "]";

    private static final String INVALID_TOKEN_WARNING = "Invalid token.";
    private static final String UTF_8 = "UTF-8";
    private static final String THE_TOKEN_CANNOT_BE_NULL_MSG = "The token cannot be null.";
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Hide Utility Class Constructor - Utility classes should not have a public
     * or default constructor.
     */
    private Utils() {
    }

    /**
     * Removes the trailing separator using apache commons lang.
     *
     * @param urlParameterTokens String
     * @param paramSeparator     String
     * @return String
     */
    public static String dropTrailingSeparator(String urlParameterTokens, String paramSeparator) {
        return StringUtils.substringBeforeLast(urlParameterTokens, paramSeparator);
    }

    /**
     * Converts the token to the application/x-www-form-urlencoded MIME format.
     *
     * @param token String
     * @return String
     */
    public static String urlEncode(String token) {

        if (token == null) {
            throw new IllegalArgumentException(THE_TOKEN_CANNOT_BE_NULL_MSG);
        }

        String encodedToken = token;

        try {
            encodedToken = URLEncoder.encode(token, UTF_8);
        } catch (UnsupportedEncodingException uee) {
            logger.warn(INVALID_TOKEN_WARNING);
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

    public static String readPropertyFrom(String propertyFilename, String propertyName) throws PropertyNotDefinedException {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader(new File(propertyFilename)));
            String propertyValue = prop.getProperty(propertyName);
            if(propertyValue != null) {
                return propertyValue;
            }
            throw new PropertyNotDefinedException("There is no property " + propertyName + " defined on file " + propertyFilename);
        } catch (IOException exception) {
            try {
                logger.info("Current path: " + new File(".").getCanonicalPath());
            } catch (IOException e) {
                logger.info("Could not determine the current path");
            }
            logger.error(exception.getMessage());
            throw new IllegalStateException("IO Exception occurred");
        }
    }

    public static String dropStartAndEndDelimiters(String inputString) {
        String result = inputString;
        if (result.startsWith(OPENING_BOX_BRACKET)) {
            if (result.length() == 1) {
                result = "";
            } else {
                result = result.substring(1, result.length());
            }
        }

        if (result.endsWith(CLOSING_BOX_BRACKET)) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

}