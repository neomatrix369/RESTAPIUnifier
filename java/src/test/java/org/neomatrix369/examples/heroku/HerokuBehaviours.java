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
package org.neomatrix369.examples.heroku;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.core.util.Base64;

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

public class HerokuBehaviours {

    private static final Logger logger = LoggerFactory.getLogger(HerokuBehaviours.class);
    private static final String HEROKU_SETTINGS_LOCATION = "resources/heroku.properties";

    private String emailaddress;
    private String apiKey;

    private Heroku heroku;

    private boolean isSuccessfulAppCreationResponse(String response) {
	logger.info("response: " + response);
	JsonReader jsonReader = Json.createReader(new StringReader(response));
	JsonObject json = jsonReader.readObject();

	return json.getJsonObject("owner").getString("email").equals(emailaddress);
    }

    @Before
    public void setup() throws IOException {
	apiKey = Utils.readPropertyFrom(HEROKU_SETTINGS_LOCATION, "APIKey");
	emailaddress = Utils.readPropertyFrom(HEROKU_SETTINGS_LOCATION, "emailaddress");
	heroku = new Heroku(apiKey, emailaddress);
    }

    @Ignore
    @Test
    public void should_create_an_app_with_correct_email__requires_authentication() throws Exception {

	String accept = "application/vnd.heroku+json; version=3";
	String basic = "Basic " + new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII");
	String request = "https://api.heroku.com/apps";

	APIReader apiReader = new APIReader(request);
	apiReader.setHeader("Accept", accept).setHeader("Authorization", basic);

	String response = apiReader.executePostUrl();

	assertThat(isSuccessfulAppCreationResponse(response), is(true));
    }

    @Ignore
    @Test
    public void should_return_a_response_when_the_account_command_is_invoked() throws APIKeyNotAssignedException,
	    IOException {
	String actualResponse = heroku.invokeAccount();
	assertThat("No response was returned from invoking the 'account' command from the Heroku server",
		actualResponse.isEmpty(), is(false));
    }
}