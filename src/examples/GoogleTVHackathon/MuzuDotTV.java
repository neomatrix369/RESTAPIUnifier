package examples.GoogleTVHackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
			prop.load(new FileReader(new File("resources/muzu_settings.properties")));
			String muzuAPIKey = prop.getProperty("APIKey");
	
			/**
			* http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+script
			*/
			BaseAPIClass muzuSearch = new MuzuSearch(muzuAPIKey, "the script",
					null, rtJSON.toString());
			muzuSearch.displayHttpReqResult(rtJSON);
			Thread.sleep(SHORT_PAUSE_IN_MILLIS);
	
			/**
			* http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&vd=0&ob=views
			*/
			BaseAPIClass muzuBrowse = new MuzuBrowse(muzuAPIKey, null, null,
					"views", "0", null, null, null, rtJSON.toString());
			muzuBrowse.displayHttpReqResult(rtJSON);
			Thread.sleep(SHORT_PAUSE_IN_MILLIS);
			
			BaseAPIClass muzuArtist = new MuzuArtist(
					"http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid=SGY60V0lEp");
			muzuArtist.displayHttpReqResult(rtJSON);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}			
	}
}

class BaseMuzuAPI extends APIReader {
	protected BaseMuzuAPI() {
		super();
	}

	public static final String CONST_MUZU_API_ID = "[MUZU_ID]";
	private String baseURL = "http://www.muzu.tv/api/";

	protected String getBaseURL() {
		return baseURL;
	}
}

class MuzuBrowse extends BaseMuzuAPI {
	private BaseAPIClass muzuBrowse;

	MuzuBrowse(String apiKey, String... params) {
		String apiCommand = "browse";
		String[] arrayURLParamCodes = { "ft", "g", "ob", "vd", "af", "l", "of",
				"format", "country", "soundoff", "autostart", "videotype",
				"width", "height", "includeAll" };
		String[] arrayURLParamDefaultValues = { null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null };

		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("muzuid", CONST_MUZU_API_ID);

		String urlText = buildURL(getBaseURL(), apiCommand, params,
				arrayURLParamCodes, arrayURLParamDefaultValues, paramList);

		muzuBrowse = new APIReader(urlText, CONST_MUZU_API_ID, apiKey);
	}
	
	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuBrowse.displayHttpReqResult(resultType);
	}
}

class MuzuSearch extends BaseMuzuAPI {
	private BaseAPIClass muzuSearch;
	
	MuzuSearch(String apiKey, String... params) {
		String apiCommand = "search";
		String[] arrayURLParamCodes = { "mySearch", "l", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };
		String[] arrayURLParamDefaultValues = { null, null, null, null, null,
				null, null, null, null, null };

		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("muzuid", CONST_MUZU_API_ID);

		String urlText = buildURL(getBaseURL(), apiCommand, params,
				arrayURLParamCodes, arrayURLParamDefaultValues, paramList);

		muzuSearch = new APIReader(urlText, CONST_MUZU_API_ID, apiKey);
	}
	
	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuSearch.displayHttpReqResult(resultType);
	}	
}

class MuzuArtist extends BaseMuzuAPI {
	private BaseAPIClass muzuArtist;
	
	MuzuArtist(String urlWithAPIKey, String... params) {
		String apiCommand = "artist";
		String[] arrayURLParamCodes = { "artist_name", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };
		String[] arrayURLParamDefaultValues = { null, null, null, null, null,
				null, null, null, null };

		Map<String, String> paramList = new HashMap<String, String>();
		paramList.put("muzuid", CONST_MUZU_API_ID);

		String urlText = buildURL(getBaseURL(), apiCommand, params,
				arrayURLParamCodes, arrayURLParamDefaultValues, paramList);

		muzuArtist = new APIReader(urlText, CONST_MUZU_API_ID, urlWithAPIKey);
	}
	
	@Override
	public void displayHttpReqResult(ResultType resultType) {
		muzuArtist.displayHttpReqResult(resultType);
	}	
}