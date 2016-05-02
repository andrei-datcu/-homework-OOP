package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import structures.Episode;

public class EpisodeTreeCellRenderer implements TreeCellRenderer {
	  private JCheckBox leafRenderer = new JCheckBox();
	  private Episode ep;
	  private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();

	  Color selectionBorderColor, selectionForeground, selectionBackground,
	      textForeground, textBackground;

	  protected JCheckBox getLeafRenderer() {
	    return leafRenderer;
	  }
	  
	  public Episode getEpisode(){
		  return ep;
	  }

	  public EpisodeTreeCellRenderer() {
	    Font fontValue;
	    fontValue = UIManager.getFont("Tree.font");
	    nonLeafRenderer.setOpenIcon(null);
	    nonLeafRenderer.setClosedIcon(null);
	    if (fontValue != null) {
	      leafRenderer.setFont(fontValue);
	    }
	    Boolean booleanValue = (Boolean) UIManager
	        .get("Tree.drawsFocusBorderAroundIcon");
	    leafRenderer.setFocusPainted((booleanValue != null)
	        && (booleanValue.booleanValue()));

	    selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
	    selectionForeground = UIManager.getColor("Tree.selectionForeground");
	    selectionBackground = UIManager.getColor("Tree.selectionBackground");
	    textForeground = UIManager.getColor("Tree.textForeground");
	    textBackground = UIManager.getColor("Tree.textBackground");
	  }

	  public Component getTreeCellRendererComponent(JTree tree, Object value,
	      boolean selected, boolean expanded, boolean leaf, int row,
	      boolean hasFocus) {

	    Component returnValue;
	    if (leaf) {

	      String stringValue = tree.convertValueToText(value, selected,
	          expanded, leaf, row, false);
	      leafRenderer.setText(stringValue);
	      leafRenderer.setSelected(false);

	      leafRenderer.setEnabled(tree.isEnabled());
	      
	      if (selected) {
	        leafRenderer.setForeground(selectionForeground);
	        leafRenderer.setBackground(selectionBackground);
	      } else {
	        leafRenderer.setForeground(textForeground);
	        leafRenderer.setBackground(textBackground);
	      }

	      if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
	        Object userObject = ((DefaultMutableTreeNode) value)
	            .getUserObject();
	        if (userObject instanceof Episode) {
	          ep = (Episode) userObject;
	          leafRenderer.setText(ep.toString());
	          leafRenderer.setSelected(ep.isSeen());
	        }
	      }
	      returnValue = leafRenderer;
	    } else {
	      returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree,
	          value, selected, expanded, leaf, row, hasFocus);
	    }
	    return returnValue;
	  }
	}

