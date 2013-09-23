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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 Create Date: Saturday 21 April 2012 13:18 PM
 Max queries: 10000
 */
public final class MuzuDotTV_artist_api {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuzuDotTV_artist_api.class);

    private MuzuDotTV_artist_api() {
	// Hide utility class constructor
    }

    public static void main(String[] args) throws InterruptedException {
	/**
	 * "http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid=[MUZU_ID]";
	 */
	Properties prop = new Properties();
	try {
	    prop.load(new FileReader(new File("resources/muzu_settings.properties")));
	    String muzuAPIKey = prop.getProperty("APIKey");

	    MuzuArtist muzuArtist = new MuzuArtist(BaseMuzuAPI.MUZU_BASE_URL + "artist/details/Bon+Jovi?muzuid="
		    + muzuAPIKey);

	    LOGGER.info(muzuArtist.getFetchedResults());
	} catch (FileNotFoundException e) {
	    LOGGER.error(e.getMessage());
	} catch (IOException e) {
	    LOGGER.error(e.getMessage());
	}
    }
}

class MuzuArtist extends BaseMuzuAPI {
    MuzuArtist(String apiKey, String... params) {
	String apiCommand = "artist";
	String[] arrayURLParamCodes = { "artist_name", "format", "country", "soundoff", "autostart", "videotype",
		"width", "height", "includeAll" };
	fetchedResults = buildAPIReadyToExecute(apiKey, apiCommand, arrayURLParamCodes, params);
	fetchedResults.executeUrl();
    }
}