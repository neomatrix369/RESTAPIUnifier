package APIWorld;
import java.io.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

public class APIReader extends baseAPIClass {
	
	APIReader() {
		super();
	}
		
	APIReader(String websiteBaseURL, String apiVarIdentifier, String apiKey) {
		lastHttpResult.clear();
		
		URLText = websiteBaseURL.replace(apiVarIdentifier, apiKey);
		sendURL(URLText);
	}
	
	APIReader (String urlText) {
		lastHttpResult.clear();
		sendURL(urlText);
	}

	@SuppressWarnings("deprecation")
	void sendURL(String urlText) {
		try
		{
			
			URL webSite = new URL(urlText);
			try {
				
				// http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html
				URLConnection urlConnection = webSite.openConnection(); System.out.println(">>> Connecting to URL: <" + URLText + ">, this may take a moment.");
				InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
				
				BufferedReader httpResult = null; 
				try {
					httpResult = new BufferedReader(isr);
					System.out.println(">>> Reading results returned, this may take a moment...");
					
					String inputLine;
					while((inputLine = httpResult.readLine()) != null) {
						lastHttpResult.add(inputLine);
					}
	
					lastHttpResultXML = StringToXML(lastHttpResult.toString()); 
					System.out.println(">>> Reading completed...");
				} finally {
					if (httpResult != null) httpResult.close();
					System.out.println(">>> Connection closed!");
				}
			} catch (IOException ioe) {
				System.out.println(">>> Error connecting to the website: " + URLText);
				ioe.printStackTrace();				
			}
		} catch (MalformedURLException me) {
			System.out.println(">>> Input URL String: " + URLText);
			me.printStackTrace();
		}
	}
	
	private Document StringToXML(String string) {
		string = string.substring(1, string.length()-1);
		string = string.replaceAll(">,", ">");
		Document doc = Jsoup.parse(string);
		return doc ;
	}
}