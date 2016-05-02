package urlreaders;

import java.io.IOException;
import java.net.MalformedURLException;

public class DetailedShowInfoURLReader {
	public static String getLines(int showId) throws MalformedURLException, IOException{
		return URLReader.getLines("http://services.tvrage.com/feeds/full_show_info.php?sid=%s", showId + "");
	}
}