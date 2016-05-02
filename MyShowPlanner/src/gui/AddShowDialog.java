package gui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import structures.SearchResult;
import structures.Show;
import urlreaders.SearchResultXmlParser;
import urlreaders.ShowSearchURLReader;

public class AddShowDialog extends JDialog{
	
	private JTable resultsTable;
	private JTextField searchTextField;
	private JButton addButton, searchButton, cancelButton;
	private JLabel searchLabel, instructionsLabel;
	private ArrayList<SearchResult> searchResults;
	private ArrayList<Show> showSearchResults;
	private TableColumnAdjuster tc;
	
	AddShowDialog(JFrame parent){
		super(parent);
		setModal(true);
		setTitle("Adauga show-uri");
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(800, 800));
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				showSearchResults = null;
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setLayout(new GridBagLayout());
		
		searchLabel = new JLabel("Nume serial:");
		add(searchLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(20, 20, 0, 0), 0, 0));

		searchTextField = new JTextField(30);
		add(searchTextField, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(20, 20, 0, 0), 0, 0));
		
		searchButton = new JButton("Cauta");
		add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(20, 20, 0, 0), 0, 0));

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (searchTextField.getText().compareTo("") == 0)
					return;
				try {
					searchResults = SearchResultXmlParser.parse(ShowSearchURLReader.getLines(searchTextField.getText()));
					((ShowSearchTableModel)resultsTable.getModel()).setFromArray(searchResults);
					tc.adjustColumns();
					addButton.setEnabled(false);
				} catch (MalformedURLException e) {
					JOptionPane.showMessageDialog(AddShowDialog.this,
						    "URL prost formatat:  " + e.getMessage(),
						    "Eroare",
						    JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(AddShowDialog.this,
						    "Nu s-a putut deschide o conexiune cu linkul dorit",
						    "Eroare",
						    JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(AddShowDialog.this,
						    e.getMessage(),
						    "Eroare",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		instructionsLabel = new JLabel("Selectati din tabel show-urile dorite folosind ctrl+mouse si apoi apasati butonul Adauga");
		add(instructionsLabel, new GridBagConstraints(0, 1, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 0, 20), 0, 0));
		
		resultsTable = new JTable(new ShowSearchTableModel());
		resultsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		resultsTable.setCellSelectionEnabled(false);
		resultsTable.setRowSelectionAllowed(true);
		resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (resultsTable.getSelectionModel().getValueIsAdjusting() == true)
						return;
				if (resultsTable.getSelectedRowCount() > 0)
					addButton.setEnabled(true);
				else
					addButton.setEnabled(false);
			}
		});
		tc = new TableColumnAdjuster(resultsTable);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(resultsTable);
		add(scroll, new GridBagConstraints(0, 2, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 0, 20), 0, 0));
		
		addButton = new JButton("Adauga");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSearchResults = new ArrayList<Show>();
				int [] selectedRows = resultsTable.getSelectedRows();
				for (int i = 0; i < selectedRows.length; ++i)
					showSearchResults.add(new Show(searchResults.get(selectedRows[i])));
				AddShowDialog.this.setVisible(false);
			}
		});
		addButton.setEnabled(false);
		
		cancelButton = new JButton("Renunta");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSearchResults = null;
				AddShowDialog.this.setVisible(false);
			}
		});
		JPanel bottomButtonPanel = new JPanel();
		bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomButtonPanel.add(addButton);
		bottomButtonPanel.add(cancelButton);
		add(bottomButtonPanel, new GridBagConstraints(0, 3, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 20, 20, 20), 0, 0));
	}
	
	public ArrayList<Show> showDialog(){
		this.setVisible(true);
		return showSearchResults;
	}

}
