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
package org.neomatrix369.apiworld;

import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;

// use like this:
//new GenericAPICommandBuilder(connection, "authenticate")
//.withParam("user", "alex").build().execute();
//
//new TwitterAuthenticationCommand(connection, "alex").execute();

class TwitterAuthenticationCommand {
    private APIConnection connection;

    public TwitterAuthenticationCommand(String commandString, String user) {
    }

    public TwitterAuthenticationCommand(APIConnection connection, String user) {
        this.connection = connection;
    }

    public Object execute() throws APIKeyNotAssignedException {
        return new GenericAPICommandBuilder(connection, "authenticate").withParam("user", "alex").build().execute();
    }

}