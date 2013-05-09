package examples.TwitterAPI;

import static apiworld.ResultType.rtJSON;

import apiworld.FinalURLNotGeneratedException;;

public final class TwitterAPI_search {
	private TwitterAPI_search() {
		// Hide utility class constructor
	}

	/**
	 * This example does not explicitly require an API key, but for other API calls
	 * an API key is required - see Twitter's API documentation for further details.
	 * 
	 * API provider URL: https://dev.twitter.com/docs/api/1.1
	 * 
	 * Required settings file to run this example: 
	 *    resources/twitter_settings.properties 
	 * 
	 * containing 
	 *    APIKey=[xxxxx]
	 * 
	 * [xxxxx] = is APIKey needed to get authentication from twitter.com to be
	 * able to make any API calls.
	 * 
	 */	
	
	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException {
		TwitterSearch twitterSearch = new TwitterSearch("", "?",
				rtJSON.toString(), "hello");
		System.out.format("%s", twitterSearch.getFetchedResults());
	}
}

class TwitterSearch extends BaseTwitterAPI {

	TwitterSearch(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = String.format("search.%s", params[0]);
		String[] arrayURLParamCodes = { null, "q" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}