package examples.MuzuTVAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.FinalURLNotGeneratedException;
import static apiworld.ResultType.*;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV_search_api {
	
	private MuzuDotTV_search_api() {
		// Hide utility class constructor
	}
	
	/**
	 * API provider URL: http://www.muzu.tv/api/
	 * 
	 * Required settings file to run this example: 
	 *    resources/muzu_settings.properties 
	 * 
	 * containing 
	 *    APIKey=[xxxxx]
	 * 
	 * [xxxxx] = is APIKey needed to get authentication from muzutv.com to be
	 * able to make any API calls.
	 * 
	 */

	public static void main(String[] args) throws InterruptedException, FinalURLNotGeneratedException {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(
					"resources/muzu_settings.properties")));
			String muzuAPIKey = prop.getProperty("APIKey");

			/**
			 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&format=json&
			 * mySearch=the+script
			 * 
			 */
			MuzuSearch muzuSearch = new MuzuSearch(muzuAPIKey, "the script",
					null, rtJSON.toString());
			System.out.format("%s", muzuSearch.getFetchedResults());
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class MuzuSearch extends BaseMuzuAPI {

	MuzuSearch(String apiKey, String... params) throws FinalURLNotGeneratedException {
		String apiCommand = "search";
		String[] arrayURLParamCodes = { "mySearch", "l", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}