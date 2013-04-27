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
	private APIBuilder aPIBuilder;
	
	@Before
	public void setup() {
		baseURL = MUZU_BASE_URL;
		aPIBuilder = new APIBuilder();
		aPIBuilder.addBaseURL(baseURL);
	}
	
	@Test(expected=APIKeyNotAssignedException.class)
	public void shouldReturnURLOnlyWhenNoParametersArePassed() throws APIKeyNotAssignedException, BaseURLNotAssignedException {
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = MUZU_BASE_URL;
		assertThat(actual, is(expected));
	}
	
	@Test(expected=APIKeyNotAssignedException.class)
	public void shouldReturnURLWithCommandWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addCommand(API_BROWSE_COMMAND);
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = MUZU_BASE_URL_WITH_BROWSE_COMMAND;
		assertThat(actual, is(expected));		
	}
		
	@Test
	public void shouldReturnURLWithAPIKeyWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addCommand(API_BROWSE_COMMAND);
		aPIBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
		assertThat(actual, is(expected));		
	}
	
	@Test (expected=BaseURLNotAssignedException.class)
	public void shouldReturnExceptionWhenNoURLIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseURL = "";
		aPIBuilder = new APIBuilder();
		aPIBuilder.addBaseURL(baseURL);
		aPIBuilder.build();
	}

	@Test (expected=APIKeyNotAssignedException.class)
	public void shouldReturnExceptionWhenNoAPIKeyIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addBaseURL(baseURL);
		aPIBuilder.build();
	}
	
	@Test
	public void shouldReturnURLWithAPIKeyAndParamsWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addCommand(API_BROWSE_COMMAND);
		aPIBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		aPIBuilder.addAURLParameter("key1", "value1");
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1";
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithAPIKeyAndSkipParamsWithNulls() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addCommand(API_BROWSE_COMMAND);
		aPIBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		aPIBuilder.addAURLParameter("key1", null);
		aPIBuilder.addAURLParameter("key2", "value2");
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key2=value2";
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void shouldReturnURLWithEncodedParam() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		aPIBuilder.addCommand(API_BROWSE_COMMAND);
		aPIBuilder.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		aPIBuilder.addAURLParameter("key", UtilityFunctions.encodeToken("string with space"));
		aPIBuilder.build();
		String actual = aPIBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key=string+with+space";
		assertThat(actual, is(expected));		
	}
}