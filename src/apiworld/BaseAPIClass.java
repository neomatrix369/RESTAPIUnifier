package apiworld;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaseAPIClass {
	private static final String THREE_TOKENS = "%s%s%s";
	private static final String TWO_TOKENS = "%s%s";
	private static final String KEY_VALUE_SEPARATOR = "=";
	private static final String STRING_WITH_NEW_LINE_FEED = "%s%n";
	private static final String NO_HTTP_CONNECTIONS_MADE = ">>> No http connections made.";
	private static final String DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL = ">>> Displaying last retrieved results from %s%n";
	private static final String NO_RESULTS_RETURNED = ">>> No results returned.";

	public static final String PARAM_START = "?";
	public static final String COMMAND_URL_SEPARATOR = "/";
	public static final String URL_SEPARATOR = "/";
	public static final String PARAM_SEPARATOR = "&";

	private List<String> lastHttpResult = new ArrayList<String>();
	private Map<String, String> paramList = new HashMap<String, String>();
	private Document lastHttpResultXML;
	private String lastHttpResultJSON;
	private String urlText;
	private String baseURL;
	private String finalURL;
	private String commandString;
	private Map<String, String> urlParameters = new HashMap<String, String>();
	private String apiKey;

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

	void clearAlllastHttpResults() {
		lastHttpResult.clear();
		lastHttpResultXML = null;
		lastHttpResultJSON = "";
	}

	protected String buildURL(String baseURL, String apiCommand,
			String[] params, String[] arrayURLParamCodes,
			String[] arrayURLParamDefaultValues, Map<String, String> paramList) {
		this.paramList = paramList;

		String[] localParams = initParamList(params, arrayURLParamDefaultValues);
		localParams = buildParamList(localParams, arrayURLParamCodes);

		String localURLText = buildURL(baseURL, apiCommand);
		localURLText = addParamsToURL(localURLText, paramList);

		this.urlText = localURLText;
		return localURLText;
	}

	private String addParamsToURL(String inURLText,
			Map<String, String> paramList) {
		this.paramList = paramList;
		String localURLText = buildURL(inURLText, PARAM_START);
		for (Map.Entry<String, String> eachParam : paramList.entrySet()) {
			String eachParamToken = eachParam.getKey() + KEY_VALUE_SEPARATOR
					+ eachParam.getValue();
			localURLText = buildURL(localURLText, eachParamToken);
			localURLText = buildURL(localURLText, PARAM_SEPARATOR);
		}

		if (localURLText.charAt(localURLText.length() - 1) == PARAM_SEPARATOR
				.charAt(0)) {
			localURLText = localURLText.substring(0, localURLText.length() - 1);
		}

		return localURLText;
	}

	@SuppressWarnings("deprecation")
	private String[] buildParamList(String[] inParams, String[] inParamCodes) {
		int paramCtr = 0;
		for (String eachParam : inParamCodes) {
			if (((inParams.length > 0) && (inParams.length > paramCtr))
					&& (inParams[paramCtr] != null)) {
				paramList.put(eachParam, URLEncoder.encode(inParams[paramCtr]));
			}
			paramCtr++;
		}
		return inParams;
	}

	private String[] initParamList(String[] params, String[] defaultValues) {
		if ((params != null) && (defaultValues != null)) {

			for (int paramCtr = 0; paramCtr < params.length; paramCtr++) {
				if ((params[paramCtr] == null) || params[paramCtr].isEmpty()) {
					params[paramCtr] = defaultValues[paramCtr];
				}
			}
		}
		return params;
	}

	private String buildURL(String someURL, String suffix) {
		return someURL + suffix;
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

	public BaseAPIClass addBaseURL(String baseURL) {
		this.baseURL = baseURL;
		return this;
	}

	public BaseAPIClass build() throws  BaseURLNotAssignedException, APIKeyNotAssignedException {		
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
			for (Map.Entry<String, String> eachKeyValuePair : urlParameters
					.entrySet()) {
				String eachToken = String.format(THREE_TOKENS,
						eachKeyValuePair.getKey(), KEY_VALUE_SEPARATOR,
						eachKeyValuePair.getValue());
				urlParameterTokens = String.format(THREE_TOKENS,
						urlParameterTokens, eachToken, PARAM_SEPARATOR);
			}

			urlParameterTokens = dropTrailingSeparator(urlParameterTokens,
					PARAM_SEPARATOR);
			this.finalURL = String.format(TWO_TOKENS, finalURL, urlParameterTokens);
		}
	}

	private void buildFinalURLWithCommandString()
			throws BaseURLNotAssignedException {
		validateBaseURL();
		baseURL = baseURL.trim();

		this.finalURL = baseURL.trim();
		if ((commandString != null) && (!commandString.isEmpty())) {
			if (doesNotHaveSeparator(this.finalURL, COMMAND_URL_SEPARATOR)) {
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

	private String dropTrailingSeparator(String urlParameterTokens,
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

	private boolean doesHaveSeparator(String urlString,
			String commandUrlSeparator) {
		int lastCharIndex = urlString.length() - commandUrlSeparator.length();
		if (lastCharIndex > 0) {
			String trailingString = urlString.substring(lastCharIndex,
					lastCharIndex + commandUrlSeparator.length());
			return trailingString.equals(commandUrlSeparator);
		}
		return false;
	}

	private boolean doesNotHaveSeparator(String urlString,
			String commandUrlSeparator) {
		return !doesHaveSeparator(urlString, commandUrlSeparator);
	}

	public String getAPIReadyURL() {
		return finalURL;
	}

	public void addCommand(String commandString) {
		this.commandString = commandString;
	}

	public void addAPIKey(String key, String value) {
		this.apiKey = String.format(THREE_TOKENS, key, KEY_VALUE_SEPARATOR, value);
		this.urlParameters.put(key, value);
	}
}