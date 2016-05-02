package urlreaders;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import structures.DetailedShowInfo;

public class DetailedShowInfoXmlParser {
	public static DetailedShowInfo parse(String xml) throws Exception {
			Document doc = AbstractXmlParser.loadXMLFromString(xml); 
			DetailedShowInfo result = null;
			Element eElement = doc.getDocumentElement();
			result = new DetailedShowInfo(AbstractXmlParser.getTagValue("name", eElement), AbstractXmlParser.getTagValue("origin_country", eElement),
					AbstractXmlParser.getTagValue("airday", eElement), AbstractXmlParser.getTagValue("airtime", eElement),
					AbstractXmlParser.getTagValue("network", eElement), AbstractXmlParser.getTagValue("started", eElement),
					AbstractXmlParser.getTagValue("ended", eElement), AbstractXmlParser.getTagValue("image", eElement),
					AbstractXmlParser.getTagValue("showlink", eElement));
			NodeList akas = eElement.getElementsByTagName("aka");
			for (int i = 0; i < akas.getLength(); ++i){
				Node node = akas.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
					result.addAka(node.getFirstChild().getNodeValue() + " (in " + node.getAttributes().item(0).getNodeValue() + ")");
			}
			return result;
		}
	
}