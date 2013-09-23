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
package org.neomatrix369.examples.importio_api;

import static org.neomatrix369.apiworld.util.UtilityFunctions.readPropertyFrom;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImportIOAPI_search {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportIOAPI_search.class);

    private ImportIOAPI_search() {
	// Hide utility class constructor
    }

    /**
     * API provider URL: http://docs.import.io/
     * 
     * Required settings file to run this example:
     * resources/importio_settings.properties
     * 
     * containing username=[yyyy] password=[zzzz]
     * 
     * [yyyy] = username registered with import.io [zzzz] = password registered
     * with import.io
     * 
     * API Key is handled differently via this API, an Auth token is return when
     * a successful login occurs. Which can be further used along with secret
     * key to perform further API actions.
     * 
     */

    public static void main(String[] args) throws InterruptedException, IOException {
	String username = readPropertyFrom("resources/importIO_settings.properties", "username");
	String password = readPropertyFrom("resources/importIO_settings.properties", "password");

	ImportIOSearch importIOSearch = new ImportIOSearch("", username, password);
	LOGGER.info(importIOSearch.getFetchedResults());
    }
}

class ImportIOSearch extends ImportIOAPI {
    ImportIOSearch(String paramStart, String... params) {
	String apiCommand = "login";
	String[] arrayURLParamCodes = { "username", "password" };

	fetchedResults = buildAPIReadyToExecute(apiCommand, paramStart, arrayURLParamCodes, params);
	fetchedResults.executeUrl();
    }
}