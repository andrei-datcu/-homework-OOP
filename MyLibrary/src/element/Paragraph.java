package element;

import java.util.StringTokenizer;

public class Paragraph extends Body<Sentence>{
	private int charNumber;
	
	public String toString(){
		String result = "";
		for (int i = 0; i < super.size() - 1; ++i)
			result += this.get(i) + " ";
		if (super.size() >= 1)
			result += this.get(super.size() - 1);
		return result;
	}
	
	private void countChars(){
		charNumber = this.toString().length();
	}
	
	public int size(){
		int result = 0;
		for (int i = 0; i < super.size(); result += this.get(i).size(), ++i);
		return result;
	}
	
	public Paragraph(){
	}
	
	public void setFromText(String text){
		this.clear();
		String delim = ".!?", prop="", nextProp ="";
		StringTokenizer st = new StringTokenizer(text, delim, true);
		while (st.hasMoreTokens()){
			prop += st.nextToken();
			nextProp = "";
			while (st.hasMoreTokens() && delim.contains(nextProp)){
				prop += nextProp;
				nextProp = st.nextToken();
			}
			if (!st.hasMoreTokens())
				prop += nextProp;
			this.add(new Sentence(prop));
			prop = nextProp;
		}
		countChars();
	}
	
	public boolean isImageParagraph(){
		String text = this.toString();
		int length = text.length();
		if (length <= 5)
			return false;
		if (text.substring(0, 5).compareTo("<img>") != 0)
			return false;
		return true;
	}

	public String getImagePath(){
		return this.toString().substring(5);
	}
	
	public int getCharCount(){
		return charNumber;
	}
}