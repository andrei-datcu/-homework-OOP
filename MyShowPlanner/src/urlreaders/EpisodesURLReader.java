package urlreaders;

import java.io.IOException;
import java.net.MalformedURLException;

public class EpisodesURLReader {
	public static String getLines(int showId) throws MalformedURLException, IOException{
		return URLReader.getLines("http://services.tvrage.com/feeds/episode_list.php?sid=%s", showId + "");
	}
}
