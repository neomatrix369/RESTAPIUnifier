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
package examples.ImportIOAPI;

import apiworld.APIBuilder;
import apiworld.APIReader;

import apiworld.APIKeyNotAssignedException;
import apiworld.BaseURLNotAssignedException;
import apiworld.UtilityFunctions;

public class ImportIOAPI {
	
	private String baseURL = "https://api.import.io/auth/";
	protected APIReader fetchedResults;

	protected APIReader buildAPIReadyToExecute(String apiCommand, String paramStart,
			String[] arrayURLParamCodes, String... params) {
		APIBuilder apiBuilder = new APIBuilder();
		apiBuilder.addBaseURL(baseURL);
		apiBuilder.setCommand(apiCommand);
		apiBuilder.setParamStart(paramStart);
		apiBuilder.setNoAPIKeyRequired();
		int paramCtr = 0;
		for (String eachValue : params) {
			apiBuilder.addAURLParameter(arrayURLParamCodes[paramCtr++],
					UtilityFunctions.encodeToken(eachValue));
		}

		try {
			apiBuilder.build();
			return new APIReader(apiBuilder);
		} catch (BaseURLNotAssignedException | APIKeyNotAssignedException e) {
			System.out.format("%s", e.getMessage());
		}
		
		return new APIReader(baseURL);
	}
	
	public String getFetchedResults() {
		if (fetchedResults != null) {
			return fetchedResults.getFetchedResults();
		}
		return "";
	}
}