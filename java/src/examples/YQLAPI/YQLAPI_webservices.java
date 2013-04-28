package examples.YQLAPI;

import static apiworld.ResultType.rtXML;
import static apiworld.ResultType.rtJSON;

import apiworld.FinalURLNotGeneratedException;
import examples.YQLAPI.BaseYQLAPI;

public final class YQLAPI_webservices {
	private YQLAPI_webservices() {
		// Hide utility class constructor
	}

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