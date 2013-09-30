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

import org.neomatrix369.apiworld.ResultType;

public final class Webservices {

    private static final String COMMAND_SHOW_TABLES = "show tables";

    private Webservices() {
	// Hide utility class constructor
    }

    /**
     * This example does not explicitly require an API key, but for other API
     * calls an API key maybe required - see YQL's API documentation for further
     * details.
     * 
     * API provider URL: http://developer.yahoo.com/yql/
     * 
     * Required settings file to run this example: resources/yql.properties
     * 
     * containing APIKey=[xxxxx]
     * 
     * [xxxxx] = is APIKey needed to get authentication from yahoo.com to be
     * able to make any API calls.
     * 
     * @throws IOException
     * 
     */

    public static void main(String[] args) throws InterruptedException, IOException {
	YQLAPIWebServices yqlAPIWebServices = new YQLAPIWebServices("", "?", ResultType.JSON.toString(),
		COMMAND_SHOW_TABLES);

	yqlAPIWebServices = new YQLAPIWebServices("", "?", ResultType.XML.toString(), COMMAND_SHOW_TABLES, null);
    }
}

class YQLAPIWebServices extends BaseYql {
    private static final String YQL_COMMAND = "yql";
    private static final String FORMAT_NOTATION = "q";
    private static final String FORMAT = "format";

    YQLAPIWebServices(String apiKey, String paramStart, String... params) throws IOException {
	String apiCommand = YQL_COMMAND;
	String[] arrayURLParamCodes = { FORMAT, FORMAT_NOTATION };

	fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart, arrayURLParamCodes, params);
	fetchedResults.executeUrl();
    }
}