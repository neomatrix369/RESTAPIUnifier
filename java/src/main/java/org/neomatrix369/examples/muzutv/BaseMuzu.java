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
package org.neomatrix369.examples.muzutv;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.util.Utils;
import org.neomatrix369.examples.muzutv.data.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseMuzu {

    private static final Logger logger = LoggerFactory.getLogger(BaseMuzu.class);

    protected static final String FORMAT = "format";
    protected static final String LENGTH = "l";

    private static final String BASE_URL = "http://www.muzu.tv/api/";
    private static final String API_KEY = "muzuid";

    protected String apiKey;
    protected Map<String, String> parameters = new HashMap<String, String>();

    private APIReader apiReader;

    public BaseMuzu() {
	this.apiKey = Utils.readMandatoryPropertyFrom("resources/muzu.properties", "APIKey");
    }

    abstract protected String apiCommand();

    public BaseMuzu buildUrl() {
	buildAPIReadyToExecute(apiKey, parameters);
	return this;
    }

    /**
     * The format of the response can be specified here. The two format types
     * are rss and xml. Defaults to rss.
     * 
     * @param format
     * @return
     */
    public BaseMuzu withFormat(Format format) {
	parameters.put(FORMAT, format.toString());
	return this;
    }

    public String executeUrl() throws IOException {
	return apiReader.executeUrl();
    }

    protected void buildAPIReadyToExecute(String apiKeyValue, Map<String, String> parameters) {
	UriBuilder uriBuilder = new UriBuilder(BASE_URL).setCommand(apiCommand()).setAPIKey(API_KEY, apiKeyValue);

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