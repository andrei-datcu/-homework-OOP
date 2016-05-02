/*
 * Created by JFormDesigner on Sun Dec 30 13:34:10 EET 2012
 */

package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import element.Book;
import element.Library;

/**
 * @author Andrei Datcu
 */
public class EditViewDialog extends JDialog {
	public EditViewDialog(JFrame owner, Book b, Library l) {
		super(owner);
		this.b = b;
		this.l = l;
		this.setPreferredSize(new Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		initComponents();
	}
	
	public boolean showDialog(){
		this.setVisible(true);
		return authorPanel.result;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Andrei Datcu
		tabbedPane1 = new JTabbedPane();
		tabbedPane1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane1.getTabCount() < 2)
					return;
				if (tabbedPane1.getSelectedIndex() == 0){
					authorPanel.save();
					publishPanel.paintPages();
				}
			}
		});
		//======== this ========
		setModal(true);
		setTitle("Editarea/Vizualizarea cartii");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};
		EditViewPanels ev = new EditViewPanels(b, l);
		authorPanel = (EditPanel)ev.authorPanel();
		publishPanel = (ViewPanel)ev.publishPanel();
		//======== tabbedPane1 ========
			tabbedPane1.addTab("Publish mode", publishPanel);

			tabbedPane1.addTab("Author mode", authorPanel);
		contentPane.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Andrei Datcu
	private JTabbedPane tabbedPane1;
	private EditPanel authorPanel;
	private ViewPanel publishPanel;
	private Book b;
	private Library l;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
