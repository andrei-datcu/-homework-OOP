package structures;

public class SearchResult {
	private int showId;
	private String name, country, status;
	
	public SearchResult(int showId, String name, String country, String status){
		this.showId = showId;
		this.name = name;
		this.country = country;
		this.status = status;
	}
	
	public String getName(){
		return name;
	}
	
	public int getShowId(){
		return showId;
	}
	
	public String getCountry(){
		return country;
	}
	
	public String getStatus(){
		return status;
	}
}
