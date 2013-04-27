package apiworld;

import static apiworld.UtilityFunctions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiworld.APIKeyNotAssignedException;
import apiworld.BaseURLNotAssignedException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class APIBuilder {
	private static final String THREE_TOKENS = "%s%s%s";
	private static final String TWO_TOKENS = "%s%s";
	private static final String KEY_VALUE_SEPARATOR = "=";
	private static final String STRING_WITH_NEW_LINE_FEED = "%s%n";
	private static final String NO_HTTP_CONNECTIONS_MADE = ">>> No http connections made.";
	private static final String DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL = ">>> Displaying last retrieved results from %s%n";
	private static final String NO_RESULTS_RETURNED = ">>> No results returned.";
	
	private String urlText;
	private Document lastHttpResultXML;
	private String lastHttpResultJSON;
	private String baseURL;
	private String apiKey;
	private String commandString;
	private String finalURL;
	
	private List<String> lastHttpResult = new ArrayList<String>();
	private Map<String, String> urlParameters = new HashMap<String, String>();

	public void updateURLText(String urlText) {
		this.urlText = urlText;
	}

	public void updateLastHttpResult(List<String> lastHttpResult) {
		this.lastHttpResult = lastHttpResult;
	}

	public void updatelastHttpResultXML(Document lastHttpResultXML) {
		this.lastHttpResultXML = lastHttpResultXML;
	}

	public void updatelastHttpResultJSON(String lastHttpResultJSON) {
		this.lastHttpResultJSON = lastHttpResultJSON;
	}

	public void clearAllLastHttpResults() {
		if (lastHttpResult != null) {
			lastHttpResult.clear();
		}
		lastHttpResultXML = null;
		
		if (lastHttpResultJSON != null) {
			lastHttpResultJSON = "";
		}
	}

	public void displayHttpReqResult(ResultType format) {
		if (urlText == null) {
			System.out.format(STRING_WITH_NEW_LINE_FEED,
					NO_HTTP_CONNECTIONS_MADE);
			return;
		}

		switch (format) {
		case rtXML: {
			displayResultsForResultTypeXML();
			break;
		}
		case rtJSON: {
			displayResultsForResultTypeJSON();
			break;
		}
		case rtNone:
		default: {
			displayResultsForAllOtherResultTypes();
			break;
		}
		}
	}

	private void displayResultsForAllOtherResultTypes() {
		if ((lastHttpResult == null) || (lastHttpResult.size() == 0)) {
			System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
			return;
		}
		displayMessageAboutLastRetrieval(urlText);

		for (String eachLine : lastHttpResult) {
			System.out.format(STRING_WITH_NEW_LINE_FEED, eachLine);
		}
	}

	private void displayResultsForResultTypeJSON() {
		if (lastHttpResultJSON == null) {
			System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
			return;
		}
		displayMessageAboutLastRetrieval(urlText);

		System.out.format(STRING_WITH_NEW_LINE_FEED, lastHttpResultJSON);
	}

	private void displayResultsForResultTypeXML() {
		if (lastHttpResultXML == null) {
			System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
			return;
		}
		displayMessageAboutLastRetrieval(urlText);

		System.out.format(STRING_WITH_NEW_LINE_FEED,
				lastHttpResultXML.toString());
	}

	private void displayMessageAboutLastRetrieval(String urlText) {
		System.out.format(DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL, urlText);
	}

	protected void updateAllLastHttpResults() {
		lastHttpResultXML = stringToXML(lastHttpResult.toString());
		lastHttpResultJSON = lastHttpResult.toString();
	}

	public Document stringToXML(String string) {
		String localString = string.substring(1, string.length() - 1);
		localString = localString.replaceAll(">,", ">");
		return Jsoup.parse(localString);
	}

	protected void addToLastHttpResults(String inputLine) {
		lastHttpResult.add(inputLine);
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