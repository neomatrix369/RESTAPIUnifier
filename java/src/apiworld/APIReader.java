package apiworld;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import apiworld.APIBuilder;

public class APIReader {

	private static final String CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT = ">>> Connecting to URL: <%s>, this may take a moment.";
	private static final String READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT = ">>> Reading results returned, this may take a moment...";
	private static final String READING_COMPLETED = ">>> Reading completed...";
	private static final String CONNECTION_CLOSED = ">>> Connection closed!";
	private static final String ERROR_CONNECTING_TO_THE_WEBSITE = ">>> Error connecting to the website: %s";
	private static final String INPUT_URL_STRING = ">>> Input URL String: %s";
	private static final String ERROR_DUE_TO = "Error due to: %s";

	private final static Logger LOGGER = Logger.getLogger(APIReader.class
			.getName());

	private static final String STRING_WITH_NEW_LINE_FEED = "%s%n";
	private static final String NO_HTTP_CONNECTIONS_MADE = ">>> No http connections made.";
	private static final String DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL = ">>> Displaying last retrieved results from %s";
	private static final String NO_RESULTS_RETURNED = ">>> No results returned.";

	private List<String> lastHttpResult = new ArrayList<String>();
	private String urlText;

	public APIReader(APIBuilder apiBuilder) {
		this.urlText = apiBuilder.getAPIReadyURL();
	}

	public APIReader(String websiteBaseURL, String apiVarIdentifier,
			String apiKey) {
		this.urlText = websiteBaseURL.replace(apiVarIdentifier, apiKey);
	}

	public APIReader(String urlText) {
		this.urlText = urlText;
	}

	public final void executeURL() throws FinalURLNotGeneratedException {
		clearAllLastHttpResults();
		if (urlText != null) {
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
		} else {
			throw new FinalURLNotGeneratedException();
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

	private void clearAllLastHttpResults() {
		if (lastHttpResult != null) {
			lastHttpResult.clear();
		}
	}

	public void displayHttpReqResult() {
		if (urlText == null) {
			LOGGER.warning(String.format(STRING_WITH_NEW_LINE_FEED,
					NO_HTTP_CONNECTIONS_MADE));
			return;
		}

		displayResults();
	}

	private void displayResults() {
		displayMessageAboutLastRetrieval(urlText);
		System.out.format(STRING_WITH_NEW_LINE_FEED, lastHttpResult.toString());
	}

	private void displayMessageAboutLastRetrieval(String urlText) {
		LOGGER.info(String.format(DISPLAYING_LAST_RETRIEVED_RESULTS_FROM_URL, urlText));
	}

	private void addToLastHttpResults(String inputLine) {
		lastHttpResult.add(inputLine);
	}

	public String getFetchedResults() {
		return lastHttpResult.toString();
	}
}