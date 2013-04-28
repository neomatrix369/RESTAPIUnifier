using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TestAPI
{
    public class APIBuilder
    {
        string url = "";
        string key = "";
        string value = "";
        string command = "";
        public bool keyRequired { get; set; }

        Dictionary<string, string> lstParams;
        public APIBuilder()
        {
            lstParams = new Dictionary<string, string>();
            keyRequired = true;
        }

        public void setBaseURL(string baseURL)
        {
            url = baseURL;
        }
        public void build()
        {
            if ((url == null) || (url.Trim() == ""))
                throw new BaseURLNotAssignedException();

            if (command != "")
                url = url + command;

            if (keyRequired)
            {
                if ((key == null) || (value == null) || (value == ""))
                    throw new APIKeyNotAssignedException();

                url = url + "?" + key + "=" + value;
            }

            if (lstParams.Count > 0)
                foreach (var d in lstParams)
                    url = url + "&" + d.Key + "=" + d.Value;
        }

        public string getAPIReadyURL()
        {
            return url;
        }

        public void setCommand(string API_BROWSE_COMMAND)
        {
            command = API_BROWSE_COMMAND;
        }

        internal void setAPIKey(string MUZUID_KEY, string MUZUID_VALUE)
        {
            key = MUZUID_KEY;
            value = MUZUID_VALUE;
        }

        public void addAURLParameter(string p1, string p2)
        {
            if ((p1 == null) || (p2 == null))
                return;
            lstParams.Add(p1, p2);
        }

        internal void setNoAPIKeyRequired()
        {
            keyRequired = false;
        }

      
    }


    public class APIKeyNotAssignedException : Exception
    {
        public APIKeyNotAssignedException()
        { }
    }


    public class BaseURLNotAssignedException : Exception
    {
        public BaseURLNotAssignedException()
        { }
    }

   
}
