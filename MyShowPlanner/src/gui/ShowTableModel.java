package gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NavigableSet;

import javax.swing.table.AbstractTableModel;

import structures.Show;
import urlreaders.EpisodesURLReader;
import urlreaders.EpisodesXmlParser;

public class ShowTableModel extends AbstractTableModel{
    private String[] columnNames = {"ID", "Nume", "Tara", "Status", "Urmatorul episod"};
    private Object[][] data = new Object[][]{};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public void setFromNavigableSet(NavigableSet<Show> values){
    	data = new Object[values.size()][5];
    	Show show = null;
    	Iterator<Show> it = values.descendingIterator();
    	Date date;
    	for (int i = 0; it.hasNext(); ++i){
    		show = it.next();
    		data[i][0] = show.getShowId();
    		data[i][1] = show.getName();
    		data[i][2] = show.getCountry();
    		data[i][3] = show.getStatus();
			try {
				date = EpisodesXmlParser.nextEpisode(EpisodesURLReader.getLines(show.getShowId()));
				data[i][4] = date != null ? DateFormat.getDateInstance().format(date) : 
					show.getStatus().toUpperCase().contains("RETURN") ? new String("Necunoscut"): new String("Terminat");
			} catch (MalformedURLException e) {
				data[i][4] = "Eroare - verificati conexiunea";
			} catch (IOException e) {
				data[i][4] = "Eroare - verificati conexiunea";
			} catch (Exception e) {
				data[i][4] = "Eroare";
			}
    	}
    	fireTableDataChanged();
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
