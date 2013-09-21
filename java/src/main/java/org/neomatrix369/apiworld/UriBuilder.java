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

import static org.neomatrix369.apiworld.util.UtilityFunctions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.Keys;

/**
 * 
 * @author Mani Sarkar
 *
 */
public class UriBuilder {

    private static final String KEY_COMMAND_URL_SEPARATOR = Keys.INSTANCE.getKey("COMMAND_URL_SEPARATOR");
	private static final String KEY_PARAM_SEPARATOR = Keys.INSTANCE.getKey("PARAM_SEPARATOR");
	private static final String KEY_VALUE_SEPARATOR = Keys.INSTANCE.getKey("KEY_VALUE_SEPARATOR");
	private static final String KEY_THREE_TOKENS = Keys.INSTANCE.getKey("THREE_TOKENS");
	private static final String KEY_TWO_TOKENS = Keys.INSTANCE.getKey("TWO_TOKENS");
	private static final String KEY_PARAM_START = Keys.INSTANCE.getKey("PARAM_START");
	
	private String baseURL;
    private String apiKey;
    private String commandString;
    private String finalURL;
    private Map<String, String> urlParameters = new LinkedHashMap<String, String>();
    private String paramStart = KEY_PARAM_START;
    private boolean apiKeyIsRequired = true;

    
    public UriBuilder(String baseURL) {
    	this.baseURL = baseURL;
    }

    /**
     * .
     * @return APIBuilder
     * @throws BaseURLNotAssignedException exception
     * @throws APIKeyNotAssignedException exception
     */
    public APIConnection build() throws BaseURLNotAssignedException,
            APIKeyNotAssignedException {
        buildFinalURLWithCommandString();
        buildFinalURLWithTheAPIKey();
        buildFinalURLWithParametersToken();
        return new APIConnection(finalURL);
    }

    /**
     * .
     * @throws APIKeyNotAssignedException exception
     */
    private void buildFinalURLWithTheAPIKey() throws APIKeyNotAssignedException {
        if (!apiKeyIsRequired) {
            return;
        }

        validateAPIKey();

        if ((apiKey != null) && (!apiKey.isEmpty())) {
            this.finalURL = String.format(KEY_TWO_TOKENS, finalURL, apiKey);
        }
    }

    /**
     * .
     * @return boolean
     * @throws APIKeyNotAssignedException exception
     */
    private boolean validateAPIKey() throws APIKeyNotAssignedException {
        if (!apiKeyIsRequired) {
            return true;
        }

        if ((apiKey == null) || (apiKey.trim().isEmpty())) {
            throw new APIKeyNotAssignedException();
        }

        return true;
    }

    /**
     * .
     */
    private void buildFinalURLWithParametersToken() {
        if (!urlParameters.isEmpty()) {
            String urlParameterTokens = "";
            for (Map.Entry<String, String> eachKeyValuePair : urlParameters
                    .entrySet()) {
                if (eachKeyValuePair.getKey() != null && eachKeyValuePair.getValue() != null) {
					String eachToken = String.format(KEY_THREE_TOKENS,
							eachKeyValuePair.getKey(), KEY_VALUE_SEPARATOR,
							eachKeyValuePair.getValue());
					urlParameterTokens = String.format(KEY_THREE_TOKENS,
							urlParameterTokens, eachToken, KEY_PARAM_SEPARATOR);
                }
            }

			urlParameterTokens = dropTrailingSeparator(urlParameterTokens,
					KEY_PARAM_SEPARATOR);
			this.finalURL = String.format(KEY_THREE_TOKENS, finalURL,
					KEY_PARAM_SEPARATOR, urlParameterTokens);
        }
    }

    /**
     * .
     * @throws BaseURLNotAssignedException exception
     */
    private void buildFinalURLWithCommandString()
            throws BaseURLNotAssignedException {
        validateBaseURL();
        baseURL = baseURL.trim();

        this.finalURL = baseURL.trim();
        if ((commandString != null) && (!commandString.isEmpty())) {
            if (!doesHaveSeparator(this.finalURL, KEY_COMMAND_URL_SEPARATOR)) {
                this.finalURL = String.format(KEY_TWO_TOKENS, finalURL,
                        KEY_COMMAND_URL_SEPARATOR);
            }

            this.finalURL = String.format(KEY_THREE_TOKENS, finalURL,
                    commandString, paramStart);
        }
    }

    /**
     * .
     * @return boolean
     * @throws BaseURLNotAssignedException exception
     */
    private boolean validateBaseURL() throws BaseURLNotAssignedException {
        if ((baseURL == null) || (baseURL.trim().isEmpty())) {
            throw new BaseURLNotAssignedException();
        }

        return true;
    }

    /**
     * Gets the finalURL attribute.
     * @return String
     */
    public String getFinalURL() {
        return finalURL;
    }

    /**
     * Sets the commandString attribute.
     * @param commandString String
     */
    public UriBuilder setCommand(String commandString) {
        this.commandString = commandString;
        return this;
    }

    /**
     * .
     * @param key String
     * @param value String
     */
    public UriBuilder setAPIKey(String key, String value) {
        this.apiKey = String.format(KEY_THREE_TOKENS, key, KEY_VALUE_SEPARATOR, value);
        apiKeyIsRequired = true;
        return this;
    }

    /**
     * .
     * @param key String
     * @param value String
     */
    public void addAURLParameter(String key, String value) {
        this.urlParameters.put(key, value);
    }

    /**
     * Sets the paramStart attribute. 
     * @param paramStart String
     */
    public UriBuilder setParamStart(String paramStart) {
        this.paramStart = paramStart;
        return this;
    }

    /**
     * Sets the apiKeyIsRequired attribute.
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