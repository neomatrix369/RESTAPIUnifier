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
import static org.junit.Assert.assertThat;
import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.Keys;

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

    private static final String VALID_RESPONSE_FAILURE_MSG = "A valid response was not returned by the API server";
    private static final String KEY_FIELDNAME_APIKEY = Keys.INSTANCE.getKey("FIELDNAME_APIKEY");
    private static final String KEY_FIELDNAME_EMAIL = Keys.INSTANCE.getKey("FIELDNAME_EMAIL");
    private static final String HEROKU_SETTINGS_LOCATION = Keys.INSTANCE.getKey("HEROKU_SETTINGS_LOCATION");

    private String emailaddress;
    private String apiKey;

    private Heroku heroku;

    @Before
    public void setup() throws IOException {
	apiKey = readPropertyFrom(HEROKU_SETTINGS_LOCATION, KEY_FIELDNAME_APIKEY);
	emailaddress = readPropertyFrom(HEROKU_SETTINGS_LOCATION, KEY_FIELDNAME_EMAIL);
	heroku = new Heroku(apiKey, emailaddress);
    }

    @Test
    @Ignore("Heroku API for Authentication needs to be debugged")
    public void should_return_a_response_when_a_valid_API_is_Passed_in() throws IOException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	String actualResponse = heroku.authenticate();
	assertThat(VALID_RESPONSE_FAILURE_MSG, actualResponse.isEmpty(), is(false));
    }

    @Test
    public void should_return_a_response_when_the_account_command_is_invoked() throws BaseURLNotAssignedException,
	    APIKeyNotAssignedException, IOException {
	String actualResponse = heroku.invokeAccount();
	assertThat("No response was returned from invoking the 'account' command from the Heroku server",
		actualResponse.isEmpty(), is(false));
    }
}