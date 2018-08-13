package ch.nyp.chatprogram.chatprogramadmingui;

import static java.awt.Color.black;

import javax.swing.JFrame;

import ch.nyp.chatprogram.common.*;
public class Main {
	public static JFrame frame;
	public static UserApplication loggedInUser;

    public static void main(String[] args) {
        frame = new JFrame("Chat");
        frame.setContentPane(new MenuForm());
        UserApplication user = new UserApplication();
        user.SetDisplayName("DisName");
        user.SetPassword("PasW");
        user.SetIsAdmin(true);
        user.SetUsername("usName");
        //frame.setContentPane(new AccountDetailsForm(user, MenuForm.class));
        //frame.setContentPane(new SimpleTableDemo());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 500);
        frame.setBackground(black);
        frame.setResizable(true);
    }
}
