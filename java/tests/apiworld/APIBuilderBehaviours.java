package apiworld;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class APIBuilderBehaviours {
	private static final String MUZUID_KEY = "muzuid";
	private static final String MUZUID_VALUE = "[MUZU_ID]";
	private static final String MUZU_BASE_URL = "http://www.muzu.tv/api/";
	private static final String MUZU_URL_WITH_BROWSE_AND_MUZU_ID = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]";
	private static final String MUZU_BASE_URL_WITH_BROWSE_COMMAND = "http://www.muzu.tv/api/browse?";
	private static final String API_BROWSE_COMMAND = "browse";
	
	private String baseURL;
	private APIBuilder apiBuilder;
	
	@Before
	public void setup() {
		baseURL = MUZU_BASE_URL;
		apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
	}

	@Test (expected=BaseURLNotAssignedException.class)
	public void shouldReturnExceptionWhenNoURLIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseURL = "";
		apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.build();
	}
	@Test (expected=APIKeyNotAssignedException.class)
	public void shouldReturnExceptionWhenNoAPIKeyIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.build();
	}
		
	@Test
	public void shouldReturnURLWithCommandWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.build();
		apiBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);	
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = MUZU_BASE_URL_WITH_BROWSE_COMMAND;
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithAPIKeyWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithAPIKeyAndParamsWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addCommand(API_BROWSE_COMMAND);
		apiBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.addAURLParameter("key1", "value1");
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1";
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithAPIKeyAndSkipParamsWithNulls() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addCommand(API_BROWSE_COMMAND);
		apiBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.addAURLParameter("key1", null);
		apiBuilder.addAURLParameter("key2", "value2");
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key2=value2";
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithEncodedParam() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addCommand(API_BROWSE_COMMAND);
		apiBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.addAURLParameter("key", UtilityFunctions.encodeToken("string with space"));
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key=string+with+space";
		assertThat(actual, is(expected));		
	}
}