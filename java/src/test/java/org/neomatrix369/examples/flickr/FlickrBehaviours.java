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
package org.neomatrix369.examples.flickr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.apiworld.ResultType;
import org.neomatrix369.apiworld.util.Utils;

public class FlickrBehaviours {

    private String apiKey;

    @Before
    public void setup() throws IOException {
	apiKey = Utils.readPropertyFrom("resources/flickr.properties", "APIKey");
    }

    @Test
    public void recentPhotos() throws IOException {
	RecentPhotos recentPhotos = new RecentPhotos(apiKey, "&", ResultType.JSON.toString());
	String response = recentPhotos.executeUrl();
	assertThat(recentPhotos.isSuccessfulResponse(response), is(true));
    }

    @Test
    public void search() throws Exception {
	Search search = new Search(apiKey, "&", ResultType.JSON.toString(), "hello");
	String response = search.executeUrl();
	assertThat(search.isSuccessfulResponse(response), is(true));
    }

}
