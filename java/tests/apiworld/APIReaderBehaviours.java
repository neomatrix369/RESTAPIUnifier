package apiworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;

public class APIReaderBehaviours {
	@Test
	public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		Properties prop = new Properties();
		prop.load(new FileReader(new File("resources/muzu_settings.properties")));
		String apiKey = prop.getProperty("APIKey");

		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop", apiKey);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults(ResultType.rtJSON);
		//String expected = loadTestDataFromFile("resources/expectedMuzuBrowseCommandResults.txt");
		assertThat(result.isEmpty(), is(false));
		//assertThat(expected, is(result));
	}

	private String loadTestDataFromFile(String filename) {
		String fileContent = "";
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filename));

			while ((sCurrentLine = br.readLine()) != null) {
				fileContent = fileContent + sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return fileContent;
	}
}
