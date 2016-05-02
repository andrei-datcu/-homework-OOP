package element;

import java.util.ArrayList;

public class Chapter implements interfaces.IChapter, interfaces.Clearable{
	private String name;
	private ArrayList<Paragraph> paragraphs;
	
	public Chapter(String name){
		this.name = name;
		paragraphs = new ArrayList<Paragraph>();
	}
	
	public Chapter(){
		this("");
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void changeChapterName(String newChapterName) {
		name = newChapterName;
	}
	
	public void remove(int i){
		paragraphs.remove(i);
	}

	public void add(Paragraph p) {
		paragraphs.add(p);
	}
	
	public void add(int i, Paragraph p){
		paragraphs.add(i, p);
	}

	public int size() {
		return paragraphs.size();
	}

	public void clear() {
		paragraphs.clear();
	}
	
	public String toString(){
		String result = "";
		result += this.name +"\n\n";
		for (int i = 0; i < paragraphs.size() - 1; ++i)
			result += paragraphs.get(i) + "\n";
		result += paragraphs.get(paragraphs.size() - 1);
		return result;
	}

	public Paragraph getMaxParagraph(){
		Paragraph result = null;
		for (int i = 0; i < paragraphs.size(); ++i)
			if (result == null)
				result = paragraphs.get(i);
			else
				if (result.size() < paragraphs.get(i).size())
					result = paragraphs.get(i);
		return result;
	}
	
	public Paragraph getParagraph(int i){
		return paragraphs.get(i);
	}	
	public String getName(){
		return name;
	}
}
