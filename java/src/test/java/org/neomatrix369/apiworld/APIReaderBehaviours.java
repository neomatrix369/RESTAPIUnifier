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
package org.neomatrix369.apiworld;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.apiworld.ResultType.JSON;
import static org.neomatrix369.apiworld.ResultType.XML;
import static org.neomatrix369.apiworld.util.Utils.isAValidJSONText;
import static org.neomatrix369.apiworld.util.Utils.isAValidXML;
import static org.neomatrix369.apiworld.util.Utils.readPropertyFrom;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.neomatrix369.examples.muzutv.BaseMuzu;

public class APIReaderBehaviours {

    private static final String INVALID_XML_RETURNED = "Invalid XML returned.";

    @Test
    public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In() throws FileNotFoundException, IOException {
	String apiKey = readPropertyFrom("resources/muzu.properties", "APIKey");
	String url = String.format(BaseMuzu.MUZU_BASE_URL + "browse?muzuid=%s&af=a&g=pop&format=%s", apiKey, JSON);
	APIReader apiReader = new APIReader(url);
	apiReader.executeUrl();
	String result = apiReader.getFetchedResults();
	assertThat(result.isEmpty(), is(false));

	assertThat(isAValidJSONText(result), is(true));
    }

    @Test
    public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In() throws FileNotFoundException, IOException {
	String apiKey = readPropertyFrom("resources/muzu.properties", "APIKey");
	String url = String.format(BaseMuzu.MUZU_BASE_URL + "browse?muzuid=%s&af=a&g=pop&format=%s", apiKey, XML);
	APIReader apiReader = new APIReader(url);
	apiReader.executeUrl();
	String result = apiReader.getFetchedResults();
	assertThat("Empty result returned.", result.isEmpty(), is(false));
	assertThat(INVALID_XML_RETURNED, isAValidXML(result), is(true));
    }

}