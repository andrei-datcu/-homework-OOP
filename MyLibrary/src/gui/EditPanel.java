/*
 * Created by JFormDesigner on Sat Dec 29 17:54:05 EET 2012
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Filter;

import javax.swing.*;

import element.Book;
import element.Chapter;
import element.Library;
import element.Paragraph;
import element.Sentence;
import gui.components.*;
/**
 * @author Andrei Datcu
 */
public class EditPanel extends JPanel {
	private class ChapterPanel extends JPanel{
		private class ParagraphPanel extends JPanel{
			public Paragraph p;
			private JTextArea paragraphText;
			private JScrollPane paragraphScrollPane;
			public ParagraphPanel(Paragraph p){
				this.p = p;
				this.setLayout(new GridBagLayout());
				((GridBagLayout)this.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)this.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)this.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)this.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//---- deleteParagraphButton ----
				JButton deleteParagraphButton = new JButton();
				deleteParagraphButton.setText("<html>Sterge<br>paragraf");
				deleteParagraphButton.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
				this.add(deleteParagraphButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.NONE,
					new Insets(10, 5, 10, 10), 0, 0));
				
				deleteParagraphButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int index = ((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).getConstraints(
								ParagraphPanel.this).gridy;
						GridBagConstraints c = null;
						ChapterPanel.this.ch.remove(index - 1);
						ChapterPanel.this.paragraphsPanel.remove(ParagraphPanel.this);
						for (int i = 0; i < ChapterPanel.this.paragraphsPanel.getComponentCount(); ++i){
							c = ((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).getConstraints(
									ChapterPanel.this.paragraphsPanel.getComponent(i));
							if (c.gridy > index){
								--c.gridy;
								((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).setConstraints(ChapterPanel.this.paragraphsPanel.getComponent(i), c);
							}
						}
						ChapterPanel.this.paragraphsPanel.revalidate();
						EditPanel.this.mainPanel.revalidate();
					}
				});
				
				JButton setImageButton = new JButton("Adauga imagine");
				setImageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int returnVal = EditPanel.this.pictureDialog.showDialog(EditPanel.this,
                                "Alege o poza");
						if (returnVal == JFileChooser.APPROVE_OPTION) {
				            JLabel imageLabel = new JLabel();
				            Icon icon = new ImageIcon(pictureDialog.getSelectedFile().getPath());
				            if (icon.getIconWidth() > 500 || icon.getIconHeight() > 500){
				            	JOptionPane.showMessageDialog(EditPanel.this,
									    "Dimensiunea imaginii nu poate depasi 500x500 pixeli.",
									    "Eroare",
									    JOptionPane.ERROR_MESSAGE);
				            	return;
				            }
				            imageLabel.setIcon(icon);
				            ParagraphPanel.this.p.setFromText("<img>"+ pictureDialog.getSelectedFile().getPath());
				            Sentence s = new Sentence();
				            s.add("<img>"+ pictureDialog.getSelectedFile().getPath());
				            ParagraphPanel.this.p.clear();
				            ParagraphPanel.this.p.add(s);
				            ParagraphPanel.this.paragraphText = null;
				            paragraphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				            paragraphScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				            ParagraphPanel.this.paragraphScrollPane.setViewportView(imageLabel);
				            pictureDialog.setSelectedFile(null);
						}
					}
				});
				this.add(setImageButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0, 5, 0, 10), 0, 0));
					

				//======== paragraphScrollPane ========
					paragraphScrollPane = new JScrollPane();
					if (!p.isImageParagraph()){
						paragraphScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						paragraphScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

					//---- paragraphText ----
					paragraphText = new JTextArea();
					paragraphText.setLineWrap(true);
					paragraphText.setWrapStyleWord(true);
					paragraphText.setRows(5);
					paragraphText.setText(p.toString());
					paragraphScrollPane.setViewportView(paragraphText);
					}
					else{
						paragraphScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						paragraphScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						paragraphText = null;
						JLabel imageLabel = new JLabel();
			            Icon icon = new ImageIcon(p.getImagePath());
			            imageLabel.setIcon(icon);
			            paragraphScrollPane.setViewportView(imageLabel);
						
					}
				this.add(paragraphScrollPane, new GridBagConstraints(1, 0, 1, 2, 1.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(10, 0, 10, 30), 0, 40));

				//---- newParagraphButton ----
				JButton newParagraphButton = new JButton();
				newParagraphButton.setText("Paragraf nou");
				newParagraphButton.setIcon(UIManager.getIcon("FileView.fileIcon"));
				this.add(newParagraphButton, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
				
				newParagraphButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int index = ((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).getConstraints(
								ParagraphPanel.this).gridy;
						GridBagConstraints c = null;
						ChapterPanel.this.ch.add(index, new Paragraph());
						for (int i = 0; i < ChapterPanel.this.paragraphsPanel.getComponentCount(); ++i){
							c = ((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).getConstraints(
									ChapterPanel.this.paragraphsPanel.getComponent(i));
							if (c.gridy > index){
								++c.gridy;
								((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).setConstraints(ChapterPanel.this.paragraphsPanel.getComponent(i), c);
							}
						}
						ChapterPanel.this.paragraphsPanel.add(new ParagraphPanel(ch.getParagraph(index)), new GridBagConstraints(0, 1 + index, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						ChapterPanel.this.paragraphsPanel.revalidate();
						EditPanel.this.mainPanel.revalidate();
					}
				});
			}
	}
		
		private JPanel paragraphsPanel;
		private JScrollPane paragraphsScrollPane;
		public Chapter ch;
		private JTextField chapterTitleText;
		
		public void save(){
			Component[] comps = paragraphsPanel.getComponents();
			for (int i = 0; i < comps.length; ++i)
				if (comps[i] instanceof ParagraphPanel)
					if (((ParagraphPanel)comps[i]).paragraphText != null)
						((ParagraphPanel)comps[i]).p.setFromText(((ParagraphPanel)comps[i]).paragraphText.getText().replace("\n", " "));
		}
		
		public ChapterPanel(Chapter ch){
			paragraphsPanel = new JPanel();
			paragraphsScrollPane = new JScrollPane();
			this.ch = ch;
			this.setLayout(new GridBagLayout());
			((GridBagLayout)this.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)this.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
			((GridBagLayout)this.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
			((GridBagLayout)this.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

			//---- deleteChapterButton ----
			JButton deleteChapterButton = new JButton();
			deleteChapterButton.setText("Sterge capitol");
			deleteChapterButton.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
			this.add(deleteChapterButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(0, 5, 10, 10), 0, 0));
			
			deleteChapterButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int index = ((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
							ChapterPanel.this).gridy;
					GridBagConstraints c;
					EditPanel.this.b.remove(index - 1);
					EditPanel.this.mainPanel.remove(ChapterPanel.this);
					for (int i = 0; i < EditPanel.this.mainPanel.getComponentCount(); ++i)
						if (((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
							EditPanel.this.mainPanel.getComponent(i)).gridy > index)
								((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
										EditPanel.this.mainPanel.getComponent(i)).gridy--;
					for (int i = 0; i < EditPanel.this.mainPanel.getComponentCount(); ++i){
						c = ((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
								EditPanel.this.mainPanel.getComponent(i));
						if (c.gridy > index){
							--c.gridy;
							((GridBagLayout)EditPanel.this.mainPanel.getLayout()).setConstraints(EditPanel.this.mainPanel.getComponent(i), c);
						}
					}
					
					EditPanel.this.mainPanel.revalidate();
					EditPanel.this.revalidate();
					EditPanel.this.repaint();
				}
			});

			//---- chapterTitleLabel ----
			chapterTitleText = new JTextField(20);
			chapterTitleText.setText(ch.getName());
			//chapterTitleText.setFont(new Font("Dialog", Font.BOLD, 14));
			this.add(chapterTitleText, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.CENTER,
				new Insets(0, 0, 10, 0), 0, 0));

			//======== paragraphsScrollPane ========

				//======== paragraphsPanel ========
					paragraphsPanel.setLayout(new GridBagLayout());
					((GridBagLayout)paragraphsPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)paragraphsPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)paragraphsPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
					((GridBagLayout)paragraphsPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

					//---- firstNewParagraphButton ----
					JButton firstNewParagraphButton = new JButton();
					firstNewParagraphButton.setText("Paragraf nou");
					firstNewParagraphButton.setIcon(UIManager.getIcon("FileView.fileIcon"));
					paragraphsPanel.add(firstNewParagraphButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(20, 0, 0, 0), 0, 0));
					
					firstNewParagraphButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							ChapterPanel.this.ch.add(0, new Paragraph());
							int index = 0;
							GridBagConstraints c = null;
							for (int i = 0; i < ChapterPanel.this.paragraphsPanel.getComponentCount(); ++i){
								c = ((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).getConstraints(
										ChapterPanel.this.paragraphsPanel.getComponent(i));
								if (c.gridy > index){
									++c.gridy;
									((GridBagLayout)ChapterPanel.this.paragraphsPanel.getLayout()).setConstraints(ChapterPanel.this.paragraphsPanel.getComponent(i), c);
								}
							}
							ChapterPanel.this.paragraphsPanel.add(new ParagraphPanel(ChapterPanel.this.ch.getParagraph(index)), new GridBagConstraints(0, 1 + index, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							ChapterPanel.this.paragraphsPanel.revalidate();
							EditPanel.this.mainPanel.revalidate();
						}
					});

					for (int i = 0; i < ch.size(); ++i)
						paragraphsPanel.add(new ParagraphPanel(ch.getParagraph(i)), new GridBagConstraints(0, 1 + i, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
				paragraphsScrollPane.setViewportView(paragraphsPanel);
				paragraphsScrollPane.setPreferredSize(new Dimension(100, 200));
			this.add(paragraphsScrollPane, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
				new Insets(0, 150, 10, 50), 0, 0));

			//---- newChapterButton ----
			JButton newChapterButton = new JButton();
			newChapterButton.setText("Capitol Nou");
			newChapterButton.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
			this.add(newChapterButton, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.NORTH, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 0), 0, 0));
			newChapterButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int index = ((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
							ChapterPanel.this).gridy;
					GridBagConstraints c = null;
					b.add(index, new Chapter());
					for (int i = 0; i < EditPanel.this.mainPanel.getComponentCount(); ++i){
						c = ((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
								EditPanel.this.mainPanel.getComponent(i));
						if (c.gridy > index){
							++c.gridy;
							((GridBagLayout)EditPanel.this.mainPanel.getLayout()).setConstraints(EditPanel.this.mainPanel.getComponent(i), c);
						}
					}
					EditPanel.this.mainPanel.add(new ChapterPanel(b.getChapter(index)), new GridBagConstraints(0, 1 + index, 1, 1, 1.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					EditPanel.this.mainPanel.revalidate();
				}
			});
		}
	}
	
	public EditPanel(Book b, Library l) {
		this.b = b;
		this.l = l;
		result = false;
		init();
	}
	
	private void init(){
				label1 = new JLabel();
				nameText = new JTextField(b.getName());
				label2 = new JLabel();
				authorText = new JTextField(b.getAuthor());
				label3 = new JLabel();
				ISBNText = new JTextField(b.getISBN());
				mainScrollPane = new JScrollPane();
				mainPanel = new JPanel();
				firstNewChapterButton = new JButton();
				buttonPanel = new JPanel();
				saveButton = new JButton();
				refreshButton = new JButton();
				pictureDialog = new JFileChooser();
				ImageFilter ff = new ImageFilter();
				pictureDialog.addChoosableFileFilter(ff);
				pictureDialog.setFileFilter(ff);
				pictureDialog.setAcceptAllFileFilterUsed(false);
				pictureDialog.setAccessory(new ImagePreview(pictureDialog));
				
				setLayout(new GridBagLayout());
				((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
				((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

				//---- label1 ----
				label1.setText("Titlu:");
				add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(20, 20, 10, 10), 0, 0));

				//---- nameText ----
				nameText.setColumns(30);
				add(nameText, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(20, 0, 10, 0), 0, 0));
				
				nameText.setMinimumSize(new Dimension(340, 19));
				//---- label2 ----
				label2.setText("Autor:");
				add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 20, 10, 10), 0, 0));

				//---- authorText ----
				authorText.setColumns(30);
				add(authorText, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 10, 0), 0, 0));
				authorText.setMinimumSize(new Dimension(340, 19));
				//---- label3 ----
				label3.setText("ISBN:");
				add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 20, 10, 10), 0, 0));

				//---- ISBNText ----
				ISBNText.setColumns(30);
				add(ISBNText, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 10, 0), 0, 0));
				ISBNText.setMinimumSize(new Dimension(340, 19));
				//======== mainScrollPane ========
					//======== mainPanel ========
						mainPanel.setLayout(new GridBagLayout());
						((GridBagLayout)mainPanel.getLayout()).columnWidths = new int[] {0, 0};
						((GridBagLayout)mainPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)mainPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
						((GridBagLayout)mainPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

						//---- firstNewChapterButton ----
						firstNewChapterButton.setText("Captiol Nou");
						firstNewChapterButton.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
						mainPanel.add(firstNewChapterButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE,
							new Insets(5, 0, 10, 0), 0, 0));
						firstNewChapterButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int index = 0;
								GridBagConstraints c = null;
								b.add(index, new Chapter());
								for (int i = 0; i < EditPanel.this.mainPanel.getComponentCount(); ++i){
									c = ((GridBagLayout)EditPanel.this.mainPanel.getLayout()).getConstraints(
											EditPanel.this.mainPanel.getComponent(i));
									if (c.gridy > index){
										++c.gridy;
										((GridBagLayout)EditPanel.this.mainPanel.getLayout()).setConstraints(EditPanel.this.mainPanel.getComponent(i), c);
									}
								}
								
								EditPanel.this.mainPanel.add(new ChapterPanel(b.getChapter(index)), new GridBagConstraints(0, 1 + index, 1, 1, 1.0, 0.0,
										GridBagConstraints.CENTER, GridBagConstraints.BOTH,
										new Insets(0, 0, 0, 0), 0, 0));
								EditPanel.this.mainPanel.revalidate();
							}
						});
						for (int i = 0; i < b.size(); ++i)
							mainPanel.add(new ChapterPanel(b.getChapter(i)), new GridBagConstraints(0, 1 + i, 1, 1, 1.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
					mainScrollPane.setViewportView(mainPanel);
				add(mainScrollPane, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 10, 0), 0, 0));

				//======== buttonPanel ========
					buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

					//---- saveButton ----
					saveButton.setText("Salveaza");
					saveButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							save();
							l.saveToFile("library.xml");
						}
					});
					buttonPanel.add(saveButton);

					//---- refreshButton ----
					refreshButton.setText("Reincarca");
					buttonPanel.add(refreshButton);
				add(buttonPanel, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
	}
	
	public void save(){
		Component[] comps = mainPanel.getComponents();
		for (int i = 0; i < comps.length; ++i)
			if (comps[i] instanceof ChapterPanel){
				((ChapterPanel)comps[i]).ch.setName(((ChapterPanel)comps[i]).chapterTitleText.getText());
				((ChapterPanel)comps[i]).save();
			}
		b.setAuthor(authorText.getText());
		b.setName(nameText.getText());
		b.setISBN(ISBNText.getText());
	}
	
	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Andrei Datcu
	private JLabel label1;
	private JTextField nameText;
	private JLabel label2;
	private JTextField authorText;
	private JLabel label3;
	private JTextField ISBNText;
	private JScrollPane mainScrollPane;
	private JPanel mainPanel;
	private JButton firstNewChapterButton;
	private JPanel chapterPanel;
	private JButton deleteChapterButton;
	private JLabel chapterTitleLabel;
	private JScrollPane paragraphsScrollPane;
	private JPanel paragraphsPanel;
	private JButton firstNewParagraphButton;
	private JPanel paragraphPanel;
	private JButton deleteParagraphButton;
	private JScrollPane paragraphScrollPane;
	private JTextArea paragraphText;
	private JButton newParagrafButton;
	private JButton newChapterButton;
	private JPanel buttonPanel;
	private JButton saveButton;
	private JButton refreshButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private Book b;
	private Library l;
	private JFileChooser pictureDialog;
	public boolean result;
}
