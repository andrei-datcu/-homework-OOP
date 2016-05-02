package urlreaders;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import structures.SearchResult;

public class SearchResultXmlParser{

	public static ArrayList<SearchResult> parse(String xml) throws Exception {
		Document doc = AbstractXmlParser.loadXMLFromString(xml); 
		NodeList nList = doc.getElementsByTagName("show");
		ArrayList<SearchResult> result = new ArrayList<SearchResult>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
		      Element eElement = (Element) nNode;
		      result.add(new SearchResult(Integer.parseInt(AbstractXmlParser.getTagValue("showid", eElement)),
		    		  AbstractXmlParser.getTagValue("name", eElement), AbstractXmlParser.getTagValue("country", eElement),
		    		  AbstractXmlParser.getTagValue("status", eElement)));
		   }
		}
		return result;
	}

}
