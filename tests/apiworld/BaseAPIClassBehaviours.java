package apiworld;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class BaseAPIClassBehaviours {
	private static final String MUZUID_VALUE = "[MUZU_ID]";
	private static final String MUZUID_KEY = "muzuid";
	private static final String MUZU_BASE_URL = "http://www.muzu.tv/api/";
	private static final String MUZU_URL_WITH_BROWSE_AND_MUZU_ID = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]";
	private static final String MUZU_BASE_URL_WITH_BROWSE_COMMAND = "http://www.muzu.tv/api/browse?";
	private static final String API_BROWSE_COMMAND = "browse";
	
	private String baseURL;
	private BaseAPIClass baseAPIClass;
	
	@Before
	public void setup() {
		baseURL = MUZU_BASE_URL;
		baseAPIClass = new BaseAPIClass();
		baseAPIClass.addBaseURL(baseURL);
	}
	
	@Test(expected=APIKeyNotAssignedException.class)
	public void shouldReturnURLOnlyWhenNoParametersArePassed() throws APIKeyNotAssignedException, BaseURLNotAssignedException {
		baseAPIClass.build();
		String actual = baseAPIClass.getAPIReadyURL(); 
		String expected = MUZU_BASE_URL;
		assertThat(actual, is(expected));
	}
	
	@Test(expected=APIKeyNotAssignedException.class)
	public void shouldReturnURLWithCommandWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseAPIClass.addCommand(API_BROWSE_COMMAND);
		baseAPIClass.build();
		String actual = baseAPIClass.getAPIReadyURL(); 
		String expected = MUZU_BASE_URL_WITH_BROWSE_COMMAND;
		assertThat(actual, is(expected));		
	}
		
	@Test
	public void shouldReturnURLWithAPIKeyWhenPassedIn() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseAPIClass.addCommand(API_BROWSE_COMMAND);
		baseAPIClass.addAPIKey(MUZUID_KEY, MUZUID_VALUE);
		baseAPIClass.build();
		String actual = baseAPIClass.getAPIReadyURL(); 
		String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
		assertThat(actual, is(expected));		
	}
	
	@Test (expected=BaseURLNotAssignedException.class)
	public void shouldReturnExceptionWhenNoURLIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseURL = "";
		baseAPIClass = new BaseAPIClass();
		baseAPIClass.addBaseURL(baseURL);
		baseAPIClass.build();
	}

	@Test (expected=APIKeyNotAssignedException.class)
	public void shouldReturnExceptionWhenNoAPIKeyIsSupplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseAPIClass.addBaseURL(baseURL);
		baseAPIClass.build();
	}
}