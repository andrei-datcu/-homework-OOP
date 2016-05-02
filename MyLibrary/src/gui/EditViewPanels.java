package gui;

import javax.swing.JPanel;

import element.Book;
import element.Library;

public class EditViewPanels extends AbstractViewMode{

	private Book b;
	private Library l;
	
	public EditViewPanels(Book b, Library l){
		this.b = b;
		this.l = l;
	}
	
	JPanel authorPanel() {
		// TODO Auto-generated method stub
		return new EditPanel(b, l);
	}

	JPanel publishPanel() {
		return new ViewPanel(b);
	}
}
