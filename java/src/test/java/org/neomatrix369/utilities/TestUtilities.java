package org.neomatrix369.utilities;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 * Created by saiubuntu on 14/06/14.
 */
public class TestUtilities {
    public static boolean isSuccessfulResponse(String response) {
	JsonReader jsonReader = Json.createReader(new StringReader(extractJson(response)));
	JsonObject json = jsonReader.readObject();
	return json.getString("stat").equals("ok");
    }

    public static String extractJson(String flickrResponse) {
	int beginIndex = flickrResponse.indexOf("{\"");
	if (beginIndex == -1) {
	    throw new IllegalStateException("begin index not found");
	}
	int endIndex = flickrResponse.lastIndexOf(")");
	if (endIndex == -1) {
	    throw new IllegalStateException("end index not found");
	}
	return flickrResponse.substring(beginIndex, endIndex);
    }
}
