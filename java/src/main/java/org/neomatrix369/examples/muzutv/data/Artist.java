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
 * documentation: http://www.muzu.tv/api/artistLookupDoc/
 * 
 * http://www.muzu.tv/api/artist/details/?aname=[ARTIST_NAME]&muzuid=[ MUZU_ID]
 * 
 * e.g:
 * 
 * http://www.muzu.tv/api/artist/details/?muzuid=[MUZU_ID]&aname=yello
 * http://www.muzu.tv/api/artist/details/?aname=yello&muzuid=[MUZU_ID]&soundoff
 * =y
 * 
 */
public class Artist extends BaseMuzu {

    @Override
    protected String apiCommand() {
	return "artist/details/";
    }

    public Artist withName(String name) {
	parameters.put("aname", name);
	return this;
    }

    /**
     * The number of items to return. Maximum allowed value is 1000. Defaults to
     * 500.
     * 
     * @param i
     * @return
     */
    public Artist withLength(int i) {
	parameters.put(LENGTH, String.valueOf(i));
	return this;
    }

}