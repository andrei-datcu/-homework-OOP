package urlreaders;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import structures.Episode;

public class EpisodesXmlParser {
	public static Date nextEpisode(String xml) throws Exception {
		Document doc = AbstractXmlParser.loadXMLFromString(xml); 
		NodeList nList = doc.getElementsByTagName("episode");
		Date currentDate = new Date(), result = null, episodeDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int temp = nList.getLength() - 1; temp > 0; --temp) {
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		      Element eElement = (Element) nNode;
		      episodeDate = format.parse(AbstractXmlParser.getTagValue("airdate", eElement));
		      if (currentDate.compareTo(episodeDate) <= 0)
		    	  result = episodeDate;
		      else
		    	  break;
		   }
		}
		return result;
	}
	
	public static DefaultMutableTreeNode parse(String xml) throws Exception{
		Document doc = AbstractXmlParser.loadXMLFromString(xml);
		NodeList seasonsList = doc.getElementsByTagName("Season");
		DefaultMutableTreeNode result = new DefaultMutableTreeNode("Seasons");
		for (int i = 0; i <seasonsList.getLength(); ++i){
			Node season = seasonsList.item(i);
			if (season.getNodeType() == Node.ELEMENT_NODE){
				DefaultMutableTreeNode seasonTreeNode = new DefaultMutableTreeNode("Sezonul " + season.getAttributes().item(0).getNodeValue());
				result.add(seasonTreeNode);
				NodeList episodeList = season.getChildNodes();
				for (int j = 0; j < episodeList.getLength(); ++j){
					Node episode = episodeList.item(j);
					if (episode.getNodeType() == Node.ELEMENT_NODE){
						Element el = (Element)episode;
						seasonTreeNode.add(new DefaultMutableTreeNode(new Episode(AbstractXmlParser.getTagValue("title", el), 
								Integer.parseInt(AbstractXmlParser.getTagValue("seasonnum", el)), 
								Integer.parseInt(AbstractXmlParser.getTagValue("epnum", el)), 
								AbstractXmlParser.getTagValue("airdate", el))));
					}
				}
			}
		}
		return result;
	}

}
