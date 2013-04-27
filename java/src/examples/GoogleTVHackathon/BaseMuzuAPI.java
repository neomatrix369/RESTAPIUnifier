package examples.GoogleTVHackathon;

import apiworld.APIBuilder;
import apiworld.APIReader;

import apiworld.APIKeyNotAssignedException;
import apiworld.BaseURLNotAssignedException;
import apiworld.UtilityFunctions;

public class BaseMuzuAPI {
	
	private static final String MUZUID_URL_PARAM = "muzuid";
	private String baseURL = "http://www.muzu.tv/api/";
	public APIReader fetchedResults;

	protected APIReader performAPICall(String apiKey, String apiCommand,
			String[] arrayURLParamCodes, String... params) {
		APIBuilder apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.setCommand(apiCommand);
		apiBuilder.setAPIKey(MUZUID_URL_PARAM, apiKey);
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
}