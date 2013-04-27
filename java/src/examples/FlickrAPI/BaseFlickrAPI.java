package examples.FlickrAPI;

import apiworld.APIBuilder;
import apiworld.APIReader;

import apiworld.APIKeyNotAssignedException;
import apiworld.BaseURLNotAssignedException;
import apiworld.UtilityFunctions;

public class BaseFlickrAPI {
	
	private static final String FLICKR_URL_PARAM = "api_key";
	private String baseURL = "http://api.flickr.com/services/rest";
	private String paramStart = "?";
	public APIReader fetchedResults;

	protected APIReader buildAPIReadyToExecute(String apiKey, String apiCommand, String paramStart,
			String[] arrayURLParamCodes, String... params) {
		APIBuilder apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.setCommand(apiCommand);
		apiBuilder.setParamStart(paramStart);
		apiBuilder.setAPIKey(FLICKR_URL_PARAM, apiKey);
		int paramCtr = 0;
		for (String eachValue : params) {
			apiBuilder.addAURLParameter(arrayURLParamCodes[paramCtr++],
					UtilityFunctions.encodeToken(eachValue));
		}

		try {
			apiBuilder.build();
			return new APIReader(apiBuilder);
		} catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
			System.out.format("%s", e.getMessage());
		}
		
		return new APIReader(baseURL);
	}
	
	public String getFetchedResults() {
		if (fetchedResults != null) {
			return fetchedResults.getFetchedResults();
		}
		return "";
	}
}