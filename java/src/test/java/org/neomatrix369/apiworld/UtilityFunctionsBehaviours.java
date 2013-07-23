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

import org.junit.Assert;
import org.junit.Test;
import org.neomatrix369.apiworld.util.Keys;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.apiworld.util.UtilityFunctions.*;


/**
 * Test class UtilityFunctionsTest.
 *
 * @author helio frota http://www.heliofrota.com
 *
 */
public class UtilityFunctionsBehaviours {

    @Test
    public void should_Verify_Invalid_JSON_Text() {
        Assert.assertEquals(false, isAValidJSONText("{abcde"));
    }

    @Test
    public void should_Verify_Valid_JSON_Text() {
        Assert.assertEquals(true, isAValidJSONText("{color:'green', status: 'good'}"));
    }

    
    @Test
    public void should_Return_A_Plus_When_Space_Is_Passed_To_Encode_Token() {
        Assert.assertEquals("+", encodeToken(" "));
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void should_Return_IllegalArgmentException_If_Null_Is_Passed_To_Encode_Token() {
        Assert.fail(encodeToken(null));        
    }
    
    @Test
    public void should_Validate_True_If_String_does_Have_A_Separator() {
        Assert.assertEquals(false, doesHaveSeparator("http://search.twitter.com", "/"));
        Assert.assertEquals(true, doesHaveSeparator("http://search.twitter.com/", "/"));
        Assert.assertEquals(true, doesHaveSeparator("http://search.twitter.com////////", "////////"));
    }
    
    @Test
    public void should_Remove_Trailing_Separator_From_String_When_A_Single_Separator_Is_Passed_In() {
        Assert.assertEquals("http://search.twitter.com", dropTrailingSeparator("http://search.twitter.com/", "/"));
    }

    @Test
    public void should_Remove_Trailing_Separators_From_String_When_Multiple_Separators_Are_Passed_In() {
        Assert.assertEquals("http://search.twitter.com", dropTrailingSeparator("http://search.twitter.com///", "///"));
    }
    
    @Test
    public void should_drop_begin_and_end_delimeters_in_an_Empty_String() {
    	String inputString = "[]";
    	String actualString = dropStartAndEndDelimeters(inputString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);
		String expectedString = "";
		assertThat("Begin & End delimeters haven't been dropped", actualString, is(expectedString));
    }
    
    @Test
    public void should_drop_double_begin_and_end_delimeters_in_an_Empty_String() {
    	String inputString = "[[]]";
    	String actualString = dropStartAndEndDelimeters(inputString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);
    	actualString = dropStartAndEndDelimeters(actualString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);    	
		String expectedString = "";
		assertThat("Begin & End delimeters haven't been dropped", actualString, is(expectedString));
    }
    
    @Test
    public void should_drop_begin_and_end_delimeters_in_a_Simple_String() {
    	String inputString = "[{'some': 'value'}]";
    	String actualString = dropStartAndEndDelimeters(inputString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);
		String expectedString = "{'some': 'value'}";
		assertThat("Begin & End delimeters haven't been dropped", actualString, is(expectedString));
    }
    
    @Test
    public void should_drop_double_begin_and_end_delimeters_in_a_Simple_String() {
    	String inputString = "[[{'some': 'value'}]]";
    	String actualString = dropStartAndEndDelimeters(inputString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);
    	actualString = dropStartAndEndDelimeters(actualString, Keys.INSTANCE.getKey("OPENING_BOX_BRACKET"),
				CLOSING_BOX_BRACKET);    	
		String expectedString = "{'some': 'value'}";
		assertThat("Begin & End delimeters haven't been dropped", actualString, is(expectedString));
    }

}
