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
package org.neomatrix369.examples.yql;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yql {

    private static final Logger logger = LoggerFactory.getLogger(Yql.class);

    private String baseURL = "http://query.yahooapis.com/v1/";
    private APIReader apiReader;
    private Map<String, String> parameters = new HashMap<String, String>();

    public Yql withStatement(String statement) {
	this.parameters.put("q", statement);
	return this;
    }

    public Yql withFormat(String format) {
	this.parameters.put("format", format);
	return this;
    }

    public Yql buildUrl() {
	buildAPIReadyToExecute(parameters);
	return this;
    }

    public String executeUrl() throws IOException {
	return apiReader.executeGetUrl();
    }

    protected void buildAPIReadyToExecute(Map<String, String> parameters) {

	UriBuilder uriBuilder = new UriBuilder(baseURL).setCommand("public/yql").setNoAPIKeyRequired();

	for (Map.Entry<String, String> param : parameters.entrySet()) {
	    uriBuilder.addUrlParameter(param.getKey(), Utils.urlEncode(param.getValue()));
	}

	try {
	    uriBuilder.build();
	    this.apiReader = new APIReader(uriBuilder);
	} catch (APIKeyNotAssignedException e) {
	    logger.error(e.getMessage());
	    throw new IllegalStateException("No API Key assigned");
	}
    }

}