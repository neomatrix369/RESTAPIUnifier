package examples.ImportIOAPI;

import java.io.IOException;

import apiworld.FinalURLNotGeneratedException;
import examples.ImportIOAPI.ImportIOAPI;
import static apiworld.UtilityFunctions.*;

public final class ImportIOAPI_login {

	private ImportIOAPI_login() {
		// Hide utility class constructor
	}
	
	/**
	 * API provider URL: http://docs.import.io/
	 * 
	 * Required settings file to run this example: 
	 *    resources/importio_settings.properties 
	 * 
	 * containing
	 *    username=[yyyy]
	 *    password=[zzzz]
	 * 
	 * [yyyy] = username registered with import.io 
	 * [zzzz] = password registered with import.io
	 * 
	 * API Key is handled differently via this API, an Auth token is return when a successful login occurs.
	 * Which can be further used along with secret key to perform further API actions.
	 *  
	 */
	
	public static void main(String[] args) throws InterruptedException,
			FinalURLNotGeneratedException, IOException {
		String username = readPropertyFrom("resources/importIO_settings.properties","username");
		String password = readPropertyFrom("resources/importIO_settings.properties","password");

		ImportIOSearch importIOSearch = new ImportIOSearch("", "",
				username, password);
		System.out.format("%s", importIOSearch.getFetchedResults());
	}
}

class ImportIOSearch extends ImportIOAPI {
	ImportIOSearch(String apiKey, String paramStart, String... params)
			throws FinalURLNotGeneratedException {
		String apiCommand = "login";
		String[] arrayURLParamCodes = { "username", "password" };

		fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
				arrayURLParamCodes, params);
		fetchedResults.executeURL();
	}
}