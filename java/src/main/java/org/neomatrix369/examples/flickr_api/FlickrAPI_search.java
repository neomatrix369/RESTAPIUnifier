/**
 *
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
package org.neomatrix369.examples.flickr_api;

import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;

import org.neomatrix369.apiworld.ResultType;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FlickrAPI_search {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FlickrAPI_search.class);
    
    private FlickrAPI_search() {
        // Hide utility class constructor
    }

    /**
     * API provider URL: http://www.flickr.com/services/api/
     * 
     * Required settings file to run this example:
     * resources/flickr_settings.properties
     * 
     * containing APIKey=[xxxxx]
     * 
     * [xxxxx] = is APIKey needed to get authentication from flickr.com to be
     * able to make any API calls.
     * 
     */

    public static void main(String[] args) throws InterruptedException,
            FinalURLNotGeneratedException, IOException {
        String flickrAPIKey = readPropertyFrom(
                "resources/flickr_settings.properties", "APIKey");

        FlickrSearch flickrSearch = new FlickrSearch(flickrAPIKey, "&",
                ResultType.JSON.toString(), "hello");
        LOGGER.info(flickrSearch.getFetchedResults());
    }
}

class FlickrSearch extends BaseFlickrAPI {

    FlickrSearch(String apiKey, String paramStart, String... params)
            throws FinalURLNotGeneratedException {
        String apiCommand = "?method=flickr.photos.search";
        String[] arrayURLParamCodes = { "format", "text" };

        fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, paramStart,
                arrayURLParamCodes, params);
        fetchedResults.executeURL();
    }
}