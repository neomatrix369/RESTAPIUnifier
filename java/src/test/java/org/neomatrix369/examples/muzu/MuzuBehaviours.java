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

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.junit.Test;
import org.neomatrix369.examples.muzutv.Artist;
import org.neomatrix369.examples.muzutv.BaseMuzu;

public class MuzuBehaviours {

    @Test
    public void artist() throws Exception {
	/**
	 * "http://www.muzu.tv/api/artist/details/Bon+Jovi?muzuid=[MUZU_ID]";
	 */
	Properties prop = new Properties();
	prop.load(new FileReader(new File("resources/muzu.properties")));
	String muzuAPIKey = prop.getProperty("APIKey");

	Artist muzuArtist = new Artist(BaseMuzu.MUZU_BASE_URL + "artist/details/Bon+Jovi?muzuid=" + muzuAPIKey);

    }
}