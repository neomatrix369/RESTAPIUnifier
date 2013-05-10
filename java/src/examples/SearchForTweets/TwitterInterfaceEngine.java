package examples.SearchForTweets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterInterfaceEngine {
	
	private static final String ERROR_WHILE_CONVERTING_AN_URL_STRING_INTO_AN_URI = "Error while converting an URL string into an URI: %s. %n";
	private static final String ERROR_WHILE_ACCESSING_THE_HTTP_SERVER = "Error while accessing the http server:%s. %n";
	private static final String HTTP_GET_METHOD_TOKEN = "GET";
	private static final String TWITTER_SEARCH_API_TEMPLATE = "http://search.twitter.com/search.json?q=%s";
	
	private static final Logger COMMON_LOGGER = Logger.getLogger(TwitterInterfaceEngine.class.getName()); 

	public String searchTweets(String usingSearchTerms) throws IOException {
		final String encodedTerms = java.net.URLEncoder.encode(usingSearchTerms, "ISO-8859-1");
		return getResponseFromTwitter(
				String.format(TWITTER_SEARCH_API_TEMPLATE, encodedTerms)); 
	}

	public String getResponseFromTwitter(String usingURL) throws IOException {
		URL url = null;
		URI usingURI = null;
		try {
			usingURI = new URI(usingURL);
		} catch (URISyntaxException ex) {
			COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_CONVERTING_AN_URL_STRING_INTO_AN_URI, ex.getMessage()));
		}
		HttpURLConnection httpURLConnection;
		BufferedReader bufferReader;
		String eachBufferLine;
		String result = "";
		try {
			url = usingURI.toURL();
			
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod(HTTP_GET_METHOD_TOKEN);
			bufferReader = new BufferedReader(
					new InputStreamReader(httpURLConnection.getInputStream()));
			while ((eachBufferLine = bufferReader.readLine()) != null) {
				result += eachBufferLine;
			}
			bufferReader.close();
		} catch (Exception e) {
			COMMON_LOGGER.log(Level.SEVERE, String.format(ERROR_WHILE_ACCESSING_THE_HTTP_SERVER, e.getMessage()));
		}
		return result;
	}
}