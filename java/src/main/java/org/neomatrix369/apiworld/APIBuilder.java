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

import static org.neomatrix369.apiworld.UtilityFunctions.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class APIBuilder {

    private static final String THREE_TOKENS = "%s%s%s";
    private static final String TWO_TOKENS = "%s%s";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private String baseURL;
    private String apiKey;
    private String commandString;
    private String finalURL;
    private Map<String, String> urlParameters = new LinkedHashMap<String, String>();
    private String paramStart = PARAM_START;
    private boolean apiKeyIsRequired = true;

    public APIBuilder addBaseURL(String baseURL) {
        this.baseURL = baseURL;
        return this;
    }

    public APIBuilder build() throws BaseURLNotAssignedException,
            APIKeyNotAssignedException {
        buildFinalURLWithCommandString();
        buildFinalURLWithTheAPIKey();
        buildFinalURLWithParametersToken();

        return this;
    }

    private void buildFinalURLWithTheAPIKey() throws APIKeyNotAssignedException {
        if (doesNotRequireAPIKey()) {
            return;
        }

        validateAPIKey();

        if ((apiKey != null) && (!apiKey.isEmpty())) {
            this.finalURL = String.format(TWO_TOKENS, finalURL, apiKey);
        }
    }

    private boolean validateAPIKey() throws APIKeyNotAssignedException {
        if (doesNotRequireAPIKey()) {
            return true;
        }

        if ((apiKey == null) || (apiKey.trim().isEmpty())) {
            throw new APIKeyNotAssignedException();
        }

        return true;
    }

    private boolean doesNotRequireAPIKey() {
        return !apiKeyIsRequired;
    }

    private void buildFinalURLWithParametersToken() {
        if (!urlParameters.isEmpty()) {
            String urlParameterTokens = "";
            for (Map.Entry<String, String> eachKeyValuePair : urlParameters
                    .entrySet()) {
                if (isNotNull(eachKeyValuePair.getKey())
                        && isNotNull(eachKeyValuePair.getValue())) {
                    String eachToken = String.format(THREE_TOKENS,
                            eachKeyValuePair.getKey(), KEY_VALUE_SEPARATOR,
                            eachKeyValuePair.getValue());
                    urlParameterTokens = String.format(THREE_TOKENS,
                            urlParameterTokens, eachToken, PARAM_SEPARATOR);
                }
            }

            urlParameterTokens = dropTrailingSeparator(urlParameterTokens,
                    PARAM_SEPARATOR);
            this.finalURL = String.format(THREE_TOKENS, finalURL,
                    PARAM_SEPARATOR, urlParameterTokens);
        }
    }

    private void buildFinalURLWithCommandString()
            throws BaseURLNotAssignedException {
        validateBaseURL();
        baseURL = baseURL.trim();

        this.finalURL = baseURL.trim();
        if ((commandString != null) && (!commandString.isEmpty())) {
            if (doesNotHaveTrailingSeparator(this.finalURL,
                    COMMAND_URL_SEPARATOR)) {
                this.finalURL = String.format(TWO_TOKENS, finalURL,
                        COMMAND_URL_SEPARATOR);
            }

            this.finalURL = String.format(THREE_TOKENS, finalURL,
                    commandString, paramStart);
        }
    }

    private boolean validateBaseURL() throws BaseURLNotAssignedException {
        if ((baseURL == null) || (baseURL.trim().isEmpty())) {
            throw new BaseURLNotAssignedException();
        }

        return true;
    }

    public String getAPIReadyURL() {
        return finalURL;
    }

    public void setCommand(String commandString) {
        this.commandString = commandString;
    }

    public void setAPIKey(String key, String value) {
        this.apiKey = String.format(THREE_TOKENS, key, KEY_VALUE_SEPARATOR,
                value);
        setAPIKeyIsRequired();
    }

    private void setAPIKeyIsRequired() {
        apiKeyIsRequired = true;
    }

    public void addAURLParameter(String key, String value) {
        this.urlParameters.put(key, value);
    }

    public void setParamStart(String paramStart) {
        this.paramStart = paramStart;
    }

    public void setNoAPIKeyRequired() {
        apiKeyIsRequired = false;
    }
}