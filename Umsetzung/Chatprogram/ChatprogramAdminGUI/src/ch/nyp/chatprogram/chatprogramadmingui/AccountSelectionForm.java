package ch.nyp.chatprogram.chatprogramadmingui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import ch.nyp.chatprogram.common.UserApplication;
import ch.nyp.chatprogram.common.UserSession;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AccountSelectionForm extends JPanel {
	private JTable jtable;
	private JScrollPane scrollPnl_1;
	public AccountSelectionForm() {
		JLabel lblNewLabel = new JLabel("Accounts");
		LoadSessionTable();
		JButton btnDeleteAccount = new JButton("Account l\u00F6schen");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = jtable.getSelectedRow(); 
				if(selectedRow != -1) { // -1 = Keine Reihe ist ausgew�hlt
					UserApplication.DeleteAccount((int)jtable.getValueAt(selectedRow, 0));
					DefaultTableModel model = (DefaultTableModel) jtable.getModel();
					model.removeRow(selectedRow);
					//model.removeRow(jtable.getSelectedRow());
				}
			}
		});
		
		JButton btnReturn = new JButton("Zur\u00FCck");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new MenuForm());
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		
		JButton btnModifyAccount = new JButton("Account bearbeiten");
		btnModifyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = jtable.getSelectedRow(); 
				if(selectedRow != -1) { // -1 = Keine Reihe ist ausgew�hlt
					Main.frame.getContentPane().removeAll();
					Main.frame.getContentPane().add(new AccountDetailsForm(UserApplication.GetUserById((int) jtable.getValueAt(jtable.getSelectedRow(), 0)), AccountSelectionForm.class));
					Main.frame.getContentPane().revalidate(); 
					Main.frame.getContentPane().repaint();
				}
				else {
					JOptionPane.showMessageDialog(null, "Sie m�ssen zuerst einen Eintrag ausw�hlen");
				}
			}
		});
		
		JButton btnNeuerAccountErstellen = new JButton("Neuer Account erstellen");
		btnNeuerAccountErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.frame.getContentPane().removeAll();
				Main.frame.getContentPane().add(new AccountDetailsForm(null, AccountSelectionForm.class));
				Main.frame.getContentPane().revalidate(); 
				Main.frame.getContentPane().repaint();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(228)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPnl_1, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnDeleteAccount, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnModifyAccount, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNeuerAccountErstellen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
									.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))))
					.addGap(73))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel)
					.addGap(5)
					.addComponent(scrollPnl_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnDeleteAccount)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnModifyAccount)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNeuerAccountErstellen)
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
	
	//Ladet die Daten f�r die Tabelle neu
	private void LoadSessionTable() {
		List<UserApplication> accountsList = UserApplication.GetAllAccounts();
		String[] columnName = {"User ID","Username", "Passwort", "Anzeige Name", "Admin?"};
		Object[][] data = new Object[accountsList.size()][columnName.length];
		for(int row = 0; row<accountsList.size(); row++) {
			UserApplication userSession = accountsList.get(row);
			data[row] = new Object[]{userSession.GetUserId() ,userSession.GetUsername(), userSession.GetPassword(), userSession.GetdisplayName(), userSession.GetIsAdmin()};
		}
		DefaultTableModel dtm = new DefaultTableModel(data, columnName)
	    {
		      public Class<?> getColumnClass(int column)
		      {
		        switch(column)
		        {
		        case 4:
		          return Boolean.class;
		        case 1:
		        	return int.class;
		        default:
		            return String.class;
		        }
		      }
		    };
		jtable = new JTable(dtm);
		jtable.setFillsViewportHeight(true);
		scrollPnl_1 = new JScrollPane(jtable);
		
	}
	
}
