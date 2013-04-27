package examples.TwitterAPI;

import static apiworld.ResultType.rtJSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.FinalURLNotGeneratedException;
import examples.FlickrAPI.BaseFlickrAPI;

public final class TwitterAPI_search {
	private TwitterAPI_search() {
		// Hide utility class constructor
	}

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