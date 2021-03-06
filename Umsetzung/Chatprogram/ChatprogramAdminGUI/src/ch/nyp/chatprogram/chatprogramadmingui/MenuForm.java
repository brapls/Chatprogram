package ch.nyp.chatprogram.chatprogramadmingui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuForm extends JPanel {

	/**
	 * Create the panel.
	 */
	public MenuForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{62, 192, 0};
		gridBagLayout.rowHeights = new int[]{50, 20, 23, 23, 23, 23, 35, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label = new JLabel("Chatprogramm Admin GUI");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		JButton btnModifyDeleteAccount = new JButton("Account bearbeiten oder l\u00F6schen");
		btnModifyDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new AccountSelectionForm());
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		GridBagConstraints gbc_btnModifyDeleteAccount = new GridBagConstraints();
		gbc_btnModifyDeleteAccount.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnModifyDeleteAccount.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifyDeleteAccount.gridx = 1;
		gbc_btnModifyDeleteAccount.gridy = 2;
		add(btnModifyDeleteAccount, gbc_btnModifyDeleteAccount);
		
		JButton btnAddAccount = new JButton("Account erfassen");
		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new AccountDetailsForm(null, MenuForm.class));
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		GridBagConstraints gbc_btnAddAccount = new GridBagConstraints();
		gbc_btnAddAccount.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_btnAddAccount.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddAccount.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddAccount.gridx = 1;
		gbc_btnAddAccount.gridy = 3;
		add(btnAddAccount, gbc_btnAddAccount);
		
		JButton btnDeleteChat = new JButton("Chat l\u00F6schen");
		GridBagConstraints gbc_btnDeleteChat = new GridBagConstraints();
		gbc_btnDeleteChat.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteChat.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteChat.gridx = 1;
		gbc_btnDeleteChat.gridy = 4;
		add(btnDeleteChat, gbc_btnDeleteChat);
		
		JButton btnStopSession = new JButton("Session stopen");
		btnStopSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new SessionForm());
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		GridBagConstraints gbc_btnStopSession = new GridBagConstraints();
		gbc_btnStopSession.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStopSession.insets = new Insets(0, 0, 5, 0);
		gbc_btnStopSession.gridx = 1;
		gbc_btnStopSession.gridy = 5;
		add(btnStopSession, gbc_btnStopSession);
		
		JButton btnLogOut = new JButton("Ausloggen");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new LoginForm());
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogOut.gridx = 1;
		gbc_btnLogOut.gridy = 7;
		add(btnLogOut, gbc_btnLogOut);
		
	}

}
