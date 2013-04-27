package examples.GoogleTVHackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.*;
import static apiworld.ResultType.*;

/*
 API Key: SGY60V0lEp
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV {
	private static final int SHORT_PAUSE_IN_MILLIS = 200;

	private MuzuDotTV() {
		// Hide utility class constructor
	}

	public static void main(String[] args) throws InterruptedException {
		/**
		 * "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&af=a&g=pop";
		 */
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(
					"resources/muzu_settings.properties")));
			String muzuAPIKey = prop.getProperty("APIKey");

			/**
			 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+
			 * script
			 */
			APIBuilder muzuSearch = new MuzuSearch(muzuAPIKey, "the script",
					null, rtJSON.toString());
			muzuSearch.displayHttpReqResult(rtJSON);
			Thread.sleep(SHORT_PAUSE_IN_MILLIS);

			/**
			 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&vd=0&ob=views
			 */
			APIBuilder muzuBrowse = new MuzuBrowse(muzuAPIKey, null, null,
					"views", "0", null, null, null, rtJSON.toString());
			muzuBrowse.displayHttpReqResult(rtJSON);
			Thread.sleep(SHORT_PAUSE_IN_MILLIS);

			APIBuilder muzuArtist = new MuzuArtist(
					"http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid="
							+ muzuAPIKey);
			muzuArtist.displayHttpReqResult(rtJSON);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class BaseMuzuAPI extends APIReader {
	private static final String MUZUID_URL_PARAM = "muzuid";
	private String baseURL = "http://www.muzu.tv/api/";

	protected APIReader performAPICall(String apiKey, String apiCommand,
			String[] arrayURLParamCodes, String... params) {
		addBaseURL(baseURL);
		addCommand(apiCommand);
		addAPIKey(MUZUID_URL_PARAM, apiKey);
		int paramCtr = 0;
		for (String eachValue : params) {
			addAURLParameter(arrayURLParamCodes[paramCtr++],
					UtilityFunctions.encodeToken(eachValue));
		}

		try {
			build();
			String urlText = getAPIReadyURL();
			return new APIReader(urlText);
		} catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
			System.out.format("%s", e.getMessage());
		}
		return new APIReader(baseURL);
	}
}

class MuzuBrowse extends BaseMuzuAPI {
	private APIBuilder muzuBrowse;

	MuzuBrowse(String apiKey, String... params) {
		String apiCommand = "browse";
		String[] arrayURLParamCodes = { "ft", "g", "ob", "vd", "af", "l", "of",
				"format", "country", "soundoff", "autostart", "videotype",
				"width", "height", "includeAll" };

		muzuBrowse = performAPICall(apiKey, apiCommand, arrayURLParamCodes,
				params);
	}

	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuBrowse.displayHttpReqResult(resultType);
	}
}

class MuzuSearch extends BaseMuzuAPI {
	private APIBuilder muzuSearch;

	MuzuSearch(String apiKey, String... params) {
		String apiCommand = "search";
		String[] arrayURLParamCodes = { "mySearch", "l", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };

		muzuSearch = performAPICall(apiKey, apiCommand, arrayURLParamCodes,
				params);
	}

	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuSearch.displayHttpReqResult(resultType);
	}
}

class MuzuArtist extends BaseMuzuAPI {
	private APIBuilder muzuArtist;

	MuzuArtist(String apiKey, String... params) {
		String apiCommand = "artist";
		String[] arrayURLParamCodes = { "artist_name", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };
		muzuArtist = performAPICall(apiKey, apiCommand, arrayURLParamCodes,
				params);
	}

	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuArtist.displayHttpReqResult(resultType);
	}
}