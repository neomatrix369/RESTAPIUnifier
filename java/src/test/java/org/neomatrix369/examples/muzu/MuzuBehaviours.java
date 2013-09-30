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
package org.neomatrix369.examples.muzu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.examples.muzutv.BaseMuzu;
import org.neomatrix369.examples.muzutv.data.Artist;
import org.neomatrix369.examples.muzutv.data.Browse;
import org.neomatrix369.examples.muzutv.data.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MuzuBehaviours {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MuzuBehaviours.class);

    private static final String MUZU_RSS_RESPONSE_BEGINNING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <rss version=\"2.0\"";
    private static final String MUZU_XML_RESPONSE_BEGINNING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <videos>,";

    @Test
    public void artist_lookup_should_return_xml() throws Exception {

	BaseMuzu muzuArtist = new Artist().withName("yello").build();
	String response = muzuArtist.executeUrl();

	assertThat(response, startsWith(MUZU_RSS_RESPONSE_BEGINNING));
    }

    @Test
    public void browse_lookup_should_return_xml() throws Exception {

	// http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&af=b&g=pop
	BaseMuzu muzuBrowse = new Browse().withGenre("pop").withAlphaFilter("b").build();

	String response = muzuBrowse.executeUrl();
	assertThat(response, startsWith(MUZU_RSS_RESPONSE_BEGINNING));
    }

    @Test
    public void should_be_rss_result_when_format_is_given_as_rss() throws Exception {
	BaseMuzu muzuBrowse = new Browse().withGenre("pop").withFormat("rss").build();
	// http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&af=b&g=pop&format=xml

	String response = muzuBrowse.executeUrl();
	assertThat(response, startsWith(MUZU_RSS_RESPONSE_BEGINNING));
    }

    @Test
    public void should_be_xml_result_when_format_is_given_as_xml() throws Exception {
	BaseMuzu muzuBrowse = new Browse().withGenre("pop").withFormat("xml").build();
	// http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&af=b&g=pop&format=xml

	String response = muzuBrowse.executeUrl();
	assertThat(response, startsWith(MUZU_XML_RESPONSE_BEGINNING));
    }

    @Ignore
    @Test
    public void search() throws Exception {
	// http://www.muzu.tv/api/searchDoc/

	/*
	 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+script
	 * http
	 * ://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=beyonce&format
	 * =[rss/xml]&country=gb&l=100
	 * 
	 * e.g. http://www.muzu.tv/api/search?muzuid=
	 * [MUZU_ID]&mySearch=beyonce&format=rss&l=200&country=ie
	 */

	BaseMuzu search = new Search().withSearchTerm("beyonce").withFormat("rss").withLength(200).withCountry("ie")
		.build();

	String response = search.executeUrl();
	assertThat(response, startsWith(MUZU_RSS_RESPONSE_BEGINNING));
    }

    @Test
    public void search_with_format_xml_should_return_xml() throws Exception {
	// http://www.muzu.tv/api/searchDoc/

	/*
	 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+script
	 * http
	 * ://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=beyonce&format
	 * =[rss/xml]&country=gb&l=100
	 * 
	 * e.g. http://www.muzu.tv/api/search?muzuid=
	 * [MUZU_ID]&mySearch=beyonce&format=rss&l=200&country=ie
	 */

	BaseMuzu search = new Search().withSearchTerm("beyonce").withFormat("rss").withLength(200).withCountry("ie")
		.build();

	String response = search.executeUrl();

	assertThat(response, startsWith(MUZU_RSS_RESPONSE_BEGINNING));
    }

    @Ignore
    @Test
    public void channel() throws Exception {
	// http://www.muzu.tv/api/channelLookupDoc/
    }

    @Ignore
    @Test
    public void video() throws Exception {
	// http://www.muzu.tv/api/videoLookupDoc/
    }

}