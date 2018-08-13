package ch.nyp.chatprogram.chatprogramadmingui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JTable;

import ch.nyp.chatprogram.common.UserSession;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class SessionForm  extends JPanel {
	private JTable jtable;
	private JScrollPane scrollPnl_1;
	public SessionForm() {
		JLabel lblNewLabel = new JLabel("Sessions");
		LoadSessionTable();
		JButton btnStopSelectedSession = new JButton("Ausgew\u00E4hlte Session stopen");
		btnStopSelectedSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = jtable.getSelectedRow(); 
				if(selectedRow != -1) { // -1 = Keine Reihe ist ausgew�hlt
					UserSession.DestroySession((int)jtable.getValueAt(selectedRow, 0));
					DefaultTableModel model = (DefaultTableModel) jtable.getModel();
					model.removeRow(selectedRow);
					//model.removeRow(jtable.getSelectedRow());
				}
				else {
					JOptionPane.showMessageDialog(null, "Sie m�ssen zuerst einen Eintrag ausw�hlen");
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
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnStopSelectedSession)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnReturn))
								.addComponent(scrollPnl_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel)
					.addGap(5)
					.addComponent(scrollPnl_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStopSelectedSession)
						.addComponent(btnReturn))
					.addContainerGap(138, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
	
	//Ladet die Daten f�r die Tabelle neu
	private void LoadSessionTable() {
		List<UserSession> sessionList = UserSession.GetAllSessions();
		String[] columnName = {"SessionId","Username", "IP", "Zuletzt Gepingt", "Token"};
		Object[][] data = new Object[sessionList.size()][columnName.length];
		for(int row = 0; row<sessionList.size(); row++) {
			UserSession userSession = sessionList.get(row);
			data[row] = new Object[]{userSession.GetSessionId() ,userSession.GetUser().GetUsername(), userSession.GetSessionIP(), userSession.GetLastPing().toString(), userSession.GetToken()};
		}
		DefaultTableModel dtm = new DefaultTableModel(data, columnName);
		jtable = new JTable(dtm);
		jtable.setFillsViewportHeight(true);
		scrollPnl_1 = new JScrollPane(jtable);
		
	}
}
