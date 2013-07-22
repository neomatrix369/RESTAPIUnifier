/**
 *
 *  Copyright (c) 2013. All rights reserved.
 *
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 2 only, as
 *  published by the Free Software Foundation.  Oracle designates this
 *  particular file as subject to the "Classpath" exception as provided
 *  by Oracle in the LICENSE file that accompanied this code.
 *
 *  This code is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  version 2 for more details (a copy is included in the LICENSE file that
 *  accompanied this code).
 *
 *  You should have received a copy of the GNU General Public License version
 *  2 along with this work; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.neomatrix369.apiworld;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.neomatrix369.apiworld.util.UtilityFunctions.*;

import com.google.gson.Gson;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.neomatrix369.apiworld.ResultType.*;

public class APIReaderBehaviours {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(APIReaderBehaviours.class);
    
	private static final String INVALID_JSON_RETURNED = "Invalid JSON returned.";
	private static final String NO_RESULTS_RETURNED = "No results returned.";

	@Test
	public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		String apiKey = readPropertyFrom(
				"resources/muzu_settings.properties", "APIKey");
		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s",
				apiKey, JSON);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat(NO_RESULTS_RETURNED, result.isEmpty(), is(false));
		
		LOGGER.info(result);
		
		assertThat(INVALID_JSON_RETURNED, isAValidJSONText(result), is(true));
	}

	@Test 
	public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In()
			throws FileNotFoundException, IOException,
			FinalURLNotGeneratedException {
		String apiKey = readPropertyFrom(
				"resources/muzu_settings.properties", "APIKey");
		String url = String.format(
				"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=%s",
				apiKey, XML);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL();
		String result = apiReader.getFetchedResults();
		assertThat("Empty result returned.", result.isEmpty(), is(false));
		assertThat(INVALID_JSON_RETURNED, isAValidXML(result), is(true));
	}

	@Test
	public void should_Return_Cookie_When_URL_Is_Invoked_With_A_Post_Method() throws FinalURLNotGeneratedException, IOException {
		String username = readPropertyFrom(
				"resources/importIO_settings.properties", "username");
		String password = readPropertyFrom(
				"resources/importIO_settings.properties", "password");
		
		String url = String.format(
				"http://api.import.io//auth/login?username=%s&password=%s",
				username, password);
		APIReader apiReader = new APIReader(url);
		apiReader.executeURL("POST", null);
		String result = apiReader.getFetchedResults();
		apiReader.displayResult();
		assertThat("Empty result returned.", result.isEmpty(), is(false));
		assertThat(INVALID_JSON_RETURNED, isAValidJSONText(result), is(true));
	}
	
	@Test @Ignore
	public void should_Return_Results_When_URL_Is_Invoked_With_An_Auth_Cookie_And_Post_Method() throws FinalURLNotGeneratedException, IOException {
		String username = readPropertyFrom(
				"resources/importIO_settings.properties", "username");
		String password = readPropertyFrom(
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
		
		LOGGER.info(result);
		
		assertThat(NO_RESULTS_RETURNED, result.isEmpty(), is(false));
		assertThat(INVALID_JSON_RETURNED, isAValidJSONText(result), is(true));
	}	
}