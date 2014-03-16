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

import org.neomatrix369.apiworld.exception.PropertyNotDefinedException;
import org.neomatrix369.examples.muzutv.BaseMuzu;

/**
 * http://www.muzu.tv/api/searchDoc/
 * <p/>
 * http://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=the+script http
 * ://www.muzu.tv/api/search?muzuid=[MUZU_ID]&mySearch=beyonce&format
 * =[rss/xml]&country=gb&l=100
 * <p/>
 * e.g. http://www.muzu.tv/api/search?muzuid=
 * [MUZU_ID]&mySearch=beyonce&format=rss&l=200&country=ie
 */
public class Search extends BaseMuzu {

    private static final String MY_SEARCH = "mySearch";

    public Search() throws PropertyNotDefinedException {
        super();
    }

    @Override
    protected String apiCommand() {
        return "browse";
    }

    /**
     * Enter the word or title you want to search for here. Please ensure that
     * you URL escape your search string. e.g: mySearch=the+script
     *
     * @param searchTerm to use for the search
     * @return Search command with a particular searchTearm
     */
    public Search withSearchTerm(String searchTerm) {
        parameters.put(MY_SEARCH, searchTerm);
        return this;
    }

    /**
     * The number of items to return. Maximum allowed value is 1000. Defaults to
     * 500.
     *
     * @param length number of items to return
     * @return Search command with a particular length
     */
    public Search withLength(int length) {
        parameters.put(LENGTH, String.valueOf(length));
        return this;
    }

}
