package examples.FlickrAPI;

import static apiworld.ResultType.rtJSON;

import apiworld.FinalURLNotGeneratedException;
import examples.FlickrAPI.BaseFlickrAPI;
import static apiworld.UtilityFunctions.*;

public final class FlickrAPI_getRecent {
	private FlickrAPI_getRecent() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException {

		/**
		 * http://api.flickr.com/services/rest/?method=
		 * 
		 * 
		 */
		String flickrAPIKey = readPropertyFrom(
				"resources/flickr_settings.properties", "APIKey");
		FlickrGetRecent flickrGetRecent = new FlickrGetRecent(flickrAPIKey,
				"&", rtJSON.toString());
		System.out.format("%s", flickrGetRecent.getFetchedResults());
	}
}

class FlickrGetRecent extends BaseFlickrAPI {

	FlickrGetRecent(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = "?method=flickr.photos.getRecent";
		String[] arrayURLParamCodes = { "format" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}