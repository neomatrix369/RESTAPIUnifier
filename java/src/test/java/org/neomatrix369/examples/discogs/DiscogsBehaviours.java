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
package org.neomatrix369.examples.discogs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;
import org.neomatrix369.apiworld.APIReader;

public class DiscogsBehaviours {

    // private static final Logger logger =
    // LoggerFactory.getLogger(DiscogsBehaviours.class);

    @Test
    public void artist_endpoint_returns_some_json() throws Exception {
	String request = "http://api.discogs.com/artists/45";
	APIReader apiReader = new APIReader(request);
	apiReader.executeUrl();
	assertThat(isJsonResult(apiReader.executeUrl()), is(true));
    }

    private boolean isJsonResult(String response) {
	// logger.info(response);
	JsonReader jsonReader = Json.createReader(new StringReader(response));
	JsonObject json = jsonReader.readObject();

	return json.getString("realname").equals("Richard David James");
    }

}
