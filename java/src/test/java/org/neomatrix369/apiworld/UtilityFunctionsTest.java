package org.neomatrix369.apiworld;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neomatrix369.apiworld.util.UtilityFunctions;

/**
 * Test class UtilityFunctionsTest.
 *
 * @author helio frota http://www.heliofrota.com
 *
 */
public class UtilityFunctionsTest {

    private UtilityFunctions utilityFunctions;
    
    @Before
    public void setUp() {
        // enum singleton ?
        //utilityFunctions = new UtilityFunctions(); 
    }
    
    @Test
    public void isAValidJSONText() {
        // this is not a valid jsonText we need to do a refactor.
        Assert.assertEquals(true, UtilityFunctions.isAValidJSONText("{abcde"));
    }
    
    
}
