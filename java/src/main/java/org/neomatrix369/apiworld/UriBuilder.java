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

import static org.neomatrix369.apiworld.util.UtilityFunctions.doesHaveSeparator;
import static org.neomatrix369.apiworld.util.UtilityFunctions.dropTrailingSeparator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;

/**
 * 
 * @author Mani Sarkar
 * 
 */
public class UriBuilder {

    private static final String COMMAND_SEPARATOR = "/";
    private static final String PARAM_SEPARATOR = "&";
    private static final String PARAM_START = "?";

    private static final String VALUE_SEPARATOR = "=";
    private static final String TWO_TOKENS = "%s%s";
    private static final String THREE_TOKENS = "%s%s%s";

    private String baseURL;
    private String apiKey;
    private String commandString;
    private String finalURL;
    private Map<String, String> urlParameters = new LinkedHashMap<String, String>();
    private String paramStart = PARAM_START;
    private boolean apiKeyIsRequired = true;

    public UriBuilder(String baseURL) {
	this.baseURL = baseURL;
    }

    public APIConnection build() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
	buildFinalURLWithCommandString();
	buildFinalURLWithTheAPIKey();
	buildFinalURLWithParametersToken();
	return new APIConnection(finalURL);
    }

    private void buildFinalURLWithTheAPIKey() throws APIKeyNotAssignedException {
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

	if ((apiKey == null) || (apiKey.trim().isEmpty())) {
	    throw new APIKeyNotAssignedException();
	}

	return true;
    }

    private void buildFinalURLWithParametersToken() {
	if (!urlParameters.isEmpty()) {
	    String urlParameterTokens = "";
	    for (Map.Entry<String, String> eachKeyValuePair : urlParameters.entrySet()) {
		if (eachKeyValuePair.getKey() != null && eachKeyValuePair.getValue() != null) {
		    String eachToken = String.format(THREE_TOKENS, eachKeyValuePair.getKey(), VALUE_SEPARATOR,
			    eachKeyValuePair.getValue());
		    urlParameterTokens = String.format(THREE_TOKENS, urlParameterTokens, eachToken, PARAM_SEPARATOR);
		}
	    }

	    urlParameterTokens = dropTrailingSeparator(urlParameterTokens, PARAM_SEPARATOR);
	    this.finalURL = String.format(THREE_TOKENS, finalURL, PARAM_SEPARATOR, urlParameterTokens);
	}
    }

    private void buildFinalURLWithCommandString() throws BaseURLNotAssignedException {
	validateBaseURL();
	baseURL = baseURL.trim();

	this.finalURL = baseURL.trim();
	if ((commandString != null) && (!commandString.isEmpty())) {
	    if (!doesHaveSeparator(this.finalURL, COMMAND_SEPARATOR)) {
		this.finalURL = String.format(TWO_TOKENS, finalURL, COMMAND_SEPARATOR);
	    }

	    this.finalURL = String.format(THREE_TOKENS, finalURL, commandString, paramStart);
	}
    }

    private boolean validateBaseURL() throws BaseURLNotAssignedException {
	if ((baseURL == null) || (baseURL.trim().isEmpty())) {
	    throw new BaseURLNotAssignedException();
	}

	return true;
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
     * @param commandString
     *            String
     */
    public UriBuilder setCommand(String commandString) {
	this.commandString = commandString;
	return this;
    }

    public UriBuilder setAPIKey(String apiKey, String apiKeyValue) {
	this.apiKey = String.format(THREE_TOKENS, apiKey, VALUE_SEPARATOR, apiKeyValue);
	apiKeyIsRequired = true;
	return this;
    }

    public void addAURLParameter(String key, String value) {
	this.urlParameters.put(key, value);
    }

    /**
     * Sets the paramStart attribute.
     * 
     * @param paramStart
     *            String
     */
    public UriBuilder setParamStart(String paramStart) {
	this.paramStart = paramStart;
	return this;
    }

    /**
     * Sets the apiKeyIsRequired attribute.
     * 
     * @param apiKeyIsRequired
     *            boolean
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