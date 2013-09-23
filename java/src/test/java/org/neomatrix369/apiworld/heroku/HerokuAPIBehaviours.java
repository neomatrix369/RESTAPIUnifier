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
package org.neomatrix369.apiworld.heroku;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.examples.heroku.Heroku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * 
 */

/**
 * API provider URL: https://devcenter.heroku.com
 * 
 * Required settings file to run this example:
 * resources/heroku_settings.properties
 * 
 * containing APIKey=[xxxxx] emailaddres=[xxx@yyyy.com]
 * 
 * [xxxxx] = is APIKey needed to get authentication from heroku.com to be able
 * to make any API calls.
 * 
 * [xxx@yyyy.com] = email address attached to the account
 * 
 */
public class HerokuAPIBehaviours {

    private static final Logger LOGGER = LoggerFactory.getLogger(HerokuAPIBehaviours.class);

    private static final String NO_FIELDNAME_REQUIRED = "";

    private static final String NO_ERROR_RESPONSE_FAILURE_MSG = "No error response was returned by the API server";
    private static final String EMPTY_RESPONSE_FAILURE_MSG = "An empty response was not returned by the API server";
    private static final String VALID_RESPONSE_FAILURE_MSG = "A valid response was not returned by the API server";

    @Before
    public void setup() throws IOException {
	Heroku.initialiseSettings();
    }

    @Test(expected = IOException.class)
    public void should_Return_An_Error_Response_When_An_Empty_API_Is_Passed_In() throws IOException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	String result = Heroku.authenticate(NO_FIELDNAME_REQUIRED);
	assertThat(NO_ERROR_RESPONSE_FAILURE_MSG, result.isEmpty(), is(true));
    }

    @Test(expected = IOException.class)
    public void should_Return_An_Error_Response_When_An_Invalid_API_Is_Passed_In() throws IOException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	String response = Heroku.authenticate("sdsdsdsdsd");
	assertThat(EMPTY_RESPONSE_FAILURE_MSG, response.isEmpty(), is(true));
    }

    // TODO: fix test, by fixing Heroku API - Authentication
    @Test
    @Ignore("Heroku API for Authentication needs to be debugged")
    public void should_return_a_response_when_a_valid_API_is_Passed_in() throws IOException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	String actualResponse = Heroku.authenticate();
	LOGGER.info("========= Authentication - API command Response ===============");
	LOGGER.info(actualResponse);
	LOGGER.info("=================================================");
	assertThat(VALID_RESPONSE_FAILURE_MSG, actualResponse.isEmpty(), is(false));
    }

    @Test
    public void should_return_a_response_when_the_account_command_is_invoked() throws BaseURLNotAssignedException,
	    APIKeyNotAssignedException, IOException {
	String actualResponse = Heroku.invokeAccount();
	LOGGER.info("========= Account - API command Response ===============");
	LOGGER.info(actualResponse);
	LOGGER.info("=================================================");
	assertThat("No response was returned from invoking the 'account' command from the Heroku server",
		actualResponse.isEmpty(), is(false));
    }
}