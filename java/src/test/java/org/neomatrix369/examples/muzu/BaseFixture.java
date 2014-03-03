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

import org.junit.Before;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

public abstract class BaseFixture {

    protected static final String RSS_RESPONSE_BEGINNING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <rss version=\"2.0\"";

    protected String xmlResponseType;
    protected String xmlResponseBeginning;
    protected String rssDescription;
    protected String rssDescriptionTag;

    @Before
    public void setup() {
        xmlResponseBeginning = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>, <" + xmlResponseType + ">,";
        rssDescriptionTag = "<description>" + rssDescription + "</description>";
    }

    protected void assertResponseIsValidRss(String response) {
        assertThat("The beginning of the response does not match RSS start", response, startsWith(RSS_RESPONSE_BEGINNING));
        assertThat("The rss description tag is not correct", response, containsString(rssDescriptionTag));
    }

    protected void assertResponseIsValidXml(String response) {
        assertThat("The beginning of the response does not match XML start", response, startsWith(xmlResponseBeginning));
    }

}