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

import org.apache.commons.lang3.StringUtils;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
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
        if (isBlank(baseURL)) {
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
        this.finalURL = format(TWO_TOKENS, finalURL, getValidatedAPIKey(apiKey));
    }

    private String getValidatedAPIKey(String apiKey) throws APIKeyNotAssignedException {
        if (apiKeyIsRequired && isBlank(apiKey)) {
            throw new APIKeyNotAssignedException();
        }
        return apiKey;
    }

    private void buildFinalURLWithParametersToken() {
        if (!urlParameters.isEmpty()) {
            String urlParameterTokens = "";
            for (Map.Entry<String, String> eachKeyValuePair : urlParameters.entrySet()) {
                if (eachKeyValuePair.getKey() != null && eachKeyValuePair.getValue() != null) {
                    String eachToken = format(THREE_TOKENS, eachKeyValuePair.getKey(), VALUE_SEPARATOR,
                            eachKeyValuePair.getValue());
                    urlParameterTokens = format(THREE_TOKENS, urlParameterTokens, eachToken, PARAM_SEPARATOR);
                }
            }
            urlParameterTokens = dropTrailingSeparator(urlParameterTokens, PARAM_SEPARATOR);
            this.finalURL = format(THREE_TOKENS, finalURL, PARAM_SEPARATOR, urlParameterTokens);
        }
    }

    private void buildFinalURLWithCommandString() {
        this.finalURL = baseURL;
        if ((commandString != null) && (!commandString.isEmpty())) {
            if (!finalURL.endsWith("/")) {
                this.finalURL = format(TWO_TOKENS, finalURL, "/");
            }

            this.finalURL = format(THREE_TOKENS, finalURL, commandString, paramStart);
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
        this.apiKey = format(THREE_TOKENS, apiKey, VALUE_SEPARATOR, apiKeyValue);
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