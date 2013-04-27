package examples.GoogleTVHackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import apiworld.*;
import static apiworld.ResultType.*;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public class MuzuDotTV_artist_api {
	private static final int SHORT_PAUSE_IN_MILLIS = 200;

	private MuzuDotTV_artist_api() {
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

			MuzuArtist muzuArtist = new MuzuArtist(
					"http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid="
							+ muzuAPIKey);
			muzuArtist.fetchedResults.displayHttpReqResult(rtJSON);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class MuzuArtist extends BaseMuzuAPI {
	MuzuArtist(String apiKey, String... params) {
		String apiCommand = "artist";
		String[] arrayURLParamCodes = { "artist_name", "format", "country",
				"soundoff", "autostart", "videotype", "width", "height",
				"includeAll" };
		fetchedResults = performAPICall(apiKey, apiCommand, arrayURLParamCodes,
				params);
	}
}