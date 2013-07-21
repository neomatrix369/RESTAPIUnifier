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
package org.neomatrix369.apiworld.HerokuAPIBehaviours;

import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import com.sun.jersey.core.util.Base64;

import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.neomatrix369.examples.heroku_api.HerokuAPI;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* ***** Heroku API *****
 * Developers Guides
 * https://devcenter.heroku.com
 * 
 * Building Scalable apps
 * http://12factor.net
 * 
 * Overview of platform API
 * http://bit.ly/heroku-api-blog
 * 
 * API quick start guide
 * http://bit.ly/heroku-api-quick
 * 
 * API reference
 * http://bit.ly/heroku-api-ref
 */

public class HerokuAPIBehaviours {	
	private static final String NO_FIELDNAME_REQUIRED = "";

	private static final String NO_ERROR_RESPONSE_FAILURE_MSG = "No error response was returned by the API server";
	private static final String EMPTY_RESPONSE_FAILURE_MSG = "An empty response was not returned by the API server";
	private static final String VALID_RESPONSE_FAILURE_MSG = "A valid response was not returned by the API server";
		
	@Before
	public void setup() throws IOException{
		HerokuAPI.initialiseSettings();
	}
	
	@Test(expected = IOException.class)
	public void should_Return_An_Error_Response_When_An_Empty_API_Is_Passed_In()
			throws FinalURLNotGeneratedException, IOException,
			BaseURLNotAssignedException, APIKeyNotAssignedException {
		String result = HerokuAPI.authenticate(NO_FIELDNAME_REQUIRED);
		assertThat(NO_ERROR_RESPONSE_FAILURE_MSG, result.isEmpty(), is(true));
	}

	@Test(expected = IOException.class)
	public void should_Return_An_Error_Response_When_An_Invalid_API_Is_Passed_In()
			throws FinalURLNotGeneratedException, IOException,
			BaseURLNotAssignedException, APIKeyNotAssignedException {
		String response = HerokuAPI.authenticate("sdsdsdsdsd");
		assertThat(EMPTY_RESPONSE_FAILURE_MSG, response.isEmpty(), is(true));
	}

	@Test
	public void should_return_a_response_when_a_valid_API_is_Passed_in()
			throws FinalURLNotGeneratedException, IOException,
			BaseURLNotAssignedException, APIKeyNotAssignedException {
		String actualResponse = HerokuAPI.authenticate();
		System.out.println("========= Authentication - API command Response ===============");
		System.out.println(actualResponse);
		System.out.println("=================================================");
		assertThat(VALID_RESPONSE_FAILURE_MSG, actualResponse.isEmpty(),is(false));
	}
	
	@Test
	public void should_return_a_response_when_the_account_command_is_invoked() throws BaseURLNotAssignedException, APIKeyNotAssignedException, FinalURLNotGeneratedException, IOException {
		String actualResponse = HerokuAPI.invokeAccount();
		System.out.println("========= Account - API command Response ===============");
		System.out.println(actualResponse);
		System.out.println("=================================================");
		assertThat("No response was returned from invoking the 'account' command from the Heroku server", actualResponse.isEmpty(), is(false));
	}
}