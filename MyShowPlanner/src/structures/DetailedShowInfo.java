package structures;

import java.util.ArrayList;

public class DetailedShowInfo {
	public String name, country, airDay, network, started, ended, airTime, imageURL, tvrageURL;
	public ArrayList<String> akas;
	
	public DetailedShowInfo(String name, String country, String airDay, String airTime, String network, String started, String ended,
			String imageURL, String tvrageURL){
		this.name = name;
		this.country = country;
		this.airDay = airDay;
		this.network = network;
		this.started = started;
		this.ended = ended;
		this.airTime = airTime;
		this.akas = new ArrayList<String>();
		this.imageURL = imageURL;
		this.tvrageURL = tvrageURL;
	}
	
	public void addAka(String aka){
		akas.add(aka);
	}
}
