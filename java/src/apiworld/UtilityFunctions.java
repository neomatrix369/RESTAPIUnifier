package apiworld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;

public final class UtilityFunctions {
	private UtilityFunctions() {
		// Hide Utility Class Constructor - Utility classes should not have a
		// public or default constructor.
	}

	public static final String PARAM_START = "?";
	public static final String COMMAND_URL_SEPARATOR = "/";
	public static final String URL_SEPARATOR = "/";
	public static final String PARAM_SEPARATOR = "&";

	public static String dropTrailingSeparator(String urlParameterTokens,
			String paramSeparator) {
		int lastCharIndex = urlParameterTokens.length()
				- paramSeparator.length();
		if (lastCharIndex > 0) {
			String trailingString = urlParameterTokens.substring(lastCharIndex,
					lastCharIndex + paramSeparator.length());
			if (trailingString.equals(paramSeparator)) {
				return urlParameterTokens.substring(0, lastCharIndex);
			}
		}
		return urlParameterTokens;
	}

	public static boolean doesHaveSeparator(String urlString,
			String commandUrlSeparator) {
		int lastCharIndex = urlString.length() - commandUrlSeparator.length();
		if (lastCharIndex > 0) {
			String trailingString = urlString.substring(lastCharIndex,
					lastCharIndex + commandUrlSeparator.length());
			return trailingString.equals(commandUrlSeparator);
		}
		return false;
	}

	public static boolean doesNotHaveTrailingSeparator(String urlString,
			String commandUrlSeparator) {
		return !doesHaveSeparator(urlString, commandUrlSeparator);
	}

	@SuppressWarnings("deprecation")
	public static String encodeToken(String value) {
		if (value == null) {
			return null;
		}
		return URLEncoder.encode(value);
	}

	public static boolean isNotNull(Object value) {
		return value != null;
	}

	public static Document stringToXML(String string) {
		String localString = string.substring(1, string.length() - 1);
		localString = localString.replaceAll(">,", ">");
		return Jsoup.parse(localString);
	}

	public static Boolean isAValidJSONText(String resultAsString) {
		Gson gson = new Gson();
		gson.toJson(resultAsString);
		return true;
	}

	public static Boolean isAValidXML(String result) {
		Document doc = stringToXML(result);
		return true;
	}

	public static String readPropertyFrom(String propertyFilename,
			String propertyName) {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(propertyFilename)));
			return prop.getProperty(propertyName);
		} catch (FileNotFoundException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		} catch (IOException e) {
			System.out.format("Error due to: %s%n", e.getMessage());
		}
		return "";
	}
}