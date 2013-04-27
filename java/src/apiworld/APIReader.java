package apiworld;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;

import apiworld.APIBuilder;

public class APIReader {

	private static final String CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT = ">>> Connecting to URL: <%s>, this may take a moment.%n";
	private static final String READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT = ">>> Reading results returned, this may take a moment...%n";
	private static final String READING_COMPLETED = ">>> Reading completed...%n";
	private static final String CONNECTION_CLOSED = ">>> Connection closed!%n";
	private static final String ERROR_CONNECTING_TO_THE_WEBSITE = ">>> Error connecting to the website: %s%n";
	private static final String INPUT_URL_STRING = ">>> Input URL String: %s%n";
	private static final String ERROR_DUE_TO = "Error due to: %s%n";

	private Document lastHttpResultXML;
	private String lastHttpResultJSON;
	private final static Logger LOGGER = Logger.getLogger(APIReader.class
			.getName());

	private static final String STRING_WITH_NEW_LINE_FEED = "%s%n";
	private static final String NO_HTTP_CONNECTIONS_MADE = ">>> No http connections made.";
	private static final String DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL = ">>> Displaying last retrieved results from %s%n";
	private static final String NO_RESULTS_RETURNED = ">>> No results returned.";

	private List<String> lastHttpResult = new ArrayList<String>();
	private String urlText;

	public APIReader(APIBuilder apiBuilder) {
		this.urlText = apiBuilder.getAPIReadyURL();
		clearAllLastHttpResults();
		executeURL(urlText);
	}

	public APIReader(String websiteBaseURL, String apiVarIdentifier,
			String apiKey) {
		this.urlText = websiteBaseURL.replace(apiVarIdentifier, apiKey);
		clearAllLastHttpResults();
		executeURL(urlText);
	}

	public APIReader(String urlText) {
		this.urlText = urlText;
		clearAllLastHttpResults();
		executeURL(urlText);
	}

	final void executeURL(String urlText) {
		try {
			URL webSite = new URL(urlText);
			try {
				/**
				 * http://docs.oracle.com/javase/7/docs/api/java/net/
				 * HttpURLConnection.html
				 */
				URLConnection urlConnection = webSite.openConnection();
				showMessageWhileMakingConnection(urlText);
				fetchDataFromURL(new InputStreamReader(
						urlConnection.getInputStream()));
			} catch (IOException ioe) {
				showMessageDueToIOException(urlText, ioe);
			}
		} catch (MalformedURLException me) {
			showMessageDueToMalformedURLException(urlText, me);
		}
	}

	private void showMessageWhileMakingConnection(String urlText) {
		LOGGER.info(String.format(CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT,
				urlText));
	}

	private void fetchDataFromURL(InputStreamReader isr) throws IOException {
		BufferedReader httpResult = null;
		try {
			httpResult = new BufferedReader(isr);
			LOGGER.info(READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT);

			String inputLine;
			while ((inputLine = httpResult.readLine()) != null) {
				addToLastHttpResults(inputLine);
			}

			updateAllLastHttpResults();
			LOGGER.info(READING_COMPLETED);
		} finally {
			if (httpResult != null) {
				httpResult.close();
			}
			LOGGER.info(CONNECTION_CLOSED);
		}
	}

	private void showMessageDueToMalformedURLException(String urlText,
			MalformedURLException me) {
		LOGGER.severe(String.format(INPUT_URL_STRING, urlText));
		LOGGER.severe(String.format(ERROR_DUE_TO, me.getMessage()));
	}

	private void showMessageDueToIOException(String urlText, IOException ioe) {
		LOGGER.severe(String.format(ERROR_CONNECTING_TO_THE_WEBSITE, urlText));
		LOGGER.severe(String.format(ERROR_DUE_TO, ioe.getMessage()));
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
			LOGGER.info(String.format(STRING_WITH_NEW_LINE_FEED,
					NO_RESULTS_RETURNED));
			return;
		}
		displayMessageAboutLastRetrieval(urlText);

		for (String eachLine : lastHttpResult) {
			LOGGER.info(String.format(STRING_WITH_NEW_LINE_FEED, eachLine));
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
		lastHttpResultXML = UtilityFunctions.stringToXML(lastHttpResult
				.toString());
		lastHttpResultJSON = lastHttpResult.toString();
	}

	protected void addToLastHttpResults(String inputLine) {
		lastHttpResult.add(inputLine);
	}
}