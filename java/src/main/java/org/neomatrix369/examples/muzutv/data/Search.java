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
package org.neomatrix369.examples.muzutv.data;

import org.neomatrix369.examples.muzutv.BaseMuzu;

/**
 * http://www.muzu.tv/api/searchDoc/
 * 
 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+script http
 * ://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=beyonce&format
 * =[rss/xml]&country=gb&l=100
 * 
 * e.g. http://www.muzu.tv/api/search?muzuid=
 * [MUZU_ID]&mySearch=beyonce&format=rss&l=200&country=ie
 * 
 */
public class Search extends BaseMuzu {

    private static final String COUNTRY = "country";
    private static final String MY_SEARCH = "mySearch";

    @Override
    protected String apiCommand() {
	return "browse";
    }

    /**
     * Enter the word or title you want to search for here. Please ensure that
     * you URL escape your search string. e.g: mySearch=the+script
     * 
     * @param value
     * @return
     */
    public Search withSearchTerm(String value) {
	parameters.put(MY_SEARCH, value);
	return this;
    }

    /**
     * The number of items to return. Maximum allowed value is 1000. Defaults to
     * 500.
     * 
     * @param i
     * @return
     */
    public Search withLength(int i) {
	parameters.put(LENGTH, String.valueOf(i));
	return this;
    }

    /**
     * Specify the country for which the results are intended by adding the
     * countries abbreviation e.g. Great Britain would be set as 'country=gb'
     * United States would be set as 'country=us'
     * 
     * @param value
     * @return
     */
    public Search withCountry(String value) {
	parameters.put(COUNTRY, value);
	return this;
    }

}
