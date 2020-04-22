package GUI.DDL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginPage {
	private JFrame frame;
	private Container container;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
	private JCheckBox showPassword;
	
	LoginPage()
    {
		initialize();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();//calling addActionEvent() method
	}

	private void initialize() {
		frame = new JFrame();
		container = frame.getContentPane();
		userLabel=new JLabel("USERNAME");
		passwordLabel=new JLabel("PASSWORD");
		userTextField=new JTextField();
		passwordField=new JPasswordField();
		loginButton=new JButton("LOGIN");
		resetButton=new JButton("RESET");
		showPassword=new JCheckBox("Show Password");

		frame.setTitle("Welcome to Temporal Database");
		frame.setVisible(true);
		frame.setBounds(10,10,370,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setLayoutManager()
	{
		container.setLayout(null);
	}

	public void setLocationAndSize()
	{
		userLabel.setBounds(50,150,100,30);
		passwordLabel.setBounds(50,220,100,30);
		userTextField.setBounds(150,150,150,30);
		passwordField.setBounds(150,220,150,30);
		showPassword.setBounds(150,250,150,30);
		loginButton.setBounds(50,300,100,30);
		resetButton.setBounds(200,300,100,30);
	
	
	}

	public void addComponentsToContainer()
	{
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
	}

	public void addActionEvent()
	{
		//adding Action listener to components
		loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
                String userText;
				String pwdText;
				userText = userTextField.getText();
				pwdText = passwordField.getText();
				if (userText.equalsIgnoreCase("username") && pwdText.equalsIgnoreCase("password")) {
					frame.setVisible(false);
					DDLPage ddl = new DDLPage();
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
				}
            }
		});
		
		resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
				userTextField.setText("");
            	passwordField.setText("");
            }
		});
		
		showPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
				if (showPassword.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
            }
        });
	}

	public static void main(String args[]) {
		LoginPage page = new LoginPage();
	}
}