package structures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ShowSet extends TreeSet<Show>{
	public ShowSet(){
		super(new Comparator<Show>() {
			public int compare(Show o1, Show o2) {
				if (o1.getShowId() == o2.getShowId())
					return 0;
				if (o1.getName().compareTo(o2.getName()) != 0)
					return o1.getName().compareTo(o2.getName());
				return (o1.getShowId() - o2.getShowId());
			}
		});
	}
	
	public ShowSet(String xmlFile){
		this();
		try {
			XStream xstream = new XStream(new DomDriver());
			ShowSet s = (ShowSet) xstream.fromXML(new FileInputStream(xmlFile));
			this.addAll(s);
		} catch (FileNotFoundException e) {
		}
	}
	
	public void saveToFile(String xmlFile){
		XStream xstream = new XStream(new DomDriver());
		try {
			xstream.toXML(this, new FileOutputStream(xmlFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Show getById(int showId){
		Show s = null;
		for (Iterator<Show> it = this.descendingIterator(); it.hasNext();){
			s = it.next();
			if (s.getShowId() == showId)
				return s;
		}
		return null;
	}
	
}
