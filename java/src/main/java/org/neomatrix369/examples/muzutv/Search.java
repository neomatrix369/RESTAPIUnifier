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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.neomatrix369.apiworld.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Search {

    private static final Logger LOGGER = LoggerFactory.getLogger(Search.class);

    private Search() {
	// Hide utility class constructor
    }

    public static void main(String[] args) throws InterruptedException {
	Properties prop = new Properties();
	try {
	    prop.load(new FileReader(new File("resources/muzu.properties")));
	    String muzuAPIKey = prop.getProperty("APIKey");

	    /**
	     * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&format=json&
	     * mySearch=the+script
	     * 
	     */
	    MuzuSearch muzuSearch = new MuzuSearch(muzuAPIKey, "the script", null, ResultType.JSON.toString());
	    LOGGER.info(muzuSearch.getFetchedResults());
	} catch (FileNotFoundException e) {
	    LOGGER.error(e.getMessage());
	} catch (IOException e) {
	    LOGGER.error(e.getMessage());
	}
    }
}

class MuzuSearch extends BaseMuzu {

    MuzuSearch(String apiKey, String... params) throws IOException {
	String apiCommand = "search";
	String[] arrayURLParamCodes = { "mySearch", "l", "format", "country", "soundoff", "autostart", "videotype",
		"width", "height", "includeAll" };

	fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, arrayURLParamCodes, params);
	fetchedResults.executeUrl();
    }
}