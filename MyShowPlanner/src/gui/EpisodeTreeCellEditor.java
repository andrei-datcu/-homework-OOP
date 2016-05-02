package gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import structures.Episode;
import structures.Show;

class EpisodeTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

	  EpisodeTreeCellRenderer renderer = new EpisodeTreeCellRenderer();

	  ChangeEvent changeEvent = null;

	  JTree tree;
	  Show show;

	  public EpisodeTreeCellEditor(JTree tree, Show show) {
	    this.tree = tree;
	    this.show = show;
	  }

	  public Object getCellEditorValue() {
	    Episode checkBoxNode = renderer.getEpisode();
	    checkBoxNode.setSeen(renderer.getLeafRenderer().isSelected());
	    show.setEpisodeWatched(checkBoxNode.getNumber(), renderer.getLeafRenderer().isSelected());
	    return checkBoxNode;
	  }

	  public boolean isCellEditable(EventObject event) {
	    boolean returnValue = false;
	    if (event instanceof MouseEvent) {
	      MouseEvent mouseEvent = (MouseEvent) event;
	      TreePath path = tree.getPathForLocation(mouseEvent.getX(),
	          mouseEvent.getY());
	      if (path != null) {
	        Object node = path.getLastPathComponent();
	        if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
	          DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
	          Object userObject = treeNode.getUserObject();
	          returnValue = ((treeNode.isLeaf()) && (userObject instanceof Episode));
	        }
	      }
	    }
	    return returnValue;
	  }

	  public Component getTreeCellEditorComponent(JTree tree, Object value,
	      boolean selected, boolean expanded, boolean leaf, int row) {

	    Component editor = renderer.getTreeCellRendererComponent(tree, value,
	        true, expanded, leaf, row, true);

	    // editor always selected / focused
	    ItemListener itemListener = new ItemListener() {
	      public void itemStateChanged(ItemEvent itemEvent) {
	        if (stopCellEditing()) {
	          fireEditingStopped();
	        }
	      }
	    };
	    if (editor instanceof JCheckBox) {
	      ((JCheckBox) editor).addItemListener(itemListener);
	    }

	    return editor;
	  }
	}
