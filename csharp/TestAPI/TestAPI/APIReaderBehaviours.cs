using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NUnit.Framework;
using System.Web.Script.Serialization;
using System.Configuration;
using System.Net;
using System.IO;
using System.Xml;

namespace TestAPI
{
    [TestFixture]
    class APIReaderBehaviours
    {
        [Test]
        public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
        {
            string json = "";
            JavaScriptSerializer ser = new JavaScriptSerializer();
            var xx = ser.Deserialize<object>(json);
            string apiKey = ConfigurationManager.AppSettings["APIKey"];
            String url = "http://search.twitter.com/search.{1}?q=hello";
            APIReader apiReader = new APIReader(url, apiKey, APIReader.outputFormat.JSON);
            String result = apiReader.getFetchedResults();
            Assert.AreEqual("http://search.twitter.com/search.json?q=hello", result);
        }


        [Test]
        public void should_Return_Data_As_Valid_JSON_When_API_URL_Is_Passed_In()
        {
            string apiKey = ConfigurationManager.AppSettings["APIKey"];
            String url = "http://search.twitter.com/search.{1}?q=hello";
            APIReader apiReader = new APIReader(url, apiKey, APIReader.outputFormat.JSON);
            String result = apiReader.getFetchedResults();
            string json = apiReader.executeURL();
            JavaScriptSerializer ser = new JavaScriptSerializer();
            var valid = ser.Deserialize<object>(json);

            Assert.IsNotNull(valid);
        }



        [Test]
        public void should_Return_Exception_When_InvalidJSON()
        {
            string apiKey = ConfigurationManager.AppSettings["APIKey"];
            String url = "http://search.twitter.com/search.{1}?q=hello";
            APIReader apiReader = new APIReader(url, apiKey, APIReader.outputFormat.JSON);
            String result = apiReader.getFetchedResults();
            string json = apiReader.executeURL();
            json = json + "xxxxx";
       
            Assert.IsFalse(apiReader.IsValidJSON(json));
     }

        [Test]
        public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In()
        {
            string apiKey = ConfigurationManager.AppSettings["APIKey"];

            String url = "http://www.muzu.tv/api/browse?muzuid={0}&af=a&g=pop&format={1}";   //"http://search.twitter.com/search.{0}?q=hello";
            APIReader apiReader = new APIReader(url, apiKey, APIReader.outputFormat.XML);
            string expected = "http://www.muzu.tv/api/browse?muzuid=" + apiKey + "&af=a&g=pop&format=xml";
            String result = apiReader.getFetchedResults();
            string xml = apiReader.executeURL();
         
            Assert.AreEqual(expected, result);
            Assert.AreEqual(true, apiReader.IsValidXML(xml));

        }

        [Test]
        public void should_Fetch_Data_As_XML_When_API_URL_Is_Passed_In_Invalid()
        {
            string apiKey = ConfigurationManager.AppSettings["APIKey"];

            String url = "http://www.muzu.tv/api/browse?muzuid={0}&af=a&g=pop&format={1}";   //"http://search.twitter.com/search.{0}?q=hello";
            APIReader apiReader = new APIReader(url, apiKey, APIReader.outputFormat.XML);
            string expected = "http://www.muzu.tv/api/browse?muzuid=" + apiKey + "&af=a&g=pop&format=xml";
            String result = apiReader.getFetchedResults();
            string xml = apiReader.executeURL();
            xml = xml + "xxxx";
            Assert.AreEqual(expected, result);
            Assert.AreEqual(false, apiReader.IsValidXML(xml));

        }


    }




    public class APIReader
    {
        string url;
        public enum outputFormat { JSON, XML };

        public APIReader(string _url, string apiKey, outputFormat fmt)
        {

            url = String.Format(_url, apiKey, getOutput(fmt));
        }

        internal string executeURL()
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            StreamReader reader = new StreamReader(response.GetResponseStream());
            return reader.ReadToEnd();
        }

        public string getOutput(outputFormat fmt)
        {

            // var fmt1 = (outputFormat)fmt;
            if (fmt == outputFormat.JSON)
                return "json";

            if (fmt == outputFormat.XML)
                return "xml";

            return "xxxx";
        }

        public string getFetchedResults()
        {
            return url;
        }


        public bool IsValidJSON(string json)
        {

            JavaScriptSerializer ser = new JavaScriptSerializer();
            try
            {
                var valid = ser.Deserialize<object>(json);
            }
            catch (Exception e1)
            { return false; }
            return true;

        }

        public bool IsValidXML(string xml)
        {
           // string xmlfmt = "";
            try
            {
                 var settings = new XmlReaderSettings();
                 settings.ValidationType = ValidationType.Schema;
                using (var reader =
                   XmlReader.Create(new StringReader(xml),
                   settings))
                {
                    while (reader.Read()) { }
                }

                return true;
            }
            catch (Exception e)
            {
                return  false;
            }
        }
    }
}
