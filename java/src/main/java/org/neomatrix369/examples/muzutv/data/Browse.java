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
 * http://www.muzu.tv/api/browseDoc/
 * 
 * Examples:
 * 
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&ob=views e.g.
 * 
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&ob=recent&l=200&of=50
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&af=b&g=pop
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&g=rock
 * http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&g=rock&soundoff=y
 * 
 */
public class Browse extends BaseMuzu {

    @Override
    protected String apiCommand() {
	return "browse";
    }

    public Browse withGenre(String value) {
	parameters.put("g", value);
	return this;
    }

    public Browse withAlphaFilter(String value) {
	parameters.put("af", value);
	return this;
    }

    /**
     * The number of items to return. Maximum allowed value is 1000. Defaults to
     * 500.
     * 
     * @param i
     * @return
     */
    public Browse withLength(int i) {
	parameters.put(LENGTH, String.valueOf(i));
	return this;
    }

}