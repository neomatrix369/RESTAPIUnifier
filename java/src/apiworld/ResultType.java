package apiworld;

public enum ResultType {
	rtNone {
		public String toString() {
			return "";
		}
	},
	rtJSON {
		public String toString() {
			return "json";
		}
	},
	rtRSS {
		public String toString() {
			return "rss";
		}
	},
	rtXML {
		public String toString() {
			return "xml";
		}
	}
}