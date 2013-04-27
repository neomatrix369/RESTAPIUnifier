package apiworld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import static apiworld.ResultType.*;
import static apiworld.UtilityFunctions.*;

public class APIReaderBehaviours {
	@Test
	public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		Properties prop = new Properties();
		prop.load(new FileReader(new File("resources/muzu_settings.properties")));
		String apiKey = prop.getProperty("APIKey");

		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s", apiKey, rtJSON);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat(result.isEmpty(), is(false));	
		assertThat(validateJSON(result), is(true));
	}

	@Test
	public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		Properties prop = new Properties();
		prop.load(new FileReader(new File("resources/muzu_settings.properties")));
		String apiKey = prop.getProperty("APIKey");

		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s", apiKey, rtXML);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat(result.isEmpty(), is(false));
		assertThat(validateXML(result), is(true));
	}
}