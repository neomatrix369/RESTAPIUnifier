package examples.GoogleTVHackathon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static apiworld.ResultType.*;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV_browse_api {

	private MuzuDotTV_browse_api() {
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
			 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&vd=0&ob=views
			 */
			MuzuBrowse muzuBrowse = new MuzuBrowse(muzuAPIKey, null, null,
					"views", "0", null, null, null, rtJSON.toString());
			muzuBrowse.fetchedResults.displayHttpReqResult(rtJSON);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
	}
}

class MuzuBrowse extends BaseMuzuAPI {
	MuzuBrowse(String apiKey, String... params) {
		String apiCommand = "browse";
		String[] arrayURLParamCodes = { "ft", "g", "ob", "vd", "af", "l", "of",
				"format", "country", "soundoff", "autostart", "videotype",
				"width", "height", "includeAll" };

		performAPICall(apiKey, apiCommand, arrayURLParamCodes,
				params);
	}
}