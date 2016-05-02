package element;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Sentence extends ArrayList<String>{
	public String toString(){
		String result = "";
		for (int i = 0; i < this.size() - 1; ++i)
			result += this.get(i) + " ";
		if (this.size() > 0)
			result += this.get(this.size() - 1);
		return result;
	}
	
	public Sentence (String text){
		clear();
		StringTokenizer st = new StringTokenizer(text, " ");
		while (st.hasMoreTokens())
			this.add(st.nextToken());
	}
	
	public Sentence (){
	}
}