package apiworld;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import apiworld.APIBuilder;

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


	@Test 
    (expected=BaseURLNotAssignedException.class)
	public void should_Return_Exception_When_No_URL_Is_Supplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		baseURL = "";
		apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.build();
	}


	@Test (expected=APIKeyNotAssignedException.class)
	public void should_Return_Exception_When_No_API_Key_Is_Supplied() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.build();
	}
	
	@Test 
	public void should_Not_Return_Exception_When_No_API_Key_Required_Is_Set() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.setNoAPIKeyRequired();
		apiBuilder.build();
	}
	
	@Test
	public void should_Return_URL_With_Command_When_Passed_In() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.setCommand(API_BROWSE_COMMAND);
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void should_Return_URL_With_API_Key_And_Params_When_Passed_In() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.setCommand(API_BROWSE_COMMAND);
		apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.addAURLParameter("key1", "value1");
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1";
		assertThat(actual, is(expected));		
	}

    @Test
    public void should_Return_URL_With_API_Key_And_All_Valid_Params() throws BaseURLNotAssignedException, APIKeyNotAssignedException    
    {
        apiBuilder.setCommand(API_BROWSE_COMMAND);
        apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
        apiBuilder.addAURLParameter("key1", "value1");
        apiBuilder.addAURLParameter("key2", "value2");
        apiBuilder.build();
        String actual = apiBuilder.getAPIReadyURL();
        String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1&key2=value2";
        assertThat(expected, is(actual));
    }

	
	@Test
	public void should_Return_URL_With_API_Key_And_Skip_Params_With_Null_Keys() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.setCommand(API_BROWSE_COMMAND);
		apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);

		apiBuilder.addAURLParameter(null, "value1");
		apiBuilder.addAURLParameter("key2", "value2");
		apiBuilder.addAURLParameter("key3", "value3");
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key2=value2&key3=value3";
		assertThat(actual, is(expected));		
	}

	@Test
	public void should_Return_URL_With_API_Key_And_Skip_Params_With_Null_Values() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.setCommand(API_BROWSE_COMMAND);
		apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);

		apiBuilder.addAURLParameter("key1", "value1");
		apiBuilder.addAURLParameter("key2", null);
		apiBuilder.addAURLParameter("key3", "value3");
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1&key3=value3";
		assertThat(actual, is(expected));		
	}
	
	@Test
	public void should_Return_URL_With_Encoded_Param() throws BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiBuilder.setCommand(API_BROWSE_COMMAND);
		apiBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
		apiBuilder.addAURLParameter("key", UtilityFunctions.encodeToken("string with space"));
		apiBuilder.build();
		String actual = apiBuilder.getAPIReadyURL(); 
		String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key=string+with+space";
		assertThat(actual, is(expected));		
	}
}