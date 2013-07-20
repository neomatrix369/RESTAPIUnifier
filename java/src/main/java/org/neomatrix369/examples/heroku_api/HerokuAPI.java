package org.neomatrix369.examples.heroku_api;

import java.io.IOException;
import java.util.Map;

import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;

public class HerokuAPI {

	private static final String HTTP_POST_METHOD = "POST";
	private static final String baseURL = "https://api.heroku.com/apps/%s";
	private static final Map<String, String> NO_PARAM_PROPERTIES_REQUIRED = null;
	protected APIReader fetchedResults;

	public static String OAuth(String apiKey) throws FinalURLNotGeneratedException, IOException {		
		String url = String.format(baseURL, apiKey);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL(HTTP_POST_METHOD, NO_PARAM_PROPERTIES_REQUIRED);
		String result = apiReader.getFetchedResults();		
		return result;
	}
}
