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
package org.neomatrix369.examples.yql_api;

import org.neomatrix369.apiworld.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class YQLAPI_webservices {

    private static final String COMMAND_SHOW_TABLES = "show tables";
    private static final Logger LOGGER = LoggerFactory.getLogger(YQLAPI_webservices.class);

    private YQLAPI_webservices() {
	// Hide utility class constructor
    }

    /**
     * This example does not explicitly require an API key, but for other API
     * calls an API key maybe required - see YQL's API documentation for further
     * details.
     * 
     * API provider URL: http://developer.yahoo.com/yql/
     * 
     * Required settings file to run this example:
     * resources/yql_settings.properties
     * 
     * containing APIKey=[xxxxx]
     * 
     * [xxxxx] = is APIKey needed to get authentication from yahoo.com to be
     * able to make any API calls.
     * 
     */

    public static void main(String[] args) throws InterruptedException {
	YQLAPIWebServices yqlAPIWebServices = new YQLAPIWebServices("", "?", ResultType.JSON.toString(),
		COMMAND_SHOW_TABLES);
	LOGGER.info(yqlAPIWebServices.getFetchedResults());

	yqlAPIWebServices = new YQLAPIWebServices("", "?", ResultType.XML.toString(), COMMAND_SHOW_TABLES, null);
	LOGGER.info(yqlAPIWebServices.getFetchedResults());
    }
}

class YQLAPIWebServices extends BaseYQLAPI {
    private static final String YQL_COMMAND = "yql";
    private static final String FORMAT_NOTATION = "q";
    private static final String FORMAT = "format";

    YQLAPIWebServices(String apiKey, String paramStart, String... params) {
	String apiCommand = YQL_COMMAND;
	String[] arrayURLParamCodes = { FORMAT, FORMAT_NOTATION };

	fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart, arrayURLParamCodes, params);
	fetchedResults.executeUrl();
    }
}