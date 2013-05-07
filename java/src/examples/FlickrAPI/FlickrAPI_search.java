package examples.FlickrAPI;

import static apiworld.ResultType.rtJSON;
import static apiworld.UtilityFunctions.readPropertyFrom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.FinalURLNotGeneratedException;
import examples.FlickrAPI.BaseFlickrAPI;

public final class FlickrAPI_search {
	private FlickrAPI_search() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException, IOException {
			String flickrAPIKey = readPropertyFrom(
					"resources/flickr_settings.properties", "APIKey");

			/**
			 * http://www.flickr.com/services/api/explore/flickr.photos.search
			 * 
			 * 
			 */
			FlickrSearch flickrSearch = new FlickrSearch(flickrAPIKey, "&",
					rtJSON.toString(), "hello");
			System.out.format("%s", flickrSearch.getFetchedResults());
	}
}

class FlickrSearch extends BaseFlickrAPI {

	FlickrSearch(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = "?method=flickr.photos.search";
		String[] arrayURLParamCodes = { "format", "text" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}