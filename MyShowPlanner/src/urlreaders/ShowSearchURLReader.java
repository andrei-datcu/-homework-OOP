package urlreaders;

import java.io.IOException;
import java.net.MalformedURLException;

public class ShowSearchURLReader{	
	public static String getLines(String searchText) throws MalformedURLException, IOException{
		return URLReader.getLines("http://services.tvrage.com/feeds/search.php?show=%s", searchText);
	}
}
