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

import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.neomatrix369.examples.heroku_api.HerokuAPI;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;
import java.lang.Exception;

public class HerokuAPIBehaviours {
	private static final String NO_ERROR_RESPONSE_FAILURE_MSG = "No error response was returned by the API server";
	private static final String EMPTY_RESPONSE_FAILURE_MSG = "An empty response was not returned by the API server";
	private static final String VALID_RESPONSE_FAILURE_MSG = "A valid response was not returned by the API server";
	
	@Test(expected=Exception.class)
	public void should_Return_An_Error_Response_When_An_Empty_API_Is_Passed_In() throws FinalURLNotGeneratedException, IOException {
		String apiKey = "";
		HerokuAPI.OAuth(apiKey);
		assertThat(NO_ERROR_RESPONSE_FAILURE_MSG, true, is(false));
	}

	@Test
	public void should_Return_An_Error_Response_When_An_Invalid_API_Is_Passed_In() throws FinalURLNotGeneratedException, IOException {
		String apiKey = "sdsdsdsdsd";
		String response = HerokuAPI.OAuth(apiKey);
		assertThat(EMPTY_RESPONSE_FAILURE_MSG, response.isEmpty(), is(true));
	}
	
	@Test
	public void should_return_a_response_when_a_valid_API_is_Passed_in() throws FinalURLNotGeneratedException, IOException {
		String expectedResponse = "{auth:OK}";
		String apiKey = readPropertyFrom(
                "resources/heroku_settings.properties", "apiKey");
		String actualResponse = HerokuAPI.OAuth(apiKey);
		assertThat(VALID_RESPONSE_FAILURE_MSG, actualResponse, is(expectedResponse));
	}
}