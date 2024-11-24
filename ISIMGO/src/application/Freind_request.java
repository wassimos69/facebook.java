package application;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Freind_request extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int port = 8818;
	DefaultListModel<String> allDlm = new DefaultListModel<String>(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Freind_request frame = new Freind_request("");
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
	public Freind_request(String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 313, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLUE); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(0, 11, 299, 437);
		contentPane.add(list);
		
		JButton btnNewButton = new JButton("Accept");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue()!=null) {
				Socket s;
				try {
					s = new Socket("localhost", port);
				 // create a socket
				DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
				DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
				outStream.writeUTF("FR_ACCEPT");
				outStream.writeUTF(username);
				outStream.writeUTF(list.getSelectedValue().toString());
				allDlm.remove(list.getSelectedIndex());
				s.close();
				
				
				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else {JOptionPane.showMessageDialog(null, "SELECT USER !");}
			}
		});
		btnNewButton.setBounds(0, 474, 143, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Refuse");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue()!=null) {
					Socket s;
					try {
						s = new Socket("localhost", port);
					 // create a socket
					DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
					DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
					outStream.writeUTF("FR_REFUSE");
					outStream.writeUTF(username);
					outStream.writeUTF(list.getSelectedValue().toString());
					allDlm.remove(list.getSelectedIndex());
					s.close();
					
					
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
					else {JOptionPane.showMessageDialog(null, "SELECT USER !");}
			}
		});
		btnNewButton_1.setBounds(153, 474, 146, 23);
		contentPane.add(btnNewButton_1);
		try {
			Socket s = new Socket("localhost", port); // create a socket
			DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
			DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
			outStream.writeUTF("LOAD_FRR");
			outStream.writeUTF(username);
			while(inputStream.readBoolean()) {
				String users =inputStream.readUTF();
				allDlm.addElement(users);
				}
				
				list.setModel(allDlm);
				s.close();
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
}
