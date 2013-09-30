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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YqlBehaviours {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(YqlBehaviours.class);

    private Yql aQuery() {
	return new Yql();
    }

    @Test
    public void should_return_xml_when_no_format_specified() throws Exception {
	// http://query.yahooapis.com/v1/public/yql?q=show%20tables

	String response = aQuery().withStatement("show tables").buildUrl().executeUrl();
	assertThat(
		response,
		startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <query xmlns:yahoo=\"http://www.yahooapis.com/v1/base.rng\" yahoo:count=\""));
	assertThat(response, containsString("<table security=\"ANY\">answers.getbycategory</table>"));

    }

    @Test
    public void should_return_json_when_format_specifies_json() throws Exception {
	// http://query.yahooapis.com/v1/public/yql?q=show%20tables&format=json

	String response = aQuery().withStatement("show tables").withFormat("json").buildUrl().executeUrl();
	assertThat(isJsonResult(response), is(true));

    }

    private boolean isJsonResult(String response) {
	// logger.info(response);
	JsonReader jsonReader = Json.createReader(new StringReader(response));
	JsonObject json = jsonReader.readObject();

	JsonArray table = json.getJsonObject("query").getJsonObject("results").getJsonArray("table");

	JsonObject firstResult = table.getJsonObject(0);
	return firstResult.getString("security").equals("ANY")
		&& firstResult.getString("content").equals("answers.getbycategory");
    }
}