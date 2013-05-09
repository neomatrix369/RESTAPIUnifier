package examples.YQLAPI;

import static apiworld.ResultType.rtXML;
import static apiworld.ResultType.rtJSON;

import apiworld.FinalURLNotGeneratedException;
import examples.YQLAPI.BaseYQLAPI;

public final class YQLAPI_webservices {
	private YQLAPI_webservices() {
		// Hide utility class constructor
	}

	/**
	 * This example does not explicitly require an API key, but for other API calls
	 * an API key maybe required - see YQL's API documentation for further details.
	 * 
	 * API provider URL: http://developer.yahoo.com/yql/
	 * 
	 * Required settings file to run this example: 
	 *    resources/yql_settings.properties 
	 * 
	 * containing 
	 *    APIKey=[xxxxx]
	 * 
	 * [xxxxx] = is APIKey needed to get authentication from yahoo.com to be
	 * able to make any API calls.
	 * 
	 */
	
	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException {
		YQLAPIWebServices yqlAPIWebServices = new YQLAPIWebServices("", "?",
				rtJSON.toString(), "show tables");
		System.out.format("%s", yqlAPIWebServices.getFetchedResults());
		
		yqlAPIWebServices = new YQLAPIWebServices("", "?",
				rtXML.toString(), "show tables", null);
		System.out.format("%s", yqlAPIWebServices.getFetchedResults());
	}
}

class YQLAPIWebServices extends BaseYQLAPI {
	YQLAPIWebServices(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = "yql";
		String[] arrayURLParamCodes = { "format", "q"};

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}