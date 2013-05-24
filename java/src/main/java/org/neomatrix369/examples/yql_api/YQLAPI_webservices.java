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

import static org.neomatrix369.apiworld.ResultType.rtJSON;
import static org.neomatrix369.apiworld.ResultType.rtXML;

import org.neomatrix369.apiworld.FinalURLNotGeneratedException;
import org.neomatrix369.examples.yql_api.BaseYQLAPI;

public final class YQLAPI_webservices {
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

    public static void main(String[] args) throws InterruptedException,
            FinalURLNotGeneratedException {
        YQLAPIWebServices yqlAPIWebServices = new YQLAPIWebServices("", "?",
                rtJSON.toString(), "show tables");
        System.out.format("%s", yqlAPIWebServices.getFetchedResults());

        yqlAPIWebServices = new YQLAPIWebServices("", "?", rtXML.toString(),
                "show tables", null);
        System.out.format("%s", yqlAPIWebServices.getFetchedResults());
    }
}

class YQLAPIWebServices extends BaseYQLAPI {
    YQLAPIWebServices(String apiKey, String paramStart, String... params)
            throws FinalURLNotGeneratedException {
        String apiCommand = "yql";
        String[] arrayURLParamCodes = { "format", "q" };

        fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
                arrayURLParamCodes, params);
        fetchedResults.executeURL();
    }
}