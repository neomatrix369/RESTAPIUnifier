package org.neomatrix369.apiworld;

import org.junit.Assert;
import org.junit.Test;
import org.neomatrix369.apiworld.util.UtilityFunctions;

/**
 * Test class UtilityFunctionsTest.
 *
 * @author helio frota http://www.heliofrota.com
 *
 */
public class UtilityFunctionsTest {

    @Test
    public void isAValidJSONText() {
        Assert.assertEquals(false, UtilityFunctions.isAValidJSONText("{abcde"));
        Assert.assertEquals(true, UtilityFunctions.isAValidJSONText("{color:'green', status: 'good'}"));
    }
    
    @Test
    public void encodeToken() {
        Assert.assertEquals("+", UtilityFunctions.encodeToken(" "));
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void encodeTokenException() {
        UtilityFunctions.encodeToken(null);        
    }
    
    @Test
    public void doesHaveSeparator() {
        Assert.assertEquals(false, UtilityFunctions.doesHaveSeparator("http://search.twitter.com", "/"));
        Assert.assertEquals(true, UtilityFunctions.doesHaveSeparator("http://search.twitter.com/", "/"));
        Assert.assertEquals(true, UtilityFunctions.doesHaveSeparator("http://search.twitter.com////////", "////////"));
    }
    
    @Test
    public void dropTrailingSeparator() {
        Assert.assertEquals("http://search.twitter.com", UtilityFunctions.dropTrailingSeparator("http://search.twitter.com/", "/"));
        Assert.assertEquals("http://search.twitter.com", UtilityFunctions.dropTrailingSeparator("http://search.twitter.com///", "///"));
    }
    
}
