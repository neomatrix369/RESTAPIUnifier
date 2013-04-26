package apiworld;
import java.io.*;
import java.net.*;

import apiworld.BaseAPIClass;

public class APIReader extends BaseAPIClass {
	
	protected APIReader() {
		super();
	}

	public APIReader(String websiteBaseURL, String apiVarIdentifier,
			String apiKey) {
		String urlText = websiteBaseURL.replace(apiVarIdentifier, apiKey);
		updateURLText(urlText);		
		clearAlllastHttpResults();
		sendURL(urlText);
	}

	public APIReader(String urlText) {
		clearAlllastHttpResults();
		sendURL(urlText);
	}

	@SuppressWarnings("deprecation")
	final void sendURL(String urlText) {
		try {

			URL webSite = new URL(urlText);
			try {

				/**
				 * http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html
				 */
				URLConnection urlConnection = webSite.openConnection();
				System.out.format(
						">>> Connecting to URL: <%s>, this may take a moment.%n",
						urlText);
				InputStreamReader isr = new InputStreamReader(
						urlConnection.getInputStream());

				BufferedReader httpResult = null;
				try {
					httpResult = new BufferedReader(isr);
					System.out
							.format(">>> Reading results returned, this may take a moment...%n");

					String inputLine;
					while ((inputLine = httpResult.readLine()) != null) {
						addToLastHttpResults(inputLine);
					}
					
					updateAllLastHttpResults();
					System.out.format(">>> Reading completed...%n");
				} finally {
					if (httpResult != null) {
						httpResult.close();
					}
					System.out.format(">>> Connection closed!%n");
				}
			} catch (IOException ioe) {
				System.out.format(">>> Error connecting to the website: %s%n", urlText);
				System.out.format("Error due to: %s%n", ioe.getMessage());
			}
		} catch (MalformedURLException me) {
			System.out.format(">>> Input URL String: %s%n", urlText);
			System.out.format("Error due to: %s%n", me.getMessage());
		}
	}
}