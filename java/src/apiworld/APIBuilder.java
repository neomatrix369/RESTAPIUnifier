package apiworld;

import static apiworld.UtilityFunctions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import apiworld.APIKeyNotAssignedException;
import apiworld.BaseURLNotAssignedException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class APIBuilder {
	private static final String THREE_TOKENS = "%s%s%s";
	private static final String TWO_TOKENS = "%s%s";
	private static final String KEY_VALUE_SEPARATOR = "=";
	
//	private String urlText;
	private String baseURL;
	private String apiKey;
	private String commandString;
	private String finalURL;
	
	private Map<String, String> urlParameters = new HashMap<String, String>();
//	private final static Logger LOGGER = Logger.getLogger(APIBuilder.class.getName());

	
	public void updateURLText(String urlText) {
//		this.urlText = urlText;
	}


	public APIBuilder addBaseURL(String baseURL) {
		this.baseURL = baseURL;
		return this;
	}

	public APIBuilder build() throws BaseURLNotAssignedException,
			APIKeyNotAssignedException {
		buildFinalURLWithCommandString();
		validateAPIKey();
		buildFinalURLWithParametersToken();

		return this;
	}

	private boolean validateAPIKey() throws APIKeyNotAssignedException {
		if ((apiKey == null) || (apiKey.trim().isEmpty())) {
			throw new APIKeyNotAssignedException();
		}

		return true;
	}

	private void buildFinalURLWithParametersToken() {
		if (!urlParameters.isEmpty()) {
			String urlParameterTokens = "";
			for (Map.Entry<String, String> eachKeyValuePair : urlParameters.entrySet()) {
				if (isNotNull(eachKeyValuePair.getValue())) {
					String eachToken = String.format(THREE_TOKENS,
							eachKeyValuePair.getKey(), KEY_VALUE_SEPARATOR,
							eachKeyValuePair.getValue());
					urlParameterTokens = String.format(THREE_TOKENS,
							urlParameterTokens, eachToken, PARAM_SEPARATOR);
				}
			}

			urlParameterTokens = dropTrailingSeparator(urlParameterTokens,
					PARAM_SEPARATOR);
			this.finalURL = String.format(TWO_TOKENS, finalURL,
					urlParameterTokens);
		}
	}
	
	private void buildFinalURLWithCommandString()
			throws BaseURLNotAssignedException {
		validateBaseURL();
		baseURL = baseURL.trim();

		this.finalURL = baseURL.trim();
		if ((commandString != null) && (!commandString.isEmpty())) {
			if (doesNotHaveTrailingSeparator(this.finalURL, COMMAND_URL_SEPARATOR)) {
				this.finalURL = String.format(TWO_TOKENS, finalURL,
						COMMAND_URL_SEPARATOR);
			}

			this.finalURL = String.format(THREE_TOKENS, finalURL,
					commandString, PARAM_START);
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

	public void addCommand(String commandString) {
		this.commandString = commandString;
	}

	public void addAPIKey(String key, String value) {
		this.apiKey = String.format(THREE_TOKENS, key, KEY_VALUE_SEPARATOR,
				value);
		addAURLParameter(key, value);
	}

	public void addAURLParameter(String key, String value) {
		this.urlParameters.put(key, value);
	}
}