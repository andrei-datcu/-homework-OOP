package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import structures.SearchResult;

class ShowSearchTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Nume", "Tara", "Status"};
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
    
    public void setFromArray(ArrayList<SearchResult> values){
    	data = new Object[values.size()][4];
    	SearchResult show = null;
    	for (int i = 0; i < values.size(); ++i){
    		show = values.get(i);
    		data[i][0] = show.getShowId();
    		data[i][1] = show.getName();
    		data[i][2] = show.getCountry();
    		data[i][3] = show.getStatus();
    	}
    	fireTableDataChanged();
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}