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

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.util.Utils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UriBuilderTest {
    private static final String API_KEY = "muzuid";
    private static final String MUZUID_VALUE = "ANY_KEY";
    private static final String MUZU_URL_WITH_BROWSE_AND_MUZU_ID = "http://www.muzu.tv/api/browse?muzuid=ANY_KEY";
    private static final String API_BROWSE_COMMAND = "browse";

    private String baseURL;
    private UriBuilder uriBuilder;

    @Before
    public void setup() {
        baseURL = "http://www.muzu.tv/api/";
        uriBuilder = new UriBuilder(baseURL);
    }

    @Test
    public void uriBuilder_should_assemble_non_empty_connection_uri() throws Exception {
        APIConnection connection = new UriBuilder("http://www.muzu.tv/api/").setNoAPIKeyRequired().build();
        assertNotNull(connection);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_Return_Exception_When_No_URL_Is_Supplied() throws APIKeyNotAssignedException {
        String emptyBaseUrl = "";
        uriBuilder = new UriBuilder(emptyBaseUrl);
    }

    @Test(expected = APIKeyNotAssignedException.class)
    public void should_Return_Exception_When_No_API_Key_Is_Supplied() throws APIKeyNotAssignedException {
        uriBuilder.build();
    }

    @Test
    public void should_Not_Return_Exception_When_No_API_Key_Required_Is_Set() throws APIKeyNotAssignedException {
        uriBuilder.setNoAPIKeyRequired();
        uriBuilder.build();
    }

    @Test
    public void should_Return_URL_With_Command_When_Passed_In() throws APIKeyNotAssignedException {
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
        assertThat(actual, is(expected));
    }

    @Test
    public void should_Return_URL_With_API_Key_And_Params_When_Passed_In() throws APIKeyNotAssignedException {
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);
        uriBuilder.addUrlParameter("key1", "value1");
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID + "&key1=value1";
        assertThat(actual, is(expected));
    }

    @Test
    public void should_Return_URL_With_API_Key_And_All_Valid_Params() throws APIKeyNotAssignedException {
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);
        uriBuilder.addUrlParameter("key1", "value1");
        uriBuilder.addUrlParameter("key2", "value2");
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID + "&key1=value1&key2=value2";
        assertThat(expected, is(actual));
    }

    @Test
    public void should_return_URL_with_API_key_and_skip_params_with_null_keys() throws APIKeyNotAssignedException {
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);

        uriBuilder.addUrlParameter(null, "value1");
        uriBuilder.addUrlParameter("key2", "value2");
        uriBuilder.addUrlParameter("key3", "value3");
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID + "&key2=value2&key3=value3";
        assertThat(actual, is(expected));
    }

    @Test
    public void should_Return_URL_With_API_Key_And_Skip_Params_With_Null_Values() throws APIKeyNotAssignedException {
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);

        uriBuilder.addUrlParameter("key1", "value1");
        uriBuilder.addUrlParameter("key2", null);
        uriBuilder.addUrlParameter("key3", "value3");
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID + "&key1=value1&key3=value3";
        assertThat(actual, is(expected));
    }

    @Test
    public void should_return_URL_with_encoded_param() throws APIKeyNotAssignedException {
        uriBuilder.setCommand(API_BROWSE_COMMAND);
        uriBuilder.setAPIKey(API_KEY, MUZUID_VALUE);
        uriBuilder.addUrlParameter("key", Utils.urlEncode("string with space"));
        uriBuilder.build();
        String actual = uriBuilder.getFinalURL();
        String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID + "&key=string+with+space";
        assertThat(actual, is(expected));
    }

}