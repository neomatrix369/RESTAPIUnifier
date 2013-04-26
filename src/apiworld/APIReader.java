package apiworld;

import java.io.*;
import java.net.*;

import apiworld.BaseAPIClass;

public class APIReader extends BaseAPIClass {

	private static final String CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT = ">>> Connecting to URL: <%s>, this may take a moment.%n";
	private static final String READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT = ">>> Reading results returned, this may take a moment...%n";
	private static final String READING_COMPLETED = ">>> Reading completed...%n";
	private static final String CONNECTION_CLOSED = ">>> Connection closed!%n";
	private static final String ERROR_CONNECTING_TO_THE_WEBSITE = ">>> Error connecting to the website: %s%n";
	private static final String INPUT_URL_STRING = ">>> Input URL String: %s%n";
	private static final String ERROR_DUE_TO = "Error due to: %s%n";

	protected APIReader() {
		super();
	}

	public APIReader(String websiteBaseURL, String apiVarIdentifier,
			String apiKey) {
		String urlText = websiteBaseURL.replace(apiVarIdentifier, apiKey);
		updateURLText(urlText);
		clearAlllastHttpResults();
		executeURL(urlText);
	}

	public APIReader(String urlText) {
		clearAlllastHttpResults();
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
		System.out.format(CONNECTING_TO_URL_THIS_MAY_TAKE_A_MOMENT,
				urlText);
	}

	private void fetchDataFromURL(InputStreamReader isr) throws IOException {
		BufferedReader httpResult = null;
		try {
			httpResult = new BufferedReader(isr);
			System.out.format(READING_RESULTS_RETURNED_THIS_MAY_TAKE_A_MOMENT);

			String inputLine;
			while ((inputLine = httpResult.readLine()) != null) {
				addToLastHttpResults(inputLine);
			}

			updateAllLastHttpResults();
			System.out.format(READING_COMPLETED);
		} finally {
			if (httpResult != null) {
				httpResult.close();
			}
			System.out.format(CONNECTION_CLOSED);
		}
	}

	private void showMessageDueToMalformedURLException(String urlText,
			MalformedURLException me) {
		System.out.format(INPUT_URL_STRING, urlText);
		System.out.format(ERROR_DUE_TO, me.getMessage());
	}

	private void showMessageDueToIOException(String urlText, IOException ioe) {
		System.out.format(ERROR_CONNECTING_TO_THE_WEBSITE, urlText);
		System.out.format(ERROR_DUE_TO, ioe.getMessage());
	}
}