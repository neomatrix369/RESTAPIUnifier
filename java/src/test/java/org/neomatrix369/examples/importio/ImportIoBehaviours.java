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
package org.neomatrix369.examples.importio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.util.Utils;

public class ImportIoBehaviours {

    private String username;
    private String password;

    @Before
    public void setup() throws IOException {
	username = Utils.readPropertyFrom("resources/importio.properties", "username");
	password = Utils.readPropertyFrom("resources/importio.properties", "password");
    }

    @Test
    public void login_with_POST_method() throws Exception {

	String urlParameters = String.format("username=%s&password=%s", username, password);
	String request = "https://api.import.io/auth/login";

	APIReader apiReader = new APIReader(request);

	String response = apiReader.executePostUrl(urlParameters);
	assertThat(ImportIO.isSuccessfulResponse(response), is(true));
    }

    // TODO: add test that does login first and stores cookie and then reuses
    // cookie to access another part, e.g. to get API Key. See
    // http://docs.import.io/reference/auth.html#heading-getapikey

}