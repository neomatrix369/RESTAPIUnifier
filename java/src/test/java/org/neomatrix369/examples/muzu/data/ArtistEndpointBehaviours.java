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

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.examples.muzu.BaseFixture;
import org.neomatrix369.examples.muzutv.BaseMuzu;
import org.neomatrix369.examples.muzutv.data.Artist;
import org.neomatrix369.examples.muzutv.data.Format;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

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
    public void should_default_to_rss_format() throws Exception {
        //Given/When
        String response = aValidArtist().buildUrl().executeUrl();
        //Then
        assertResponseIsValidRss(response);
    }

    @Test
    public void should_retrieve_appropriate_format() throws Exception {
        //Given
        BaseMuzu muzuArtist = aValidArtist().withFormat(Format.XML).buildUrl();
        //When
        String response = muzuArtist.executeUrl();
        //Then
        assertResponseIsValidXml(response);
        assertThat("The vanityname tag is not valid", response, containsString("vanityname=\"" + validArtistName + "\">"));
    }

    @Test
    public void should_return_error_on_rss_format() throws Exception {
        //Given
        BaseMuzu muzuArtist = anInvalidArtist().buildUrl();
        //When
        String response = muzuArtist.executeUrl();
        //Then
        assertThat("The beginning of the response does not match RSS start", response, startsWith(RSS_RESPONSE_BEGINNING));
        assertThat("There is no error tag", response, containsString("<title>Error</title>"));
    }

    @Test
    public void should_be_xml_error_result_if_artist_not_found_when_format_is_given_as_xml() throws Exception {
        //Given
        BaseMuzu muzuArtist = anInvalidArtist().withFormat(Format.XML).buildUrl();
        //When
        String response = muzuArtist.executeUrl();
        //Then
        assertThat("The beginning of the response does not match XML error start", response, startsWith(XML_ERROR_RESPONSE_BEGINNING));
    }

}