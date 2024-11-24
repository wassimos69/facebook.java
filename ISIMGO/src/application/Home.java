package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private int port = 8818;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private DefaultListModel<String> allDlm = new DefaultListModel<String>(); 
	

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home("");
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
	public Home(String username, Socket soc) {
		setTitle(username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.BLUE); 
      
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(23, 414, 354, 46);
		contentPane.add(textField);
		textField.setColumns(10);
		JList publ_view = new JList();
		publ_view.setBounds(23, 97, 527, 306);
		contentPane.add(publ_view);
		
		JButton btnNewButton = new JButton("Publish");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Socket s = new Socket("localhost", port); // create a socket
					DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
					DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
					outStream.writeUTF("PUBLISH");
					
					allDlm.addElement(username+":"+textField.getText());
					outStream.writeUTF(username); //send user name
					outStream.writeUTF(textField.getText()); //send publication
					textField.setText("");
					
				
				
				
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(387, 420, 163, 35);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Friend Request🛂");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Freind_request fr=new  Freind_request(username);
				 fr.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(0, 47, 150, 39);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Add a new Friend➕");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFriend f = new AddFriend(username);
				f.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(147, 47, 150, 39);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Send Message 📨");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MesssageBOX msg;
				try {
					msg = new MesssageBOX(username,soc);
					msg.setVisible(true);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton_3.setBounds(415, 47, 145, 39);
		contentPane.add(btnNewButton_3);
		
		
		try {
		Socket s = new Socket("localhost", port); // create a socket
		DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
		DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
		outStream.writeUTF("LOAD_PUB");
		System.out.println(inputStream.available());
		while(inputStream.readBoolean()) {
		String user =inputStream.readUTF();
		String publication=inputStream.readUTF();
		allDlm.addElement(user+":"+publication);
		
		InetAddress ipAddress = s.getInetAddress();
		System.out.println(ipAddress);
		}
		System.out.println(inputStream.available());
		
		publ_view.setModel(allDlm);
		
		JButton btnNewButton_4 = new JButton("Comment💬");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((publ_view.getSelectedValue())!=null) {
				Comment	Com=new Comment(publ_view.getSelectedValue().toString(),username);
				Com.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "SELECT PUBLICATION !");
				}
				
			}
		});
		btnNewButton_4.setBounds(288, 47, 136, 39);
		contentPane.add(btnNewButton_4);
	 
	   
	   
		
	    
		
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
