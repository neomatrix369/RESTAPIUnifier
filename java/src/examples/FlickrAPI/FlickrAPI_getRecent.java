package examples.FlickrAPI;

import static apiworld.ResultType.rtJSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.FinalURLNotGeneratedException;
import examples.FlickrAPI.BaseFlickrAPI;

public final class FlickrAPI_getRecent {
	private FlickrAPI_getRecent() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(
					"resources/flickr_settings.properties")));
			String flickrAPIKey = prop.getProperty("APIKey");

			/**
			 * http://api.flickr.com/services/rest/?method=
			 * 
			 * 
			 */
			FlickrGetRecent flickrGetRecent = new FlickrGetRecent(flickrAPIKey,
					"&", rtJSON.toString());
			System.out.format("%s", flickrGetRecent.getFetchedResults());
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class FlickrGetRecent extends BaseFlickrAPI {

	FlickrGetRecent(String apiKey, String paramStart,
			String... params) throws FinalURLNotGeneratedException {
		String apiCommand = "?method=flickr.photos.getRecent";
		String[] arrayURLParamCodes = { "format" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand,
				paramStart, arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}