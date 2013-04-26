package APIWorld;
import org.w3c.dom.Document;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;

public class StringToXML {
	
	StringToXML(String stringInput) throws Exception {
		/*XMLReader myReader = XMLReaderFactory.createXMLReader();
		myReader.setContentHandler(handler);
		myReader.parse(stringInput);*/
		
		// or if you prefer DOM:
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stringInput);
	}
}

/*public class StringToXML {
	XMLReader parser;
	
	try {
	    SAXParserFactory factory;
	
	    factory = SAXParserFactory.newInstance ();
	    factory.setNamespaceAware (true);
	    parser = factory.newSAXParser ().getXMLReader ();
	    // success!
	
	} catch (FactoryConfigurationError err) {
	    System.err.println ("can't create JAXP SAXParserFactory, "
	    + err.getMessage ());
	} catch (ParserConfigurationException err) {
	    System.err.println ("can't create XMLReader with namespaces, "
	    + err.getMessage ());
	} catch (SAXException err) {
	    System.err.println ("Hmm, SAXException, " + err.getMessage ());
	}
}

/*import java.io.File;
import java.io.StringReader;

import java.io.ByteArrayInputStream;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class StringToXML {
	String s="<hi>hello</hi>";

	// create an input stream
	ByteArrayInputStream xmlIn = new ByteArrayInputStream(s.getBytes());

	// get a document builder from the pool
	DocumentBuilder builder = getBuilder(true);

	try {

		// use the DocumentBuilder to parse the XML input.
		Document doc = builder.parse(xmlIn);
	}
	catch (Exception e) {
		throw e;
	}
}*/
