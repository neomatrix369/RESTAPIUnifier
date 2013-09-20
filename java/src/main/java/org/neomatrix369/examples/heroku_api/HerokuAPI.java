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
package org.neomatrix369.examples.heroku_api;

import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.neomatrix369.apiworld.APIBuilder;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.neomatrix369.apiworld.util.Keys;

import com.sun.jersey.core.util.Base64;

/**
 * API provider URL: https://devcenter.heroku.com
 * 
 * Required settings file to run this example:
 * resources/heroku_settings.properties
 * 
 * containing 
 * APIKey=[xxxxx]
 * emailaddres=[xxx@yyyy.com]
 * 
 * [xxxxx] = is APIKey needed to get authentication from heroku.com to be
 * able to make any API calls.
 * 
 * [xxx@yyyy.com] = email address attached to the account
 * 
 */


public class HerokuAPI {
    private static final String baseURL = "https://api.heroku.com/%s";
    private static final boolean APIKEY_NOT_REQUIRED = false;

    private static Map<String, String> param = new HashMap<String, String>();
    private static String emailaddress;
    private static String apiKey;
    private static String apiCommand;

    protected APIReader fetchedResults;

    public static void initialiseSettings() throws IOException {
	emailaddress = readPropertyFrom(Keys.INSTANCE.getKey("HEROKU_SETTINGS_LOCATION"),
		Keys.INSTANCE.getKey("FIELDNAME_EMAIL"));
	apiKey = readPropertyFrom(Keys.INSTANCE.getKey("HEROKU_SETTINGS_LOCATION"),
		Keys.INSTANCE.getKey("FIELDNAME_APIKEY"));
    }

    public static String authenticate(String changedAPIKey) throws IOException, FinalURLNotGeneratedException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	apiKey = changedAPIKey;
	return authenticate();
    }

    public static String authenticate() throws IOException, FinalURLNotGeneratedException, BaseURLNotAssignedException,
	    APIKeyNotAssignedException {
	prepareParamObjectWithAuthenticationDetails();
	return authenticate(param);
    }

    private static void prepareParamObjectWithAuthenticationDetails() throws UnsupportedEncodingException {
	param.put(Keys.INSTANCE.getKey("FORMAT_TYPE_FIELD_NAME"), Keys.INSTANCE.getKey("RESULT_FORMAT_TYPE"));
	String httpBasicAuthFilterAuthentication = "Basic "
		+ new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII");
	param.put(Keys.INSTANCE.getKey("AUTHORIZATION_FIELDNAME"), httpBasicAuthFilterAuthentication);
    }

    public static String authenticate(Map<String, String> param) throws FinalURLNotGeneratedException, IOException,
	    BaseURLNotAssignedException, APIKeyNotAssignedException {
	apiCommand = Keys.INSTANCE.getKey("AUTHENTICATION_COMMAND");
	APIBuilder apiBuilder = new APIBuilder(String.format(baseURL, apiCommand))
		.setApiKeyIsRequired(APIKEY_NOT_REQUIRED);
	apiBuilder.build();
	APIReader apiReader = new APIReader(apiBuilder);
	apiReader.executeURL(Keys.INSTANCE.getKey("HTTP_POST_METHOD"), param);
	return apiReader.getFetchedResults();
    }

    public static String invokeAccount() throws BaseURLNotAssignedException, APIKeyNotAssignedException,
	    FinalURLNotGeneratedException, IOException {
	apiCommand = Keys.INSTANCE.getKey("ACCOUNT_COMMAND");
	APIBuilder apiBuilder = new APIBuilder(String.format(baseURL, apiCommand))
		.setApiKeyIsRequired(APIKEY_NOT_REQUIRED);
	apiBuilder.build();
	APIReader apiReader = new APIReader(apiBuilder);
	prepareParamObjectWithAuthenticationDetails();
	apiReader.executeURL(Keys.INSTANCE.getKey("HTTP_GET_METHOD"), param);
	return apiReader.getFetchedResults();
    }
}