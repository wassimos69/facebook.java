package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField clientUserName;
	private JTextField clientPassword;
	private int port = 8818;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.blue); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ISIMGO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(175, 0, 189, 50);
		contentPane.add(lblNewLabel);
		JLabel err = new JLabel("");
		err.setForeground(new Color(255, 0, 0));
		err.setBounds(110, 224, 233, 14);
		contentPane.add(err);
		
		clientUserName = new JTextField();
		clientUserName.setBounds(96, 134, 244, 20);
		contentPane.add(clientUserName);
		clientUserName.setColumns(10);
		
		clientPassword = new JPasswordField();
		clientPassword.setBounds(96, 165, 244, 20);
		contentPane.add(clientPassword);
		clientPassword.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String id = clientUserName.getText();// username entered by user
				String ps = clientPassword.getText();
				Socket s = new Socket("localhost", port); // create a socket
				DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
				DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
				outStream.writeUTF("LOGIN");
				outStream.writeUTF(id); // send username to the output stream
				outStream.writeUTF(ps);
				
				String MSG_ERR=inputStream.readUTF();
				
				if(MSG_ERR.length()>0) {
					err.setText(MSG_ERR);
				 
				}
				else {
					System.out.println("test1");
					dispose();
					Home h=new Home(id,s);
					
					h.setVisible(true);
					
					
					
				}
				
				
				
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(167, 196, 89, 23);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("Creat one...");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Register R= new Register();
				R.setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1.setBounds(175, 249, 104, 14);
		contentPane.add(lblNewLabel_1);
		
		
	}
}
