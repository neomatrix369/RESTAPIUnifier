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
import static org.hamcrest.Matchers.startsWith;

import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.examples.muzu.VideoBaseFixture;
import org.neomatrix369.examples.muzutv.BaseMuzu;
import org.neomatrix369.examples.muzutv.data.Format;
import org.neomatrix369.examples.muzutv.data.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchEndpointBhaviours extends VideoBaseFixture {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SearchEndpointBhaviours.class);

    private Search aSearchForArtist() {
	return new Search().withSearchTerm("beyonce").withLength(1);
    }

    @Before
    public void setup() {
	super.setup();
    }

    @Test
    public void search() throws Exception {

	String response = aSearchForArtist().buildUrl().executeUrl();
	assertResponseIsValidRss(response);
    }

    @Test
    public void search_with_format_xml_should_return_xml() throws Exception {

	BaseMuzu search = aSearchForArtist().withFormat(Format.XML).buildUrl();

	String response = search.executeUrl();
	assertThat(response, startsWith(xmlResponseBeginning));
    }

}