/*
 * Created by JFormDesigner on Fri Dec 28 22:48:04 EET 2012
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import element.Book;
import element.Library;

/**
 * @author Andrei Datcu
 */
public class NewBookDialog extends JDialog {
	public NewBookDialog(JFrame owner, Library l) {
		super(owner);
		this.l = l;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Andrei Datcu
		nameLabel = new JLabel();
		nameText = new JTextField();
		authorLabel = new JLabel();
		authorText = new JTextField();
		ISBNLabel = new JLabel();
		ISBNText = new JTextField();
		buttonsPanel = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setModal(true);
		setResizable(false);
		setTitle("Adauga o carte noua...");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- nameLabel ----
		nameLabel.setText("Nume:");
		contentPane.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(20, 10, 15, 10), 0, 0));
		contentPane.add(nameText, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 15, 0), 0, 0));

		//---- authorLabel ----
		authorLabel.setText("Autor:");
		contentPane.add(authorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 10, 15, 10), 0, 0));
		contentPane.add(authorText, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 15, 0), 0, 0));

		//---- ISBNLabel ----
		ISBNLabel.setText("ISBN:");
		contentPane.add(ISBNLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 10, 15, 10), 0, 0));

		//---- ISBNText ----
		ISBNText.setColumns(30);
		contentPane.add(ISBNText, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 15, 0), 0, 0));

		//======== buttonsPanel ========
		{

			buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

			//---- okButton ----
			okButton.setText("OK");
			okButton.setPreferredSize(new Dimension(65, 22));
			okButton.setMaximumSize(new Dimension(65, 22));
			okButton.setMinimumSize(new Dimension(65, 22));
			buttonsPanel.add(okButton);

			//---- cancelButton ----
			cancelButton.setText("Renunta");
			buttonsPanel.add(cancelButton);
		}
		contentPane.add(buttonsPanel, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (authorText.getText().compareTo("") == 0 || nameText.getText().compareTo("") == 0
						|| ISBNText.getText().compareTo("") == 0){
					JOptionPane.showMessageDialog(NewBookDialog.this,
						    "Toate campurile trebuie completate.",
						    "Eroare",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				Book b = new Book(authorText.getText(), nameText.getText(), ISBNText.getText());
				l.add(b);
				setVisible(false);
				l.saveToFile("library.xml");
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Andrei Datcu
	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel authorLabel;
	private JTextField authorText;
	private JLabel ISBNLabel;
	private JTextField ISBNText;
	private JPanel buttonsPanel;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	private Library l;
}
