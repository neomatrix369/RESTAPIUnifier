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
package org.neomatrix369.examples.muzu.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.examples.muzu.BaseFixture;
import org.neomatrix369.examples.muzutv.BaseMuzu;
import org.neomatrix369.examples.muzutv.data.Artist;
import org.neomatrix369.examples.muzutv.data.Format;

public class ArtistEndpointBehaviours extends BaseFixture {

    private static final String XML_ERROR_RESPONSE_BEGINNING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <error code=";

    private String validArtistName = "yello";
    private String invalidArtistName = "yellooooooo";

    private Artist anArtist() {
	return new Artist().withLength(1);
    }

    private Artist aValidArtist() {
	return anArtist().withName(validArtistName);
    }

    private Artist anInvalidArtist() {
	return anArtist().withName(invalidArtistName);
    }

    @Before
    public void setup() {
	xmlResponseType = "artist";
	rssDescription = "Artist Lookup for" + " " + validArtistName;
	super.setup();
    }

    @Test
    public void should_be_rss_result_if_no_format_given_for_artist_endpoint() throws Exception {

	String response = aValidArtist().buildUrl().executeUrl();
	assertResponseIsValidRss(response);
    }

    @Test
    public void should_be_xml_result_when_format_given_as_xml_for_artist_endpoint() throws Exception {

	BaseMuzu muzuArtist = aValidArtist().withFormat(Format.XML).buildUrl();
	String response = muzuArtist.executeUrl();

	assertThat(response, startsWith(xmlResponseBeginning));
	assertThat(response, containsString("vanityname=\"" + validArtistName + "\">"));
    }

    @Test
    public void should_be_rss_error_result_if_artist_not_found() throws Exception {

	BaseMuzu muzuArtist = anInvalidArtist().buildUrl();
	String response = muzuArtist.executeUrl();
	assertThat(response, startsWith(RSS_RESPONSE_BEGINNING));
	assertThat(response, containsString("<title>Error</title>"));
    }

    @Test
    public void should_be_xml_error_result_if_artist_not_found_when_format_is_given_as_xml() throws Exception {

	BaseMuzu muzuArtist = anInvalidArtist().withFormat(Format.XML).buildUrl();
	String response = muzuArtist.executeUrl();
	assertThat(response, startsWith(XML_ERROR_RESPONSE_BEGINNING));
    }

}