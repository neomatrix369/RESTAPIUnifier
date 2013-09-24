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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.Keys;

import com.sun.jersey.core.util.Base64;

public class Heroku {
    private static final String BASIC = "Basic";
    private static final String SPACE = " ";
    private static final String COLON = ":";
    private static final String ASCII = "ASCII";
    private static final String KEY_HTTP_GET_METHOD = Keys.INSTANCE.getKey("HTTP_GET_METHOD");
    private static final String KEY_ACCOUNT_COMMAND = Keys.INSTANCE.getKey("ACCOUNT_COMMAND");
    private static final String KEY_HTTP_POST_METHOD = Keys.INSTANCE.getKey("HTTP_POST_METHOD");
    private static final String KEY_AUTHENTICATION_COMMAND = Keys.INSTANCE.getKey("AUTHENTICATION_COMMAND");
    private static final String KEY_AUTHORIZATION_FIELDNAME = Keys.INSTANCE.getKey("AUTHORIZATION_FIELDNAME");
    private static final String KEY_RESULT_FORMAT_TYPE = Keys.INSTANCE.getKey("RESULT_FORMAT_TYPE");
    private static final String KEY_FORMAT_TYPE_FIELD_NAME = Keys.INSTANCE.getKey("FORMAT_TYPE_FIELD_NAME");

    private static final String baseURL = "https://api.heroku.com/%s";

    private Map<String, String> param = new HashMap<String, String>();
    private String emailaddress;
    private String apiKey;
    private String apiCommand;

    protected APIReader fetchedResults;

    public Heroku(String apiKey, String emailaddress) {
	this.apiKey = apiKey;
	this.emailaddress = emailaddress;
    }

    public String authenticate() throws IOException, BaseURLNotAssignedException, APIKeyNotAssignedException {
	prepareParamObjectWithAuthenticationDetails();
	return authenticate(param);
    }

    private void prepareParamObjectWithAuthenticationDetails() throws UnsupportedEncodingException {
	param.put(KEY_FORMAT_TYPE_FIELD_NAME, KEY_RESULT_FORMAT_TYPE);
	String httpBasicAuthFilterAuthentication = BASIC + SPACE
		+ new String(Base64.encode(emailaddress + COLON + apiKey), ASCII);
	param.put(KEY_AUTHORIZATION_FIELDNAME, httpBasicAuthFilterAuthentication);
    }

    public String authenticate(Map<String, String> param) throws IOException, BaseURLNotAssignedException,
	    APIKeyNotAssignedException {
	apiCommand = KEY_AUTHENTICATION_COMMAND;
	UriBuilder apiBuilder = new UriBuilder(String.format(baseURL, apiCommand));
	apiBuilder.setApiKeyIsRequired(false);
	apiBuilder.build();
	APIReader apiReader = new APIReader(apiBuilder);
	apiReader.executeUrl(KEY_HTTP_POST_METHOD, param);
	return apiReader.getFetchedResults();
    }

    public String invokeAccount() throws BaseURLNotAssignedException, APIKeyNotAssignedException, IOException {
	apiCommand = KEY_ACCOUNT_COMMAND;
	UriBuilder apiBuilder = new UriBuilder(String.format(baseURL, apiCommand));
	apiBuilder.setApiKeyIsRequired(false);
	apiBuilder.build();
	APIReader apiReader = new APIReader(apiBuilder);
	prepareParamObjectWithAuthenticationDetails();
	apiReader.executeUrl(KEY_HTTP_GET_METHOD, param);
	return apiReader.getFetchedResults();
    }
}