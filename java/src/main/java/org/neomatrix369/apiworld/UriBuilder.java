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

<<<<<<< HEAD
=======
import org.apache.commons.lang3.StringUtils;
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;

import java.util.LinkedHashMap;
import java.util.Map;

<<<<<<< HEAD
=======
import static java.lang.String.format;
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
import static org.neomatrix369.apiworld.util.Utils.dropTrailingSeparator;

/**
 * @author Mani Sarkar
 */
public class UriBuilder {

    private static final String PARAM_SEPARATOR = "&";
    private static final String PARAM_START = "?";

    private static final String VALUE_SEPARATOR = "=";
    private static final String TWO_TOKENS = "%s%s";
    private static final String THREE_TOKENS = "%s%s%s";

    private final String baseURL;
    private String apiKey;
    private String commandString;
    private String finalURL;
    private Map<String, String> urlParameters = new LinkedHashMap<String, String>();
    private String paramStart = PARAM_START;
    private boolean apiKeyIsRequired = true;

    public UriBuilder(String baseURL) {
<<<<<<< HEAD
        if (baseURL == null || baseURL.trim().isEmpty()) { //FIXME use StringUtils.isEmpty instead
=======
        if (baseURL == null || baseURL.trim().isEmpty()) {
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
            throw new IllegalArgumentException("base url has to be non empty string");
        }
        this.baseURL = baseURL.trim();
    }

    public APIConnection build() throws APIKeyNotAssignedException {
        buildFinalURLWithCommandString();
        buildFinalURLWithTheAPIKey();
        buildFinalURLWithParametersToken();
        return new APIConnection(finalURL);
    }

    private void buildFinalURLWithTheAPIKey() throws APIKeyNotAssignedException {
<<<<<<< HEAD
        //FIXME refactor this, only do the assign if validAPIKey, not need for two ifs and call in the middle which does the same ifs
        if (!apiKeyIsRequired) {
            return;
        }

        validateAPIKey();

        if ((apiKey != null) && (!apiKey.isEmpty())) {
            this.finalURL = String.format(TWO_TOKENS, finalURL, apiKey);
        }
    }

    private boolean validateAPIKey() throws APIKeyNotAssignedException {
        if (!apiKeyIsRequired) {
            return true;
        }

        if ((apiKey == null) || (apiKey.trim().isEmpty())) {//FIXME use StringUtils.isEmpty instead
            throw new APIKeyNotAssignedException();
        }

        return true;
=======
        this.finalURL = format(TWO_TOKENS, finalURL, getValidatedAPIKey(apiKey));
    }

    private String getValidatedAPIKey(String apiKey) throws APIKeyNotAssignedException {
        if (apiKeyIsRequired && StringUtils.isBlank(apiKey)) {
            throw new APIKeyNotAssignedException();
        }
        return apiKey;
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
    }

    private void buildFinalURLWithParametersToken() {
        if (!urlParameters.isEmpty()) {
            String urlParameterTokens = "";
            for (Map.Entry<String, String> eachKeyValuePair : urlParameters.entrySet()) {
                if (eachKeyValuePair.getKey() != null && eachKeyValuePair.getValue() != null) {
<<<<<<< HEAD
                    String eachToken = String.format(THREE_TOKENS, eachKeyValuePair.getKey(), VALUE_SEPARATOR,
                            eachKeyValuePair.getValue());
                    urlParameterTokens = String.format(THREE_TOKENS, urlParameterTokens, eachToken, PARAM_SEPARATOR);
=======
                    String eachToken = format(THREE_TOKENS, eachKeyValuePair.getKey(), VALUE_SEPARATOR,
                            eachKeyValuePair.getValue());
                    urlParameterTokens = format(THREE_TOKENS, urlParameterTokens, eachToken, PARAM_SEPARATOR);
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
                }
            }

            urlParameterTokens = dropTrailingSeparator(urlParameterTokens, PARAM_SEPARATOR);
<<<<<<< HEAD
            this.finalURL = String.format(THREE_TOKENS, finalURL, PARAM_SEPARATOR, urlParameterTokens);
=======
            this.finalURL = format(THREE_TOKENS, finalURL, PARAM_SEPARATOR, urlParameterTokens);
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
        }
    }

    private void buildFinalURLWithCommandString() {

        this.finalURL = baseURL;
        if ((commandString != null) && (!commandString.isEmpty())) {
            if (!finalURL.endsWith("/")) {
<<<<<<< HEAD
                this.finalURL = String.format(TWO_TOKENS, finalURL, "/");
            }

            this.finalURL = String.format(THREE_TOKENS, finalURL, commandString, paramStart);
=======
                this.finalURL = format(TWO_TOKENS, finalURL, "/");
            }

            this.finalURL = format(THREE_TOKENS, finalURL, commandString, paramStart);
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
        }
    }

    /**
     * Gets the finalURL attribute.
     *
     * @return String
     */
    public String getFinalURL() {
        return finalURL;
    }

    /**
     * Sets the commandString attribute.
     *
     * @param commandString String
     */
    public UriBuilder setCommand(String commandString) {
        this.commandString = commandString;
        return this;
    }

    public UriBuilder setAPIKey(String apiKey, String apiKeyValue) {
<<<<<<< HEAD
        this.apiKey = String.format(THREE_TOKENS, apiKey, VALUE_SEPARATOR, apiKeyValue);
=======
        this.apiKey = format(THREE_TOKENS, apiKey, VALUE_SEPARATOR, apiKeyValue);
>>>>>>> e11ddbb5e7d08f3b10d097eacf36a51b061b16e1
        apiKeyIsRequired = true;
        return this;
    }

    public void addUrlParameter(String key, String value) {
        this.urlParameters.put(key, value);
    }

    /**
     * Sets the paramStart attribute.
     *
     * @param paramStart String
     */
    public UriBuilder setParamStart(String paramStart) {
        this.paramStart = paramStart;
        return this;
    }

    /**
     * Sets the apiKeyIsRequired attribute.
     *
     * @param apiKeyIsRequired boolean
     */
    public UriBuilder setApiKeyIsRequired(boolean apiKeyIsRequired) {
        this.apiKeyIsRequired = apiKeyIsRequired;
        return this;
    }

    public UriBuilder setNoAPIKeyRequired() {
        this.apiKeyIsRequired = false;
        return this;
    }

}