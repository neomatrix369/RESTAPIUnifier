/**
 * Copyright (c) 2013. All rights reserved.
 * <p/>
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * <p/>
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 * <p/>
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * <p/>
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.neomatrix369.apiworld;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class APIReaderTest {

    private APIReader apiReader;
    private URL url;
    @Mock private HttpURLConnection mockConnection;
    private URLStreamHandler urlStreamHandler = new URLStreamHandler() {
        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return mockConnection;
        }
    };

    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("http://restapiunifier.com", "restapiunifier.com", -1, "", urlStreamHandler);
        apiReader = new APIReader(url);
    }

    @Test
    public void should_Send_A_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        //When
        apiReader.executeUrl();
        //Then
        verify(mockConnection).setRequestMethod("GET");
    }

    @Test
    public void should_Return_Response_To_Http_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        //When
        String response = apiReader.executeUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Return_Response_Without_Delimiters_To_Http_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("[[response]]"));
        //When
        String response = apiReader.executeUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Send_A_Get_Request_With_RequestProperties() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        Map<String, String> properties = new HashMap<>();
        String propertyKey1 = "propertyKey1";
        String propertyValue1 = "propertyValue1";
        String propertyKey2 = "propertyKey2";
        String propertyValue2 = "propertyValue2";
        properties.put(propertyKey1, propertyValue1);
        properties.put(propertyKey2, propertyValue2);
        //When
        apiReader.executeGetUrl(properties);
        //Then
        verify(mockConnection).setRequestProperty(propertyKey1, propertyValue1);
        verify(mockConnection).setRequestProperty(propertyKey2, propertyValue2);
    }

}