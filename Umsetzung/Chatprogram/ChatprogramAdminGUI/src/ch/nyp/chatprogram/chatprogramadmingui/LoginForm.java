package ch.nyp.chatprogram.chatprogramadmingui;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import ch.nyp.chatprogram.common.*;

import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.omg.CORBA.portable.ApplicationException;

public class LoginForm extends JPanel {
	private JTextField txtUsername;
	private JTextField txtPassword;
	public UserApplication loginUser; // this users will be used in other forms
	/**
	 * Create the panel.
	 */
	public LoginForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 57, 347, 0};
		gridBagLayout.rowHeights = new int[]{40, 20, 35, 20, 20, 35, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblChatprogrammAdminGui = new JLabel("Chatprogramm Admin GUI");
		lblChatprogrammAdminGui.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblChatprogrammAdminGui = new GridBagConstraints();
		gbc_lblChatprogrammAdminGui.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblChatprogrammAdminGui.anchor = GridBagConstraints.NORTH;
		gbc_lblChatprogrammAdminGui.insets = new Insets(0, 0, 5, 0);
		gbc_lblChatprogrammAdminGui.gridx = 2;
		gbc_lblChatprogrammAdminGui.gridy = 1;
		add(lblChatprogrammAdminGui, gbc_lblChatprogrammAdminGui);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 3;
		add(lblUsername, gbc_lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setToolTipText("Username");
		txtUsername.setColumns(10);
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 3;
		add(txtUsername, gbc_txtUsername);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("Password");
		txtPassword.setColumns(10);
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.anchor = GridBagConstraints.NORTH;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 4;
		add(txtPassword, gbc_txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageError = "";
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				if(username.length() < 5) 
					messageError += "Username darf nicht weniger wie 5 Zeichen lang sein. \n";
				if(password.length() < 5)
					messageError += "Password darf nicht weniger wie 5 Zeichen lang sein. \n";
				UserApplication user = new UserApplication();
				if(messageError == "") { 															//try to login
					user = UserApplication.GetUserByUsernameAndPassword(username, password);
					if(user != null) 														//Check if user is authorized
					{
						loginUser = user;
						Main.frame.getContentPane().removeAll();
						Main.frame.getContentPane().add(new MenuForm());
						Main.frame.getContentPane().revalidate(); 
						Main.frame.getContentPane().repaint();
					}
					else {
						JOptionPane.showMessageDialog(null, "Authentifikationsdaten sind inkorrekt");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, messageError);
				}
			}
		});
		btnLogin.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 6;
		add(btnLogin, gbc_btnLogin);
		
	}

}
