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
package org.neomatrix369.examples.heroku_api;

import java.io.IOException;
import java.util.Map;

import org.neomatrix369.apiworld.APIBuilder;
import org.neomatrix369.apiworld.APIReader;
import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;
import org.neomatrix369.apiworld.exception.FinalURLNotGeneratedException;

public class HerokuAPI {

	private static final String HTTP_POST_METHOD = "POST";
	private static final String HTTP_GET_METHOD = "GET";
	private static final String baseURL = "https://api.heroku.com/apps";
	protected APIReader fetchedResults;

	public static String OAuth(Map<String, String> param) throws FinalURLNotGeneratedException, IOException, BaseURLNotAssignedException, APIKeyNotAssignedException {
		APIBuilder apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.setApiKeyIsRequired(false);
		apiBuilder.build();
		APIReader apiReader = new APIReader(apiBuilder);		
		apiReader.executeURL(HTTP_POST_METHOD, param);
		return apiReader.getFetchedResults();
	}
}
