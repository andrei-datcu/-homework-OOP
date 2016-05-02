package structures;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Episode {
	private String title;
	private int number, mainNumber;
	private Date airDate;
	private boolean seen;
	
	public Episode(String title, int number, int mainNumber, String date) throws ParseException{
		this.title = title;
		this.number = number;
		this.mainNumber = mainNumber;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		airDate = format.parse(date);
		seen = false;
	}
	
	public String toString(){
		return number +": " + title + "(" + DateFormat.getDateInstance().format(airDate)+")";
	}
	
	public void setSeen(boolean newValue){
		seen = newValue;
	}
	
	public int getNumber(){
		return mainNumber;
	}
	public boolean isSeen(){
		return seen;
	}
}
