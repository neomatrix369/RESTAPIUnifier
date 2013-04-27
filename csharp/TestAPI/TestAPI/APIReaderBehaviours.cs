using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using NUnit.Framework;
using System.Web.Script.Serialization;
using System.Configuration;
using System.Net;
using System.IO;

namespace TestAPI
{
    [TestFixture]
    class APIReaderBehaviours
    {
        enum outputFormat { rtJSON , XML  };

     [Test]
	public void should_Fetch_Data_As_JSON_When_API_URL_Is_Passed_In()
     {	
          
     
 //json)   var serializer = new JavaScriptSerializer();
//var;
         string json = "";
         JavaScriptSerializer ser = new JavaScriptSerializer(); 
var xx = ser.Deserialize<object>(json);

         
         string apiKey = ConfigurationManager.AppSettings["APIKey"];

         //throws FileNotFoundException, IOException,
		//	FinalURLNotGeneratedException {
	//	Properties prop = new Properties();
		//prop.load(new FileReader(new File("resources/muzu_settings.properties")));
	//	String apiKey = prop.getProperty("APIKey");

	//	String url = String.format(
	//			"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=" + rtJSON, apiKey);

//String url = String.Format( "http://search.twitter.com/search.{0}?q=hello", outputFormat.rtJSON, apiKey);
        String url = "http://search.twitter.com/search.{0}?q=hello";
         APIReader apiReader = new APIReader(url, outputFormat.rtJSON, apiKey);
		String result = apiReader.getFetchedResults();
        Assert.AreEqual("http://search.twitter.com/search.json?q=hello", result);
		}


           [Test]
	public void should_Return_Data_As_Valid_JSON_When_API_URL_Is_Passed_In()
     {	        
         string apiKey = ConfigurationManager.AppSettings["APIKey"];

         //throws FileNotFoundException, IOException,
		//	FinalURLNotGeneratedException {
	//	Properties prop = new Properties();
		//prop.load(new FileReader(new File("resources/muzu_settings.properties")));
	//	String apiKey = prop.getProperty("APIKey");

	//	String url = String.format(
	//			"http://www.muzu.tv/api/browse?muzuid=%s&af=a&g=pop&format=" + rtJSON, apiKey);

//String url = String.Format( "http://search.twitter.com/search.{0}?q=hello", outputFormat.rtJSON, apiKey);
        String url = "http://search.twitter.com/search.{0}?q=hello";
         APIReader apiReader = new APIReader(url, outputFormat.rtJSON, apiKey);
		String result = apiReader.getFetchedResults();
        string json = apiReader.executeURL();
        JavaScriptSerializer ser = new JavaScriptSerializer();
        var valid = ser.Deserialize<object>(json);

        Assert.IsNotNull(valid);
   	}



           [Test]
          // [ExpectedException(typeof(ArgumentException))]
           public void should_Return_Exception_When_InvalidJSON()
           {
               string apiKey = ConfigurationManager.AppSettings["APIKey"];

               //String url = String.Format( "http://search.twitter.com/search.{0}?q=hello", outputFormat.rtJSON, apiKey);
               String url = "http://search.twitter.com/search.{0}?q=hello";
               APIReader apiReader = new APIReader(url, outputFormat.rtJSON, apiKey);
               String result = apiReader.getFetchedResults();
               string json = apiReader.executeURL();
                 json = json + "xxxxx";
               //  IsValidJSON(json);
           
                Assert.IsFalse(IsValidJSON(json));
               
          //     .AreEqual("http://search.twitter.com/search.json?q=hello", result);
           }


           bool IsValidJSON(string json)
           {

               JavaScriptSerializer ser = new JavaScriptSerializer();
               try
               {
                   var valid = ser.Deserialize<object>(json);
               }
               catch  (Exception e1)
               { return false; }
               return true;

           }
    }




    public class APIReader
    {
        string url;
         enum outputFormat { rtJSON , XML  };

                public APIReader (string __url, object fmt, string apiKey) {
                    url = __url;

                    url = String.Format(__url, getOutput(outputFormat.rtJSON), apiKey);
     
	}

                internal string executeURL()
                {
                     HttpWebRequest request = (HttpWebRequest)WebRequest.Create (url );
HttpWebResponse response = (HttpWebResponse)request.GetResponse();
StreamReader reader = new StreamReader(response.GetResponseStream()); 

return reader.ReadToEnd();


                }


    public  string  getOutput(object fmt)
      {

        var fmt1 = (outputFormat)fmt;
        if (fmt1 == outputFormat.rtJSON)

          return "json" ;

        return "xxxx";
      }

    public string getFetchedResults()
    {

  
        return url;  // "xxxx";
    }
    }
}
