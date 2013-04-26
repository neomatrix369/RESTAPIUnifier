package APIWorld;
import java.net.URLEncoder;
import java.util.*;
import javax.swing.text.DocumentFilter;

import org.jsoup.nodes.Document;

enum resultType {
	rtNone { public String toString() { return "";     } },
	rtJSON { public String toString() { return "json"; } },
	rtRSS  { public String toString() { return "rss";  } },
	rtXML  { public String toString() { return "xml";  } }
}

public class baseAPIClass {
	static ArrayList<String> lastHttpResult = new ArrayList<String>();
	static Document lastHttpResultXML;
	
	HashMap<String,String> paramList = new HashMap<String,String>();

	String APICommand="";
	String URLText = null;

	public static final String CONST_PARAM_START = "?";
	public static final String CONST_URL_SEPARATOR = "/";
	public static final String CONST_PARAM_SEPARATOR = "&";
	
	String buildURL(String baseURL, String aPICommand, String[] params,
			String[] arrayURLParamCodes, String[] arrayURLParamDefaultValues,
			HashMap<String, String> paramList) {
		params = initParamList(params, arrayURLParamDefaultValues);
		params = buildParamList(params, arrayURLParamCodes);
		String URLText = buildURL(baseURL, APICommand);
		URLText = addParamsToURL(URLText, paramList);
		return URLText;
	}

	private String addParamsToURL(String inURLText, HashMap<String, String> inParamList) {
		inURLText = buildURL(inURLText, CONST_PARAM_START);
		for(Map.Entry<String, String> eachParam: inParamList.entrySet()) {
			String eachParamToken = eachParam.getKey()+"="+eachParam.getValue();
			inURLText = buildURL(inURLText, eachParamToken);
			inURLText = buildURL(inURLText, CONST_PARAM_SEPARATOR);
		}

		if (inURLText.charAt(inURLText.length()-1) == CONST_PARAM_SEPARATOR.charAt(0))
			inURLText = inURLText.substring(0, inURLText.length()-1);
	
		return inURLText;
	}

	private String[] buildParamList(String[] inParams, String[] inParamCodes) {
		int paramCtr = 0;
		for (String eachParam: inParamCodes) {
			//System.out.println(paramCtr);
			if ((inParams.length > 0) && (inParams.length > paramCtr)) {
				
				if (inParams[paramCtr] != null)
					paramList.put(eachParam, URLEncoder.encode(inParams[paramCtr]));
			}
			paramCtr++;
		}
		return inParams;
	}

	private String[] initParamList(String[] params, String[] defaultValues) {
		if ((params != null) && (defaultValues != null)) {
			int paramCtr = 0;
			for(String eachParam: params) {
				if ((params[paramCtr] == null) || params[paramCtr].isEmpty()) {
					//if (defaultValues[paramCtr] != null) {
						params[paramCtr] = defaultValues[paramCtr];
					//}
					paramCtr++;
				}
			}
		}
		return params;	
	}
	
	private String buildURL(String someURL, String suffix) {
		return someURL + suffix;
	}
	
	public void displayHttpReqResult(resultType format) {
		if (URLText == null) {
			System.out.println(">>> No http connections made.");			
			return;
		}
		
		switch(format) {
			case rtNone:
			default: { 
					if ((lastHttpResult == null) || (lastHttpResult.size() == 0)) {
						System.out.println(">>> No results returned.");			
						return;
					}
					break;
				}
			case rtXML: {
				if (lastHttpResultXML == null) {
					System.out.println(">>> No results returned.");			
					return;
				}
				break;				
				}
		}
			
		System.out.println(">>> Displaying last retrieved results from" + URLText);
		
		switch(format) {
			case rtNone:
			default: { 
				for (String eachLine: lastHttpResult) 
					System.out.println(eachLine);
				break;
			}
			case rtXML: {
				System.out.println(lastHttpResultXML.toString());
				break;				
			}		
		}
	}
}