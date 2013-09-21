package org.neomatrix369.apiworld;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class UriBuilderTest {

	@Test
	public void uriBuilder_should_assemble_non_empty_connection_uri()
			throws Exception {

		String baseURL = "http://www.muzu.tv/api/";

		APIConnection connection = new UriBuilder(baseURL).setAPIKey("", "")
				.build();

		assertNotNull(connection);
	}

	@Ignore
	@Test
	public void connectionUri_should_start_with_base_url() throws Exception {
//		assertThat()
	}
}

// new GenericAPICommandBuilder(connection, "authenticate")
// .withParam("user", "alex").build().execute();
//
// new TwitterAuthenticationCommand(connection, "alex").execute();