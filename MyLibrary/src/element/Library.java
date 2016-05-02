package element;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Library implements interfaces.ILibrary, interfaces.Clearable{
	
	private ArrayList<Book> books;
	
	public Library(){
		books = new ArrayList<Book>();
	}
	
	public Book getMaxChapterBook(){
		int maxChapter = -1;
		for (int i = 0; i < books.size(); ++i)
			if (maxChapter == -1)
				maxChapter = i;
			else
				if (books.get(maxChapter).size() < books.get(i).size())
					maxChapter = i;
		
		return maxChapter > -1 ? books.get(maxChapter) : null;
	}
	
	public int size(){
		return books.size();
	}
	
	public Paragraph getMaxParagraph(){
		Paragraph result = null, aux = null;
		for (int i = 0; i < books.size(); ++i){
			aux = books.get(i).getMaxParagraph();
			if (result == null)
				result = aux;
			else
				if (aux != null && result.size() < aux.size())
					result = aux;
		}
		return result;
	}
	
	public void clear() {
		books.clear();
	}

	public void add(Book book) {
		books.add(book);
	}

	public void remove(Book book) {
		books.remove(book);
	}
	
	public void remove(int index){
		books.remove(index);
	}
	
	public Library(String xmlFile){
		try {
			XStream xstream = new XStream(new DomDriver());
			Library l = (Library) xstream.fromXML(new FileInputStream(xmlFile));
			this.books = l.books;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveToFile(String xmlFile){
		XStream xstream = new XStream(new DomDriver());
		try {
			xstream.toXML(this, new FileOutputStream(xmlFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vector<String> titles(){
		Vector<String> result = new Vector<String>();
		for (int i = 0; i < books.size(); ++i)
			result.add(books.get(i).getName());
		return result;
	}
	
	public Book get(int i){
		return books.get(i);
	}

}
