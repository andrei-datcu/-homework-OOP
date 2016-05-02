package structures;

import java.util.HashSet;


public class Show extends SearchResult{
	private HashSet<Integer> watchedEpisodes;
	public Show(int showId, String name, String country, String status){
		super(showId, name, country, status);
		watchedEpisodes = new HashSet<Integer>(); 
	}
	
	public Show(SearchResult search){
		this(search.getShowId(), search.getName(), search.getCountry(), search.getStatus());
	}
	
	public boolean equals(Object obj){
		return (((Show)obj).getShowId() == this.getShowId());	
	}
	
	public boolean isEpisodeWatched(int number){
		return watchedEpisodes.contains(new Integer(number));
	}
	
	public void setEpisodeWatched(int number, boolean newValue){
		if (newValue)
			watchedEpisodes.add(new Integer(number));
		else
			watchedEpisodes.remove(new Integer(number));
	}
}
