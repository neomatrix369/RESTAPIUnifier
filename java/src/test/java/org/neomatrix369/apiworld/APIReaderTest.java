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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class APIReaderTest {

    private APIReader apiReader;
    @Mock
    private HttpURLConnection mockConnection;
    @Mock
    private OutputStream mockOutputStream;
    @Mock
    private Writer mockWriter;
    private URLStreamHandler urlStreamHandler = new URLStreamHandler() {
        @Override
        protected URLConnection openConnection(URL url) throws IOException {
            return mockConnection;
        }
    };
    private URL url;

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
        apiReader.executeGetUrl();
        //Then
        verify(mockConnection).setRequestMethod("GET");
    }

    @Test
    public void should_Return_Response_To_Http_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        //When
        String response = apiReader.executeGetUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Return_Response_Without_Delimiters_To_Http_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("[[response]]"));
        //When
        String response = apiReader.executeGetUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Send_A_Get_Request_With_Request_Properties() throws Exception {
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

    @Test
    public void should_Disconnect_When_Http_Get_Request_Response_Is_Back() throws IOException {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream(""));
        //When
        apiReader.executeGetUrl();
        //Then
        verify(mockConnection).disconnect();
    }

    @Test(expected = IOException.class)
    public void should_Throw_Exception_When_Connection_Error_On_Get_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenThrow(IOException.class);
        //When
        apiReader.executeGetUrl(new HashMap<String, String>());
        //Then
        //Exception should be thrown
    }

    @Test
    public void should_Send_A_Post_Request_With_Appropriate_Options_And_Properties_And_Content_Type_And_Charset() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        apiReader.setHeader("headerKey", "headerValue");
        //When
        apiReader.executePostUrl();
        //Then
        verify(mockConnection).setDoOutput(true);
        verify(mockConnection).setDoInput(true);
        verify(mockConnection).setInstanceFollowRedirects(false);
        verify(mockConnection).setRequestMethod("POST");
        verify(mockConnection).setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        verify(mockConnection).setRequestProperty("charset", "utf-8");
        verify(mockConnection).setUseCaches(false);
        verify(mockConnection).setRequestProperty("headerKey", "headerValue");
    }

    @Test
    public void should_Return_Response_To_Http_Post_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        //When
        String response = apiReader.executePostUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Return_Response_Without_Delimiters_To_Http_Post_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("[[response]]"));
        //When
        String response = apiReader.executePostUrl();
        //Then
        assertThat(response, is("response"));
    }

    @Test
    public void should_Fire_Http_Post_Request_With_Appropriate_Url_Parameters() throws Exception {
        //Given
        String urlParameters = "urlParameters";
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response"));
        APIReader apiReader = new APIReader(url) {
            @Override
            Writer createWriter(OutputStream outputStream) {
                return mockWriter;
            }
        };

        //When
        apiReader.executePostUrl(urlParameters);
        //Then
        verify(mockWriter).write(urlParameters);
        verify(mockWriter).flush();
        verify(mockWriter).close();
    }

    @Test
    public void should_Disconnect_When_Http_Post_Request_Response_Is_Back() throws IOException {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream(""));
        //When
        apiReader.executePostUrl();
        //Then
        verify(mockConnection).disconnect();
    }

    @Test(expected = IOException.class)
    public void should_Throw_Exception_When_Connection_Error_On_Post_Request() throws Exception {
        //Given
        when(mockConnection.getInputStream()).thenThrow(IOException.class);
        //When
        apiReader.executePostUrl();
        //Then
        //Exception should be thrown
    }

    @Test
    public void should_Only_Return_Response_For_Last_Request() throws IOException {
        //Given
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response 1"));
        apiReader.executeGetUrl();
        when(mockConnection.getInputStream()).thenReturn(IOUtils.toInputStream("response 2"));
        //When
        String response = apiReader.executeGetUrl();
        //Then
        assertThat(response, is("response 2"));
    }

}