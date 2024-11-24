package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setTitle("creat accont");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLUE); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(128, 156, 214, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(128, 195, 216, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ISIMGO");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(153, 11, 189, 50);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("username");
		lblNewLabel_1.setBounds(30, 159, 69, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("password");
		lblNewLabel_2.setBounds(30, 198, 69, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Creat new account");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port=8818;
				Socket s;
				if(textField.getText().length()>0  && textField_1.getText().length()>0) {
				try {
					s = new Socket("localhost", port);
				// create a socket
				DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
				DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
				outStream.writeUTF("REGISTER");
				outStream.writeUTF(textField.getText());
				outStream.writeUTF(textField_1.getText());
				String err=inputStream.readUTF();
				System.out.println(err);
				if(err.equals("NO")) {
					JOptionPane.showMessageDialog(null, "User alredy Taken !");
					System.out.println("sysysysy");
				}
				if(err.equals("YES")) {
					
					JOptionPane.showMessageDialog(null, "Congratulations! Your account has been created !");
					Login l=new Login();
					l.setVisible(true);
					dispose();
					
				}
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}else {
					JOptionPane.showMessageDialog(null, "Please fill out all fields !");
				}
			}
		});
		btnNewButton.setBounds(128, 229, 214, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back to login ");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login l=new Login();
				l.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(0, 11, 99, 23);
		contentPane.add(btnNewButton_1);
	}
}
