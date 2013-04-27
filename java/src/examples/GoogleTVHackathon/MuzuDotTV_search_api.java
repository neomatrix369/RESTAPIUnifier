package examples.GoogleTVHackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import static apiworld.ResultType.*;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV_search_api {
	private MuzuDotTV_search_api() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException {
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
			muzuSearch.fetchedResults.displayHttpReqResult(rtJSON);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class MuzuSearch extends BaseMuzuAPI {

	MuzuSearch(String apiKey, String... params) {
		String apiCommand = "search";
		String[] arrayURLParamCodes = { "mySearch", "l", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };

		performAPICall(apiKey, apiCommand, arrayURLParamCodes, params);
	}
}