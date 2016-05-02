package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import structures.Episode;
import structures.Show;
import urlreaders.DetailedShowInfoURLReader;
import urlreaders.EpisodesXmlParser;

public class DetailsDialog extends JDialog{
	private JTree episodesTree;
	private Show show;
	private JLabel instructionsLabel;
	
	private void setWatchedShows(DefaultMutableTreeNode seasons){
		if (seasons.getUserObject() instanceof Episode){
			Episode ep = (Episode)seasons.getUserObject();
			ep.setSeen(show.isEpisodeWatched(ep.getNumber()));
		}
		for (int i = 0; i < seasons.getChildCount(); ++i)
			setWatchedShows((DefaultMutableTreeNode) seasons.getChildAt(i));
	}
	
	public DetailsDialog(JFrame parent, Show show){
		super(parent);
		this.show = show;
		setLayout(new GridBagLayout());
		String xml;
		try {
			xml = DetailedShowInfoURLReader.getLines(show.getShowId());
			add(new DetailedShowInfoPanel(xml), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
			setTitle("Detalii Show");
			setModal(true);
			setMinimumSize(new Dimension(800, 600));
			setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			this.setLocationByPlatform(true);
			instructionsLabel = new JLabel("<html>Lista Episoadelor:<br>Daca ati vizionat un episod, va rugam bifati in casuta care ii precede.</html");
			add(instructionsLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 20, 10, 20), 0, 0));
			DefaultMutableTreeNode seasons = EpisodesXmlParser.parse((xml));
			setWatchedShows(seasons);
			episodesTree = new JTree(seasons);
			episodesTree.setCellRenderer(new EpisodeTreeCellRenderer());
			episodesTree.setCellEditor(new EpisodeTreeCellEditor(episodesTree, show));
			episodesTree.setEditable(true);
			episodesTree.setRootVisible(false);
			episodesTree.setShowsRootHandles(true);
			JScrollPane scroll = new JScrollPane();
			scroll.setViewportView(episodesTree);
			add(scroll, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH,
					new Insets(20, 20, 20, 20), 0, 0));
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(parent,
				    "URL prost formatat:  " + e.getMessage(),
				    "Eroare",
				    JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent,
				    "Nu s-a putut deschide o conexiune cu linkul dorit",
				    "Eroare",
				    JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent,
				    e.getMessage(),
				    "Eroare",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
