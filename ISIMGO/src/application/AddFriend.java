package application;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AddFriend extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	int port=8818;
	DefaultListModel<String> allDlm = new DefaultListModel<String>(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFriend frame = new AddFriend("");
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
	public AddFriend(String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 288, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.BLUE); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 11, 254, 411);
		contentPane.add(list);
		
		JButton btnNewButton = new JButton("Add new frined");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue()!=null) {
					try {
					Socket s = new Socket("localhost", port); // create a socket
					DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
					DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
					outStream.writeUTF("SEND_FR");
					outStream.writeUTF(username);
					outStream.writeUTF(list.getSelectedValue().toString());
					allDlm.remove(list.getSelectedIndex());
					s.close();
					
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "SELECT USER !");
				}
			}
		});
		btnNewButton.setBounds(10, 433, 254, 23);
		contentPane.add(btnNewButton);
		try {
		Socket s = new Socket("localhost", port); // create a socket
		DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
		DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
		outStream.writeUTF("LOAD_FR");
		outStream.writeUTF(username);
		while(inputStream.readBoolean()) {
			String users =inputStream.readUTF();
			allDlm.addElement(users);
			}
			
			list.setModel(allDlm);
			s.close();
		
		
		
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
