package application;

import java.awt.Color;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Comment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private DefaultListModel<String> allDlm = new DefaultListModel<String>();
	private DefaultListModel<String> allDlm2 = new DefaultListModel<String>();
	private int port = 8818;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Comment frame = new Comment("","");
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
	public Comment(String pub , String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 266, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLUE); 
		  String[] parties = pub.split(":");
		  String publication=parties[1];
		  String userpub=parties[0];
System.out.println(publication+username);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 111, 230, 297);
		contentPane.add(list);
		
		textField = new JTextField();
		textField.setBounds(10, 419, 230, 35);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add Comment ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().length()>0) {
				try {
				Socket s = new Socket("localhost", port); // create a socket
				DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
				DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
				outStream.writeUTF("COMMENT_SEND");
				outStream.writeUTF(userpub);
				outStream.writeUTF(publication);
				outStream.writeUTF(textField.getText());
				outStream.writeUTF(username);
				allDlm2.addElement(username+">>>"+textField.getText());
				
				
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
				
				
			}else {
				JOptionPane.showMessageDialog(null, "WRITE A COMMENT !");
			}
				
			}
			
		});
		try {
			Socket s = new Socket("localhost", port); // create a socket
			DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
			DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
			outStream.writeUTF("COMMENT");
			outStream.writeUTF(publication);
			outStream.writeUTF(userpub);
			while(inputStream.readBoolean()) {
				String us=inputStream.readUTF();
				String CM=inputStream.readUTF();
				allDlm2.addElement(us+">>>"+CM);
				
			}
			list.setModel(allDlm2);
			
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		
		btnNewButton.setBounds(10, 461, 230, 23);
		contentPane.add(btnNewButton);
		allDlm.addElement(pub);
		JList list_1 = new JList();
		list_1.setBounds(10, 43, 230, 54);
		contentPane.add(list_1);
		list_1.setModel(allDlm);
	}
}
