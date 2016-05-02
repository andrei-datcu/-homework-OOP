/*
 * Created by JFormDesigner on Sun Dec 30 20:03:20 EET 2012
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import element.Book;
import element.Chapter;
import element.ChapterIterator;

/**
 * @author Andrei Datcu
 */
public class ViewPanel extends JPanel {
	
	private class ChapterListEntry{
		public int pageNumber;
		public String chapterTitle;
		public ChapterListEntry(int pageNumber, String chapterTitle){
			this.pageNumber = pageNumber;
			this.chapterTitle = chapterTitle;
		}
	}
	public ViewPanel(Book b) {
		this.b = b;
		initComponents();
	}
	
	public void paintPages(){
		pagesPanel.removeAll();
		pagesPanel.revalidate();
		pagesPanel.repaint();
		int pageNumber = 1, maxParagraph = 0;
		Chapter curChapter = null;
		Font font = new Font(fontChooserComboBox1.getSelectedFontName(), Font.PLAIN, (Integer)fontSizeSpinner.getValue());
		ArrayList<ChapterListEntry> listData = new ArrayList<ChapterListEntry>();
		for (int chapterIndex = 0; chapterIndex < b.size(); ++chapterIndex){
			curChapter = b.getChapter(chapterIndex);
			if (curChapter.size() == 0)
				continue;
			ChapterIterator it = new ChapterIterator();	
			maxParagraph = curChapter.size();
			ChapterListEntry entry = new ChapterListEntry(pageNumber, curChapter.getName());
			listData.add(entry);
			while (it.paragraphIndex < maxParagraph){
				PagePanel page = new PagePanel(it, curChapter, font, curChapter.getName(),
						b.getAuthor() + " - " + b.getName(), pageNumber++, 567, 800);
				pagesPanel.add(page, new GridBagConstraints(0,  pageNumber - 2, 1, 1, 0, 0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(20, 0, 0, 0), 0, 0));
				it = page.getChapterIterator();
			}
		}
		chapterList.setListData(listData.toArray());
	}
	
	private void searchText(){
		String text = searchTextField.getText();
		boolean caseSensitive = caseCheckBox.isSelected(); 
		ArrayList<Integer> v = null;
		searchResultY = new ArrayList<Integer>();
		PagePanel page = null;
		for (int i = 0; i < pagesPanel.getComponentCount(); ++i){
			page  = (PagePanel)pagesPanel.getComponent(i);
			v = page.searchAndHighlightText(text, caseSensitive);
			for (int k = 0; k < v.size(); ++k){
				searchResultY.add(v.get(k) + i * (page.getPreferredSize().height +20) + 20); 
			}
		}
		searchResults.setText(searchResultY.size() + " rezultate");
		searchResults.setVisible(true);
		stopSearchButton.setEnabled(true);
		if (searchResultY.size() > 0){
			nextButton.setEnabled(true);
			previousButton.setEnabled(true);
			currentResultIndex = 0;
			showResult();
		}
	}
	
	private void showResult(){
		pagesScrollPane.getViewport().setViewPosition(new Point(0, searchResultY.get(currentResultIndex)));
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Andrei Datcu
		toolBoxPanel = new JToolBar();
		fontChooserComboBox1 = new FontChooserComboBox();
		fontSizeSpinner = new JSpinner();
		searchButton = new JButton();
		pagesScrollPane = new JScrollPane();
		pagesPanel = new JPanel();
		chapterList = new JList();
		chapterList.setCellRenderer(new MultiColumnCellRenderer());
		chapterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chapterPane = new JScrollPane();
		searchTextField = new JTextField(30);
		previousButton = new JButton();
		nextButton = new JButton();
		caseCheckBox = new JCheckBox("case sensitive");
		stopSearchButton = new JButton();
		searchResults = new JLabel();
		//======== this ========

		setLayout(new GridBagLayout());
		((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

		//======== toolBoxPanel ========
		{
			toolBoxPanel.setFloatable(false);

			//---- fontChooserComboBox1 ----
			fontChooserComboBox1.setSelectedIndex(9);
			fontChooserComboBox1.setToolTipText("Click pentru a alege alt font");
			fontChooserComboBox1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					paintPages();
					if (searchResultY != null){
						int aux = currentResultIndex;
						searchText();
						currentResultIndex = aux;
						showResult();
					}
				}
			});
			toolBoxPanel.add(fontChooserComboBox1);

			//---- fontSizeSpinner ----
			fontSizeSpinner.setModel(new SpinnerNumberModel(12, 5, 36, 1));
			fontSizeSpinner.setToolTipText("Modifica dimensiunea textului");
			fontSizeSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					paintPages();
					if (searchResultY != null){
						int aux = currentResultIndex;
						searchText();
						currentResultIndex = aux;
						showResult();
					}
				}
			});
			toolBoxPanel.add(fontSizeSpinner);
			toolBoxPanel.addSeparator();
			toolBoxPanel.add(new JLabel("Cauta: "));
			//---- searchButton ----
			
			searchTextField.setToolTipText("Expresia cautata");
			toolBoxPanel.add(searchTextField);
			previousButton.setIcon(new ImageIcon("icons"+File.separator+"previous.png"));
			previousButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					currentResultIndex = (currentResultIndex + searchResultY.size() - 1) % searchResultY.size();// nu-i modulo in java 
					showResult();
				}
			});
			
			nextButton.setIcon(new ImageIcon("icons"+File.separator+"next.png"));
			nextButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					currentResultIndex = (currentResultIndex + 1) % searchResultY.size();
					showResult();
				}
			});
			
			previousButton.setEnabled(false);
			nextButton.setEnabled(false);
			
			toolBoxPanel.add(previousButton);
			toolBoxPanel.add(nextButton);
			toolBoxPanel.add(caseCheckBox);
			
			searchButton.setIcon(new ImageIcon("icons"+File.separator+"search.png"));
			searchButton.setToolTipText("Cauta");
			searchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (searchTextField.getText() == "")
						return;
					searchText();
				}
			});
			toolBoxPanel.add(searchButton);
			
			stopSearchButton.setIcon(new ImageIcon("icons"+File.separator+"stopsearch.png"));
			stopSearchButton.setToolTipText("Opreste cautarea si deselecteaza rezultatele");
			stopSearchButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < pagesPanel.getComponentCount(); ++i)
						((PagePanel)pagesPanel.getComponent(i)).stopSearch();
					nextButton.setEnabled(false);
					previousButton.setEnabled(false);
					stopSearchButton.setEnabled(false);
					searchResults.setVisible(false);
					searchResultY = null;
				}
			});
			toolBoxPanel.add(stopSearchButton);
			stopSearchButton.setEnabled(false);
			searchResults.setVisible(false);
			toolBoxPanel.add(searchResults);
		}
		add(toolBoxPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));

		//======== pagesScrollPane ========
		{

			//======== pagesPanel ========
			{
				pagesPanel.setBackground(new Color(153, 153, 153));
				pagesPanel.setLayout(new GridBagLayout());
				((GridBagLayout)pagesPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)pagesPanel.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)pagesPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)pagesPanel.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};
			}
			pagesScrollPane.setViewportView(pagesPanel);
		}
		
		chapterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (chapterList.getValueIsAdjusting())
					return;
				if (chapterList.getSelectedIndex() == -1)
					return;
				int page = ((ChapterListEntry)chapterList.getSelectedValue()).pageNumber;
				int size = pagesPanel.getComponent(0).getHeight() + 20;
				pagesScrollPane.getViewport().setViewPosition(new Point(0, size * (page - 1)));
			}
		});
		chapterPane.setViewportView(chapterList);
		chapterPane.setPreferredSize(new Dimension(300, 100));
		chapterPane.setMinimumSize(new Dimension(300, 100));
		add(chapterPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 10, 0, 20), 0, 0));
		
		add(pagesScrollPane, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		paintPages();
	}
	

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Andrei Datcu
	private JToolBar toolBoxPanel;
	private FontChooserComboBox fontChooserComboBox1;
	private JSpinner fontSizeSpinner;
	private JButton searchButton;
	private JScrollPane pagesScrollPane;
	private JPanel pagesPanel;
	private JTextField searchTextField;
	private JButton previousButton, nextButton;
	private JButton stopSearchButton;
	private JCheckBox caseCheckBox; 
	private JLabel searchResults;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private Book b;
	private JList chapterList;
	private JScrollPane chapterPane;
	private ArrayList<Integer> searchResultY;
	private int currentResultIndex;
	
	   private class MultiColumnCellRenderer extends JPanel
	   implements ListCellRenderer {
	 
	   private JLabel chapterName, chapterPage;
	   public MultiColumnCellRenderer() {
	       setLayout(new GridBagLayout());
	       chapterName = new JLabel();
	       chapterPage = new JLabel();
	    add(chapterName, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
	    		new Insets(0, 10, 0, 20), 0, 0));
	    
	    add(chapterPage, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
	    		new Insets(0, 0, 0, 15), 0, 0));
	   }
	   public Component getListCellRendererComponent(JList list, Object value,
	       int index, boolean isSelected, boolean cellHasFocus) {
	       ChapterListEntry celldata = (ChapterListEntry)value;

	       chapterName.setText(celldata.chapterTitle);
	       chapterPage.setText(""+celldata.pageNumber);

	    if(isSelected) {
//	    	chapterName.setBackground(list.getSelectionBackground());
	    	chapterName.setForeground(list.getSelectionForeground());
	    	
//	    	chapterPage.setBackground(list.getSelectionBackground());
	    	chapterPage.setForeground(list.getSelectionForeground());
	    	
	    	this.setBackground(list.getSelectionBackground());
	    	this.setForeground(list.getSelectionForeground());
	    }
	    else {
///	    	chapterName.setBackground(list.getBackground());
	    	chapterName.setForeground(list.getForeground());
	    	
//	    	chapterPage.setBackground(list.getBackground());
	    	chapterPage.setForeground(list.getForeground());
	    	
	    	this.setBackground(list.getBackground());
	    	this.setForeground(list.getForeground());
	    }
	       super.setEnabled(list.isEnabled());
	       super.setFont(list.getFont());
	       return this;
	   }
	     }
	    
}
