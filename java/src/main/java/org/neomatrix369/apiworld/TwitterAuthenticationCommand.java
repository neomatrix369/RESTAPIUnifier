package org.neomatrix369.apiworld;

import org.neomatrix369.apiworld.exception.APIKeyNotAssignedException;
import org.neomatrix369.apiworld.exception.BaseURLNotAssignedException;

class TwitterAuthenticationCommand{
	private APIConnection connection;

	public TwitterAuthenticationCommand(String commandString, String user){
		
	}
	
	public TwitterAuthenticationCommand(APIConnection connection,
			String user) {
		this.connection = connection;
	}

	public Object execute() throws BaseURLNotAssignedException, APIKeyNotAssignedException{
		return new GenericAPICommandBuilder(connection, "authenticate").withParam("user", "alex").build().execute();
	}
	
}


