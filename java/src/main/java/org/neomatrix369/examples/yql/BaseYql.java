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

import org.neomatrix369.apiworld.UriBuilder;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.UtilityFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseYql {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseYql.class);
    
    private String baseURL = "http://query.yahooapis.com/v1/public/yql";
    protected APIReader fetchedResults;

    protected APIReader buildAPIReadyToExecute(String apiKey,
            String apiCommand, String paramStart, String[] arrayURLParamCodes,
            String... params) {
        UriBuilder uriBuilder = new UriBuilder(baseURL)
                    .setCommand(apiCommand)
        			.setParamStart(paramStart)
                    .setApiKeyIsRequired(false);
        int paramCtr = 0;
        for (String eachValue : params) {
            uriBuilder.addAURLParameter(arrayURLParamCodes[paramCtr++],
                    UtilityFunctions.encodeToken(eachValue));
        }

        try {
            uriBuilder.build();
            return new APIReader(uriBuilder);
        } catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
            LOGGER.error(e.getMessage());
        }

        return new APIReader(baseURL);
    }

    public String getFetchedResults() {
        if (fetchedResults != null) {
            return fetchedResults.getFetchedResults();
        }
        return "";
    }
}