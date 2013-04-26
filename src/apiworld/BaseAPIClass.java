package apiworld;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaseAPIClass {
	private static final String STRING_WITH_NEW_LINE_FEED = "%s%n";
	private static final String NO_HTTP_CONNECTIONS_MADE = ">>> No http connections made.";
	private static final String DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL = ">>> Displaying last retrieved results from %s%n";
	private static final String NO_RESULTS_RETURNED = ">>> No results returned.";

	public static final String CONST_PARAM_START = "?";
	public static final String CONST_URL_SEPARATOR = "/";
	public static final String CONST_PARAM_SEPARATOR = "&";
	
	private List<String> lastHttpResult = new ArrayList<String>();
	private Map<String, String> paramList = new HashMap<String, String>();
	private Document lastHttpResultXML;
	private String lastHttpResultJSON;
	private String urlText;

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
		String localURLText = buildURL(inURLText, CONST_PARAM_START);
		for (Map.Entry<String, String> eachParam : paramList.entrySet()) {
			String eachParamToken = eachParam.getKey() + "="
					+ eachParam.getValue();
			localURLText = buildURL(localURLText, eachParamToken);
			localURLText = buildURL(localURLText, CONST_PARAM_SEPARATOR);
		}

		if (localURLText.charAt(localURLText.length() - 1) == CONST_PARAM_SEPARATOR
				.charAt(0)) {
			localURLText = localURLText.substring(0, localURLText.length() - 1);
		}

		return localURLText;
	}

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
			System.out.format(STRING_WITH_NEW_LINE_FEED, NO_HTTP_CONNECTIONS_MADE);
			return;
		}

		switch (format) {
		case rtXML: {
			if (lastHttpResultXML == null) {
				System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
				return;
			}
			displayMessageAboutLastRetrieval(urlText);

			System.out.format(STRING_WITH_NEW_LINE_FEED, lastHttpResultXML.toString());
			break;
		}
		case rtJSON: {
			if (lastHttpResultJSON == null) {
				System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
				return;
			}
			displayMessageAboutLastRetrieval(urlText);

			System.out.format(STRING_WITH_NEW_LINE_FEED, lastHttpResultJSON);
			break;
		}
		case rtNone:
		default: {
			if ((lastHttpResult == null) || (lastHttpResult.size() == 0)) {
				System.out.format(STRING_WITH_NEW_LINE_FEED, NO_RESULTS_RETURNED);
				return;
			}
			displayMessageAboutLastRetrieval(urlText);

			for (String eachLine : lastHttpResult) {
				System.out.format(STRING_WITH_NEW_LINE_FEED, eachLine);
			}
			break;
		}
		}
	}

	private void displayMessageAboutLastRetrieval(String urlText) {
		System.out.format(DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL,
				urlText);
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
}