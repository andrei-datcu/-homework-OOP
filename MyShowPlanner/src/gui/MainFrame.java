package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import structures.Show;
import structures.ShowSet;

public class MainFrame extends JFrame{
	private JTable showTable;
	private JButton addButton, deleteButton, detailsButton;
	private ShowSet shows;
	private TableColumnAdjuster tc;
	public MainFrame(){
		shows = new ShowSet("shows.xml");
		setTitle("TV Shows Scheduler");
		setLayout(new GridBagLayout());
		showTable = new JTable(new ShowTableModel());
		showTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		showTable.setCellSelectionEnabled(false);
		showTable.setRowSelectionAllowed(true);
		showTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (showTable.getSelectionModel().getValueIsAdjusting() == true)
						return;
				if (showTable.getSelectedRowCount() > 0){
					detailsButton.setEnabled(true);
					deleteButton.setEnabled(true);
				}
				else{
					detailsButton.setEnabled(false);
					deleteButton.setEnabled(false);
				}
			}
		});
		tc = new TableColumnAdjuster(showTable);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(showTable);
		add(scroll, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));
		
		addButton = new JButton("Adauga");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddShowDialog addDialog = new AddShowDialog(MainFrame.this);
				ArrayList<Show> addedShows = addDialog.showDialog();
				if (addedShows != null){
					boolean ok = false;
					for (int i = 0; i < addedShows.size(); ++i)
						ok |= shows.add(addedShows.get(i));
					if (ok){
						populateTable();
						shows.saveToFile("shows.xml");
					}
				}
			}
		});
		
		deleteButton = new JButton("Sterge");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shows.remove(shows.getById((Integer)showTable.getValueAt(showTable.getSelectedRow(), 0)));
				shows.saveToFile("shows.xml");
				populateTable();
			}
		});

		
		detailsButton = new JButton("Detalii");
		detailsButton.setEnabled(false);
		detailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DetailsDialog(MainFrame.this, shows.getById((Integer)showTable.getValueAt(showTable.getSelectedRow(), 0))).setVisible(true);
				shows.saveToFile("shows.xml");
			}
		});
		
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelButton.add(addButton);
		panelButton.add(deleteButton);
		panelButton.add(detailsButton);
		add(panelButton, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 20, 20, 20), 0, 0));
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		populateTable();
	}
	
	public static void main(String args[]){
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}
	
	private void populateTable(){
		((ShowTableModel)showTable.getModel()).setFromNavigableSet(shows.descendingSet());
		tc.adjustColumns();		
	}
}
