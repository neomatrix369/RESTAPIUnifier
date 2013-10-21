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

import com.sun.jersey.core.util.Base64;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Heroku {

    private static final String baseURL = "https://api.heroku.com/%s";

    private final String urlParameters;
    private final Map<String, String> requestProperties = new HashMap<String, String>();

    protected APIReader fetchedResults;//FIXME this is not used, do we want to keep it?
    private String apiKey;
    private String emailaddress;

    public Heroku(String apiKey, String emailaddress) throws IOException {
        this.apiKey = apiKey;
        this.emailaddress = emailaddress;
        this.urlParameters = addCommonHeaders();
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

    private String authenticate(String urlParameters) throws IOException, APIKeyNotAssignedException {
        UriBuilder uriBuilder = new UriBuilder(String.format(baseURL, "apps"));
        uriBuilder.setApiKeyIsRequired(false);
        uriBuilder.build();
        APIReader apiReader = new APIReader(uriBuilder);
        return apiReader.executePostUrl(urlParameters);
    }

    private String addCommonHeaders() {
        return acceptHeader() + "&" + authorizationHeader();
    }

    private void prepareParamObjectWithAuthenticationDetails() {
        requestProperties.put("Accept", acceptHeaderValue());
        requestProperties.put("Authorization", authorizationHeaderValue());
    }

    private String acceptHeader() {
        return "Accept=" + acceptHeaderValue();
    }

    private String authorizationHeader() {
        return "Authorization=" + authorizationHeaderValue();
    }

    private String authorizationHeaderValue() {
        try {
            return String.format("Basic %s", new String(Base64.encode(emailaddress + ":" + apiKey), "ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Could not encode authorization header value from email address & api key");
        }
    }

    private String acceptHeaderValue() {
        return "application/vnd.heroku+json; version=3";
    }

}