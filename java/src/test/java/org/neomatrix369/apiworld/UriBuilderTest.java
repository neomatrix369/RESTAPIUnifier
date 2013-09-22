package org.neomatrix369.apiworld;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.neomatrix369.examples.muzu_tv_api.BaseMuzuAPI;

public class UriBuilderTest {

    @Test
    public void uriBuilder_should_assemble_non_empty_connection_uri() throws Exception {
	APIConnection connection = new UriBuilder(BaseMuzuAPI.MUZU_BASE_URL).setNoAPIKeyRequired().build();
	assertNotNull(connection);
    }

    @Ignore
    @Test
    public void connectionUri_should_start_with_base_url() throws Exception {
	// assertThat()
    }
}