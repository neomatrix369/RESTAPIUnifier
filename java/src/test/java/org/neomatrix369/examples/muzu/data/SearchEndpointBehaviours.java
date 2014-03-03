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
import org.neomatrix369.examples.muzu.VideoBaseFixture;
import org.neomatrix369.examples.muzutv.BaseMuzu;
import org.neomatrix369.examples.muzutv.data.Format;
import org.neomatrix369.examples.muzutv.data.Search;

public class SearchEndpointBehaviours extends VideoBaseFixture {

    private Search aSearchForArtist() {
        return new Search().withSearchTerm("beyonce").withLength(1);
    }

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void should_default_to_rss_format() throws Exception {
        //Given/When
        String response = aSearchForArtist().buildUrl().executeUrl();
        //Then
        assertResponseIsValidRss(response);
    }

    @Test
    public void should_retrieve_appropriate_format() throws Exception {
        //Given
        BaseMuzu search = aSearchForArtist().withFormat(Format.XML).buildUrl();
        //When
        String response = search.executeUrl();
        //Then
        assertResponseIsValidXml(response);
    }

}