package apiworld;

import static apiworld.UtilityFunctions.*;

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