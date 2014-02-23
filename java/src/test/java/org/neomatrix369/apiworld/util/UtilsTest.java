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
package org.neomatrix369.apiworld.util;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.neomatrix369.apiworld.util.Utils.*;

/**
 * Test class UtilsTest.
 *
 * @author helio frota http://www.heliofrota.com
 */
public class UtilsTest {

    @Test
    public void should_Verify_Invalid_JSON_Text() {
        assertThat(isAValidJSONText("{abcde"), is(false));
    }

    @Test
    public void should_Verify_Valid_JSON_Text() {
        assertThat(isAValidJSONText("{color:'green', status: 'good'}"), is(true));
    }

    @Test
    public void should_Return_A_Plus_When_Space_Is_Passed_To_Encode_Token() {
        assertThat(urlEncode(" "), is("+"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_Return_IllegalArgumentException_If_Null_Is_Passed_To_Encode_Token() {
        Utils.urlEncode(null);
    }

    @Test
    public void should_Remove_Trailing_Separator_From_String_When_A_Single_Separator_Is_Passed_In() {
        assertThat(dropTrailingSeparator("http://search.twitter.com/", "/"), is("http://search.twitter.com"));
    }

    @Test
    public void should_Drop_Begin_And_End_Delimiters_In_An_Empty_String() {
        String inputString = "[]";
        String actualString = dropStartAndEndDelimiters(inputString);
        String expectedString = "";
        assertThat("Begin & End delimiters haven't been dropped", actualString, is(expectedString));
    }

    @Test
    public void should_Drop_Double_Begin_And_End_Delimiters_In_An_Empty_String() {
        String inputString = "[[]]";
        String actualString = dropStartAndEndDelimiters(inputString);
        actualString = dropStartAndEndDelimiters(actualString);
        String expectedString = "";
        assertThat("Begin & End delimiters haven't been dropped", actualString, is(expectedString));
    }

    @Test
    public void should_Drop_Begin_And_End_Delimiters_In_A_Simple_String() {
        String inputString = "[{'some': 'value'}]";
        String actualString = dropStartAndEndDelimiters(inputString);
        String expectedString = "{'some': 'value'}";
        assertThat("Begin & End delimiters haven't been dropped", actualString, is(expectedString));
    }

    @Test
    public void should_Drop_Double_Begin_And_End_Delimiters_In_A_Simple_String() {
        String inputString = "[[{'some': 'value'}]]";
        String actualString = dropStartAndEndDelimiters(inputString);
        actualString = dropStartAndEndDelimiters(actualString);
        String expectedString = "{'some': 'value'}";
        assertThat("Begin & End delimiters haven't been dropped", actualString, is(expectedString));
    }

    @Test
    public void should_Return_Property_Value() throws IOException {
        //When
        String propertyValue = readPropertyFrom("java/src/test/resources/test.properties", "propertyKey");
        //Then
        assertThat("The property value is not the expected one", propertyValue, is("propertyValue"));
    }

    @Test(expected = IOException.class)
    public void should_Fail_If_File_Does_Not_Exist() throws IOException {
        //When
        readPropertyFrom("non-existing-path", "propertyKey");
        //Then
        //Exception should have been thrown
    }

    @Test
    public void should_Return_No_Value_If_Property_Does_Not_Exist() throws IOException {
        //When
        String propertyValue = readPropertyFrom("java/src/test/resources/test.properties", "nonExistingPropertyKey");
        //Then
        assertThat("The key value is not the expected one", propertyValue, is(nullValue()));
    }

}