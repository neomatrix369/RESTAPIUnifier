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
package org.neomatrix369.examples.flickr;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.Keys;
import org.neomatrix369.apiworld.util.UtilityFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//http://www.flickr.com/services/api/response.json.html

// EXAMPLE for successful flick return value
// jsonFlickrApi({"method":{"_content":"flickr.test.echo"},
// "format":{"_content":"json"},
// "api_key":{"_content":"4cccadecce65a39cfcfee90b1c01c6a4"}, "stat":"ok"})

public class BaseFlickr {

    private static final String KEY_FLICKR_API_PARAM = Keys.INSTANCE.getKey("FLICKR_API_PARAM");

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFlickr.class);

    private String baseURL = "http://api.flickr.com/services/rest";
    protected APIReader fetchedResults;

    protected APIReader buildAPIReadyToExecute(String apiKey, String apiCommand, String paramStart,
	    String[] arrayURLParamCodes, String... params) {
	UriBuilder apiBuilder = new UriBuilder(baseURL).setCommand(apiCommand).setParamStart(paramStart)
		.setAPIKey(KEY_FLICKR_API_PARAM, apiKey);
	int paramCtr = 0;
	for (String eachValue : params) {
	    apiBuilder.addAURLParameter(arrayURLParamCodes[paramCtr++], UtilityFunctions.encodeToken(eachValue));
	}

	try {
	    apiBuilder.build();
	    return new APIReader(apiBuilder);
	} catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
	    LOGGER.error(e.getMessage());
	    return new APIReader(baseURL);
	}

    }

    public String getFetchedResults() {
	if (fetchedResults != null) {
	    return fetchedResults.getFetchedResults();
	}
	return "";
    }

    public boolean isSuccess() {
	return isSuccessfulResponse(getFetchedResults());
    }

    private boolean isSuccessfulResponse(String response) {
	JsonReader jsonReader = Json.createReader(new StringReader(extractJson(response)));
	JsonObject json = jsonReader.readObject();
	return json.getString("stat").equals("ok");
    }

    public String extractJson(String flickrResponse) {
	int beginIndex = flickrResponse.indexOf("{\"");
	if (beginIndex == -1) {
	    throw new IllegalStateException("begin index not found");
	}
	int endIndex = flickrResponse.lastIndexOf(")");
	if (endIndex == -1) {
	    throw new IllegalStateException("end index not found");
	}
	return flickrResponse.substring(beginIndex, endIndex);
    }
}