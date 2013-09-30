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

import com.sun.jersey.core.util.Base64;

public class Heroku {

    private static final String baseURL = "https://api.heroku.com/%s";

    private final String urlParameters;
    private final Map<String, String> requestProperties = new HashMap<String, String>();
    private final String emailaddress;
    private final String apiKey;

    protected APIReader fetchedResults;

    public Heroku(String apiKey, String emailaddress) throws IOException {
	this.apiKey = apiKey;
	this.emailaddress = emailaddress;
	String basic = "Basic " + new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII");
	this.urlParameters = "Accept=application/vnd.heroku+json; version=3&Authorization=" + basic;
	prepareParamObjectWithAuthenticationDetails();
    }

    public String invokeAccount() throws APIKeyNotAssignedException, IOException {
	UriBuilder uriBuilder = new UriBuilder(String.format(baseURL, "account"));
	uriBuilder.setApiKeyIsRequired(false);
	uriBuilder.build();
	APIReader apiReader = new APIReader(uriBuilder);
	return apiReader.executeGetUrl(requestProperties);
    }

    public String authenticate() throws IOException, APIKeyNotAssignedException {
	return authenticate(urlParameters);
    }

    private void prepareParamObjectWithAuthenticationDetails() throws UnsupportedEncodingException {
	requestProperties.put("Accept", "application/vnd.heroku+json; version=3");
	String basic = "Basic " + new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII");
	requestProperties.put("Authorization", basic);
    }

    private String authenticate(String urlParameters) throws IOException, APIKeyNotAssignedException {
	UriBuilder uriBuilder = new UriBuilder(String.format(baseURL, "apps"));
	uriBuilder.setApiKeyIsRequired(false);
	uriBuilder.build();
	APIReader apiReader = new APIReader(uriBuilder);
	return apiReader.executePostUrl(urlParameters);
    }

}