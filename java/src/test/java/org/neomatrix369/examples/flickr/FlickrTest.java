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

import org.junit.Test;

public class FlickrTest {

    @Test
    public void should_remove_jsonFlickrApi_to_receive_json_response() {
	String flickrResponse = "jsonFlickrApi({\"method\":{\"_content\":\"flickr.test.echo\"}, \"format\":{\"_content\":\"json\"}, \"api_key\":{\"_content\":\"4cccadecce65a39cfcfee90b1c01c6a4\"}, \"stat\":\"ok\"})";
	String jsonResponse = "{\"method\":{\"_content\":\"flickr.test.echo\"}, \"format\":{\"_content\":\"json\"}, \"api_key\":{\"_content\":\"4cccadecce65a39cfcfee90b1c01c6a4\"}, \"stat\":\"ok\"}";
	assertThat(aFlickrRestApi().extractJson(flickrResponse), is(jsonResponse));
    }

    private BaseFlickr aFlickrRestApi() {
	return new BaseFlickr();
    }
}
