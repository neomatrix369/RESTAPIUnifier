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

import com.sun.jersey.core.util.Base64;

public class HerokuAPI {
	private static final String AUTHORIZATION_FIELDNAME = "Authorization";
	private static final String FIELDNAME_APIKEY = "APIKey";
	private static final String FORMAT_TYPE_FIELD_NAME = "Accept";
	private static final String RESULT_FORMAT_TYPE = "application/vnd.heroku+json; version=3";
	private static final String EMPTY_STRING = "";
	private static final String HEROKU_SETTINGS_LOCATION = "resources/heroku_settings.properties";
	
	private static final String HTTP_POST_METHOD = "POST";
	private static final String HTTP_GET_METHOD = "GET";
	private static final String baseURL = "https://api.heroku.com/%s";
	private static final String AUTHENTICATION_COMMAND = "apps";
	private static final boolean APIKEY_NOT_REQUIRED = false;
	
	private static Map<String, String> param = new HashMap<String, String>();
	private static final String FIELDNAME_EMAIL = "emailaddress";
	private static final String ACCOUNT_COMMAND = "account";
	private static String emailaddress;
	private static String apiKey;
	private static String apiCommand;
	
	protected APIReader fetchedResults;


	public static void initialiseSettings() throws IOException {
		emailaddress = readPropertyFrom(HEROKU_SETTINGS_LOCATION, FIELDNAME_EMAIL);
		apiKey = readPropertyFrom(HEROKU_SETTINGS_LOCATION, FIELDNAME_APIKEY);
	}
	
	public static String authenticate(String changedAPIKey) throws IOException, FinalURLNotGeneratedException, BaseURLNotAssignedException, APIKeyNotAssignedException {
		apiKey = changedAPIKey;
		return authenticate();
	}
	
	public static String authenticate() throws IOException, FinalURLNotGeneratedException, BaseURLNotAssignedException, APIKeyNotAssignedException {
		prepareParamObjectWithAuthenticationDetails();
		return authenticate(param);
	}

	private static void prepareParamObjectWithAuthenticationDetails()
			throws UnsupportedEncodingException {
		param.put(FORMAT_TYPE_FIELD_NAME, RESULT_FORMAT_TYPE);		
		String httpBasicAuthFilterAuthentication = "Basic " + new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII");
		param.put(AUTHORIZATION_FIELDNAME, httpBasicAuthFilterAuthentication);
	}

	public static String authenticate(Map<String, String> param) throws FinalURLNotGeneratedException, IOException, BaseURLNotAssignedException, APIKeyNotAssignedException {
		APIBuilder apiBuilder = new APIBuilder();		
		apiCommand = AUTHENTICATION_COMMAND;
		apiBuilder.addBaseURL(String.format(baseURL, apiCommand));
		apiBuilder.setApiKeyIsRequired(APIKEY_NOT_REQUIRED);
		apiBuilder.build();
		APIReader apiReader = new APIReader(apiBuilder);		
		apiReader.executeURL(HTTP_POST_METHOD, param);
		return apiReader.getFetchedResults();
	}

	public static String invokeAccount() throws BaseURLNotAssignedException, APIKeyNotAssignedException, FinalURLNotGeneratedException, IOException {		
		APIBuilder apiBuilder = new APIBuilder();
		apiCommand = ACCOUNT_COMMAND;
		apiBuilder.addBaseURL(String.format(baseURL, apiCommand));
		apiBuilder.setApiKeyIsRequired(APIKEY_NOT_REQUIRED);
		apiBuilder.build();
		APIReader apiReader = new APIReader(apiBuilder);
		prepareParamObjectWithAuthenticationDetails();
		apiReader.executeURL(HTTP_GET_METHOD, param);
		return apiReader.getFetchedResults();
	}
}