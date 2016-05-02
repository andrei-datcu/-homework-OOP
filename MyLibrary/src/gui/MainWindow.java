/*
 * Created by JFormDesigner on Thu Dec 27 17:12:51 EET 2012
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import element.Book;
import element.Library;
import element.Paragraph;

/**
 * @author Andrei Datcu
 */
public class MainWindow extends JFrame {
	public MainWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("My Library");
		File f = new File("library.xml");
		if (f.exists() && f.isFile())
			initComponentsLibrary();
		else
			initComponentsNoLibrary();
	}
	
	private void initComponentsNoLibrary(){
		titleLabel = new JLabel();
		newLibraryPanel = new JPanel();
		label2 = new JLabel();
		button1 = new JButton();
		booksPanel = new JPanel();
		maxChapterPanel = new JPanel();
		textField1 = new JTextField();
		label1 = new JLabel();
		maxParagraphScrollPane = new JScrollPane();
		textArea1 = new JTextArea();
		button2 = new JButton();
		editBookPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		list1 = new JList();
		button3 = new JButton();
		button4 = new JButton();

		//======== this ========
		setMinimumSize(new Dimension(500, 500));
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

		//---- titleLabel ----
		titleLabel.setText("My Library");
		contentPane.add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 0), 0, 40));

		//======== newLibraryPanel ========
		{
			newLibraryPanel.setBorder(new TitledBorder("Biblioteca"));
			newLibraryPanel.setLayout(new GridBagLayout());
			((GridBagLayout)newLibraryPanel.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)newLibraryPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)newLibraryPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
			((GridBagLayout)newLibraryPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//---- label2 ----
			label2.setText("Nu exista nicio biblioteca. Va rugam sa creati una!");
			newLibraryPanel.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 20, 5, 0), 0, 10));

			//---- button1 ----
			button1.setText("Biblioteca noua");
			newLibraryPanel.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
				new Insets(0, 20, 0, 0), 0, 0));
		}
		contentPane.add(newLibraryPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 0), 0, 0));
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				l = new Library();
				l.saveToFile("library.xml");
				getContentPane().removeAll();
				initComponentsLibrary();
				repaint();
			}
		});
	}
	
	private void initComponentsLibrary(){
		l = new Library("library.xml");
		titleLabel = new JLabel();
		booksPanel = new JPanel();
		maxChapterPanel = new JPanel();
		textField1 = new JTextField();
		label1 = new JLabel();
		maxParagraphScrollPane = new JScrollPane();
		textArea1 = new JTextArea();
		button2 = new JButton();
		editBookPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		list1 = new JList();
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		button3 = new JButton();
		button4 = new JButton();

		//======== this ========
		setMinimumSize(new Dimension(500, 500));
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0};

		//---- titleLabel ----
		titleLabel.setText("My Library");
		contentPane.add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 0), 0, 40));

		//======== booksPanel ========
		{
			booksPanel.setBorder(new TitledBorder("Carti"));
			booksPanel.setLayout(new GridBagLayout());
			((GridBagLayout)booksPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
			((GridBagLayout)booksPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)booksPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
			((GridBagLayout)booksPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

			//======== maxChapterPanel ========
			{
				maxChapterPanel.setBorder(new TitledBorder(null, "Cea mai mare carte", TitledBorder.LEFT, TitledBorder.TOP));
				maxChapterPanel.setMinimumSize(new Dimension(150, 72));
				maxChapterPanel.setPreferredSize(new Dimension(170, 72));
				maxChapterPanel.setLayout(new GridBagLayout());
				((GridBagLayout)maxChapterPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)maxChapterPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)maxChapterPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
				((GridBagLayout)maxChapterPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//---- textField1 ----
				textField1.setEditable(false);
				maxChapterPanel.add(textField1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 10, 0), 0, 0));

				//---- label1 ----
				maxChapterPanel.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 5, 0, 0), 0, 0));
			}
			booksPanel.add(maxChapterPanel, new GridBagConstraints(0, 0, 1, 1, 0.3, 0.2,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 15, 15), 0, 0));

			//======== maxParagraphScrollPane ========
			{
				maxParagraphScrollPane.setViewportBorder(new TitledBorder("Cel mai lung paragraf"));
				maxParagraphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//---- textArea1 ----
				//textArea1.setPreferredSize(new Dimension(170, 20));
				textArea1.setMaximumSize(new Dimension(300, 300));
				textArea1.setEditable(false);
				textArea1.setLineWrap(true);
				textArea1.setWrapStyleWord(true);
				
				maxParagraphScrollPane.setViewportView(textArea1);
			}
			booksPanel.add(maxParagraphScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.7, 0.2,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 15, 0), 0, 0));

			//---- button2 ----
			button2.setText("Carte noua");
			button2.setIcon(new ImageIcon("icons" + File.separator + "newbook.png"));
			button2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					int oldSize = l.size();
					new NewBookDialog(MainWindow.this, l).setVisible(true);
					if (l.size() != oldSize)
						populate();
				}
			});
			booksPanel.add(button2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 15), 0, 0));

			//======== editBookPanel ========
			{
				editBookPanel.setBorder(new TitledBorder("Editeaza carte"));
				editBookPanel.setLayout(new GridBagLayout());
				((GridBagLayout)editBookPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
				((GridBagLayout)editBookPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)editBookPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
				((GridBagLayout)editBookPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

				//======== scrollPane1 ========
					scrollPane1.setViewportView(list1);
					list1.addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if (list1.getValueIsAdjusting())
								return;
							if (list1.getSelectedIndex() == -1){
								button3.setEnabled(false);
								button4.setEnabled(false);
							}
							else{
								button3.setEnabled(true);
								button4.setEnabled(true);
							}
						}
					});
				editBookPanel.add(scrollPane1, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 10, 0), 0, 0));

				//---- button3 ----
				button3.setText("<html>Editeaza/<br>Vizualizeaza");
				button3.setIcon(new ImageIcon("icons" + File.separator + "edit.png"));
				button3.setEnabled(false);
				editBookPanel.add(button3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 10), 0, 0));

				//---- button4 ----
				button4.setText("Sterge");
				button4.setEnabled(false);
				button4.setIcon(new ImageIcon("icons" + File.separator + "delete.png"));
				editBookPanel.add(button4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			booksPanel.add(editBookPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.8,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		}
		contentPane.add(booksPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		populate();
		
		button3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				EditViewDialog evd = new EditViewDialog(MainWindow.this, l.get(list1.getSelectedIndex()), l);
				evd.showDialog();
				l = new Library("library.xml");
			}
		});
		
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				l.remove(list1.getSelectedIndex());
				l.saveToFile("library.xml");
				populate();
			}
		});
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Andrei Datcu
	private JLabel titleLabel;
	private JPanel newLibraryPanel;
	private JLabel label2;
	private JButton button1;
	private JPanel booksPanel;
	private JPanel maxChapterPanel;
	private JTextField textField1;
	private JLabel label1;
	private JScrollPane maxParagraphScrollPane;
	private JTextArea textArea1;
	private JButton button2;
	private JPanel editBookPanel;
	private JScrollPane scrollPane1;
	private JList list1;
	private JButton button3;
	private JButton button4;
	private Library l;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	private void populate(){
		Book b = l.getMaxChapterBook();
		if (b != null){
			textField1.setText(b.getName());
			label1.setText("Are " + b.size() + (b.size() != 1 ? " capitole" : " captiol"));
		}
		else{
			textField1.setText("");
			label1.setText("");
		}
		Paragraph p = l.getMaxParagraph();
		if (p != null)
			textArea1.setText(p.toString());
		else
			textArea1.setText("");
		list1.setListData(l.titles());
	}
	
	public static void main(String args[]){
		MainWindow mw = new MainWindow();
		mw.setVisible(true);
	}
}
