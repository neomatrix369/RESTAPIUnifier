package apiworld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import static apiworld.ResultType.*;
import static apiworld.UtilityFunctions.*;

public class APIReaderBehaviours {
	@Test
	public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		String apiKey = UtilityFunctions.readPropertyFrom(
				"resources/muzu_settings.properties", "APIKey");
		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s",
				apiKey, rtJSON);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat(result.isEmpty(), is(false));
		assertThat(isAValidJSONText(result), is(true));
	}

	@Test
	public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		String apiKey = UtilityFunctions.readPropertyFrom(
				"resources/muzu_settings.properties", "APIKey");
		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s",
				apiKey, rtXML);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat(result.isEmpty(), is(false));
		assertThat(isAValidXML(result), is(true));
	}

	@Test
	public void should_Return_Cookie_When_URL_Is_Invoked_With_A_Post_Method() throws FinalURLNotGeneratedException {
		String username = UtilityFunctions.readPropertyFrom(
				"resources/importIO_settings.properties", "username");
		String password = UtilityFunctions.readPropertyFrom(
				"resources/importIO_settings.properties", "password");
		
		String url = String.format(
				"http://api.import.io//auth/login?username=%s&password=%s",
				username, password);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL("POST", null);
		String result = apiReader.getFetchedResults();
		apiReader.displayResult();
		assertThat(result.isEmpty(), is(false));
		assertThat(isAValidJSONText(result), is(true));
	}
	
	@Test
	public void should_Return_Results_When_URL_Is_Invoked_With_An_Auth_Cookie_And_Post_Method() throws FinalURLNotGeneratedException {
		String username = UtilityFunctions.readPropertyFrom(
				"resources/importIO_settings.properties", "username");
		String password = UtilityFunctions.readPropertyFrom(
				"resources/importIO_settings.properties", "password");
		
		String url = String.format(
				"http://api.import.io//auth/login?username=%s&password=%s",
				username, password);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL("POST", null);
		Gson resultJson = new Gson();
		resultJson.toJson(apiReader.getFetchedResults());
		
		url = 
				"http://query.import.io/store/connector/d56b49bf-c668-47af-b893-9834bae3001d/_query?AUTH=c6eug4h43lidpjbmwk66vxb01q62yfkl8cxzics6gktnrt1oybecn0r3v21hnhm8yv2a9ov3r&location=london";
		apiReader = new APIReader(url);
		Map<String, String> propertiesParam = new HashMap<String, String>();
		propertiesParam.put("AUTH", "");
		propertiesParam.put("test", "london");		
		apiReader.executeURL("POST", propertiesParam);
		
		String result = apiReader.getFetchedResults();		
		System.out.format("%s", result);
		assertThat(result.isEmpty(), is(false));
		assertThat(isAValidJSONText(result), is(true));
	}	
}