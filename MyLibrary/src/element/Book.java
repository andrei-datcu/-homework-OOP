package element;

import java.util.ArrayList;

public class Book implements interfaces.IBook, interfaces.Clearable{
	private String author, name, ISBN;
	private ArrayList<Chapter> chapters;
	
	public Book(String author, String name, String ISBN){
		this.author = author;
		this.name = name;
		this.ISBN = ISBN;
		chapters = new ArrayList<Chapter>();
	}
	

	public void add(Chapter ch) {
		chapters.add(ch);
	}
	
	public void add(int i, Chapter ch){
		chapters.add(i, ch);
	}

	public void remove(int i) {
		chapters.remove(i);
				
	}
	
	public Chapter getChapter(int i){
		return chapters.get(i);
	}
	
	public Paragraph getMaxParagraph(){
		Paragraph result = null, aux = null;
		for (int i = 0; i < chapters.size(); ++i)
			if (result == null)
				result = chapters.get(i).getMaxParagraph();
			else{
				aux = chapters.get(i).getMaxParagraph();
				if (aux != null && result.size() < aux.size())
					result = aux;
			}
		return result;
	}

	public void renameAuthor(String newAuthorName) {
		author = newAuthorName;
	}

	public void renameBook(String newBookName) {
		name = newBookName;
	}

	public void changeISBN(String newISBN) {
		ISBN = newISBN;
	}

	public int size() {
		return chapters.size();
	}

	public void clear() {
		chapters.clear();
	}
	
	public String toString(){
		String result = "";
		result += this.author + "\n";
		result += this.name + "\n";
		for (int i = 0; i < chapters.size(); ++i)
			result += chapters.get(i) + "\n";
		return result;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getISBN(){
		return ISBN;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}

	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}
}
