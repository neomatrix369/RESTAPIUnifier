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
package org.neomatrix369.examples.flickr;

import org.neomatrix369.apiworld.APIReader;

import java.io.IOException;

public class Search {

    private final FlickrAPI flickrAPI;
    private final String apiKey;
    private final String paramStart;
    private final String[] params;

    Search(FlickrAPI flickrAPI, String apiKey, String paramStart, String... params) throws IOException {
        this.flickrAPI = flickrAPI;
        this.apiKey = apiKey;
        this.paramStart = paramStart;
        this.params = params;
   }

    public String execute() throws IOException {
        String apiCommand = "?method=flickr.photos.search";
        String[] arrayURLParamCodes = { "format", "text" };
        APIReader apiReader = flickrAPI.buildAPIReadyToExecute(apiKey, apiCommand, paramStart, arrayURLParamCodes, params);
        return apiReader.executeGetUrl();
    }
}