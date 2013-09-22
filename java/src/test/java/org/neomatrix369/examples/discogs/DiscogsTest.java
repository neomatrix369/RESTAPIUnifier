package org.neomatrix369.examples.discogs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

public class DiscogsTest {

    @Ignore
    @Test
    public void a_restful_artist_returns_some_json() throws Exception {
	assertThat(aRestCall().withUrl("http://api.discogs.com/artists/45").GET(), isA(JSONObject.class));
    }

    private RestClient aRestCall() {
	return null;
    }
}

class RestClient {

    public RestClient withUrl(String string) {
	return this;
    }

    public JSONObject GET() {
	// TODO Auto-generated method stub
	return null;
    }

}
