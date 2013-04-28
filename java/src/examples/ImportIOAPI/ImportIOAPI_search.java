package examples.ImportIOAPI;

import static apiworld.ResultType.rtJSON;

import apiworld.FinalURLNotGeneratedException;
import examples.ImportIOAPI.ImportIOAPI;

public final class ImportIOAPI_search {
	private ImportIOAPI_search() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException {
		importIOSearch importIOSearch = new importIOSearch("", "?",
				rtJSON.toString(), "hello");
		System.out.format("%s", importIOSearch.getFetchedResults());
	}
}

class importIOSearch extends ImportIOAPI {
	importIOSearch(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = String.format("search.%s", params[0]);
		String[] arrayURLParamCodes = { null, "q" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}