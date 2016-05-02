package urlreaders;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public abstract class AbstractXmlParser {	
	public static Document loadXMLFromString(String xml) throws Exception
	   	{
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        InputSource is = new InputSource(new StringReader(xml));
	        Document result = builder.parse(is); 
	        result.getDocumentElement().normalize();
	        return result;
	    }
 
	public static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	 
		return nValue == null ? "" : nValue.getNodeValue();
	  }
	 
}

