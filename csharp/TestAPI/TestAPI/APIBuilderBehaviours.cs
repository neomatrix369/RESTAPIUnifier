using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NUnit.Framework;
using System.Web;

namespace TestAPI
{

    public class APIBuilderBehaviours
    {
        private static String MUZUID_KEY = "muzuid";
        private static String MUZUID_VALUE = "[MUZU_ID]";
        private static String MUZU_BASE_URL = "http://www.muzu.tv/api/";
        private static String MUZU_URL_WITH_BROWSE_AND_MUZU_ID = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]";
        private static String MUZU_BASE_URL_WITH_BROWSE_COMMAND = "http://www.muzu.tv/api/browse";
        private static String API_BROWSE_COMMAND = "browse";

        private String baseURL;
        private APIBuilder aPIBuilder;

        [SetUp]
        public void setup()
        {
            baseURL = MUZU_BASE_URL;
            aPIBuilder = new APIBuilder();
            aPIBuilder.setBaseURL(baseURL);
        }


        [Test]
        [ExpectedException(typeof(BaseURLNotAssignedException))]
        public void shouldReturnExceptionWhenNoURLIsSupplied()
        {
            baseURL = "";
            aPIBuilder = new APIBuilder();
            aPIBuilder.setBaseURL(baseURL);
            aPIBuilder.build();
        }

        [Test]
        [ExpectedException(typeof(APIKeyNotAssignedException))]
        public void shouldReturnExceptionWhenNoAPIKeyAssignedAtBuildStage()   
        {
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = MUZU_BASE_URL;
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void shouldReturnURLWithAPIKeyWhenPassedIn()
        {  
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = MUZU_URL_WITH_BROWSE_AND_MUZU_ID;
            Assert.AreEqual(expected, actual);
        }


        [Test]
        public void shouldReturnURLWithAPIKeyAndParamsWhenPassedIn()   
        {
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.addAURLParameter("param1", "value1");
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&param1=value1";
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void shouldReturnURLWithAPIKeyAndSkipParamsWithNulls()    //throws BaseURLNotAssignedException, APIKeyNotAssignedException {
        {
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.addAURLParameter("param1", null);
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]";
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void shouldReturnURLWithAPIKeyAndAllValidParams()    
        {
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.addAURLParameter("key1", "value1");
            aPIBuilder.addAURLParameter("key2", "value2");
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1&key2=value2";
            Assert.AreEqual(expected, actual);
        }



        [Test]
        public void shouldReturnURLWithAPIKeyAndAllValidParamsWhichAreNotNull()
        {
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.addAURLParameter("key1", "value1");
            aPIBuilder.addAURLParameter("key2", null);
            aPIBuilder.addAURLParameter("key3", "value3");
            aPIBuilder.addAURLParameter(null, "value4");
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key1=value1&key3=value3";
            Assert.AreEqual(expected, actual);
        }

        [Test]
        public void shouldReturnURLWithEncodedParam()
        {
            aPIBuilder.setCommand(API_BROWSE_COMMAND);
            aPIBuilder.setAPIKey(MUZUID_KEY, MUZUID_VALUE);
            aPIBuilder.addAURLParameter("key", HttpUtility.UrlEncode("string with space"));
            aPIBuilder.build();
            String actual = aPIBuilder.getAPIReadyURL();
            String expected = "http://www.muzu.tv/api/browse?muzuid=[MUZU_ID]&key=string+with+space";
            Assert.AreEqual(expected, actual);
        }

    }

   
}


