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
package org.neomatrix369.examples.muzu_tv_api;

import org.neomatrix369.apiworld.APIBuilder;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.util.UtilityFunctions;

public class BaseMuzuAPI {

    private static final String MUZUID_URL_PARAM = "muzuid";
    private String baseURL = "http://www.muzu.tv/api/";
    protected APIReader fetchedResults;

    protected APIReader buildAPIReadyToExecute(String apiKey,
            String apiCommand, String[] arrayURLParamCodes, String... params) {
        APIBuilder apiBuilder = new APIBuilder();
        apiBuilder.addBaseURL(baseURL);
        apiBuilder.setCommand(apiCommand);
        apiBuilder.setAPIKey(MUZUID_URL_PARAM, apiKey);
        int paramCtr = 0;
        for (String eachValue : params) {
            apiBuilder.addAURLParameter(arrayURLParamCodes[paramCtr++],
                    UtilityFunctions.encodeToken(eachValue));
        }

        try {
            apiBuilder.build();
            return new APIReader(apiBuilder);
        } catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
            System.out.format("%s", e.getMessage());
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