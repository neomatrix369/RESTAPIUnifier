package examples.MuzuTVAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.FinalURLNotGeneratedException;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV_artist_api {
	private MuzuDotTV_artist_api() {
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
		/**
		 * "http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid=[MUZU_ID]";
		 */
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(
					"resources/muzu_settings.properties")));
			String muzuAPIKey = prop.getProperty("APIKey");

			MuzuArtist muzuArtist = new MuzuArtist(
					"http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid="
							+ muzuAPIKey);
			
			System.out.format("%s", muzuArtist.getFetchedResults());
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class MuzuArtist extends BaseMuzuAPI {
	MuzuArtist(String apiKey, String... params) throws FinalURLNotGeneratedException {
		String apiCommand = "artist";
		String[] arrayURLParamCodes = { "artist_name", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };
		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, arrayURLParamCodes,
				params);
		fetchedResults.executeURL();
	}
}