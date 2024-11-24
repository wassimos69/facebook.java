package application;

import java.awt.Color;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MesssageBOX extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private int port = 8818;
	private DefaultListModel<String> allDlm = new DefaultListModel<String>(); 
	private DefaultListModel<String> allDlm2 = new DefaultListModel<String>();
	private DefaultListModel<String> allDlm3 = new DefaultListModel<String>();
	DataInputStream input;
	DataOutputStream out;
	

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MesssageBOX frame = new MesssageBOX("");
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
	public MesssageBOX(String user,Socket soc) throws InterruptedException {
		setTitle("Messanger");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.BLUE); 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Friends");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 140, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("DialogBox");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(166, 7, 225, 22);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(166, 380, 260, 42);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 140, 321);
		contentPane.add(scrollPane);
		
		JList list = new JList();
	
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(166, 48, 260, 321);
		contentPane.add(scrollPane_1);
		
		JList<String> list_1 = new JList();
		scrollPane_1.setViewportView(list_1);
		
		JButton btnNewButton = new JButton("Send");
	
		btnNewButton.setBounds(166, 433, 260, 34);
		contentPane.add(btnNewButton);
		
		
		
		
		
	
		      
		     
					
	
		 
		
		try {
			
			
			Socket s = new Socket("localhost", port); // create a socket
			DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
			DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
			outStream.writeUTF("LOAD_FRIEND");
			outStream.writeUTF(user);
			
			System.out.println(inputStream.available());
			while(inputStream.readBoolean()) {
			String users =inputStream.readUTF();
			InetAddress ipAddress = s.getInetAddress();
			System.out.println(ipAddress);
			allDlm.addElement(users);
			}
			
			list.setModel(allDlm);
			s.close();
		  
		 
		   
		   
			
		    
			
			
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		//cree une socket de reception
		try {
		Socket com = new Socket("localhost", port); // create a socket
		DataInputStream input = new DataInputStream(com.getInputStream()); // create input and output stream
		DataOutputStream out = new DataOutputStream(com.getOutputStream());
		out.writeUTF("SOCKET_FOR_REC_MSG");
		out.writeUTF(user);
		new Read(com).start();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
				Socket socket_for_send = new Socket("localhost", port); // create a socket
				DataInputStream input = new DataInputStream(socket_for_send.getInputStream()); // create input and output stream
				DataOutputStream out = new DataOutputStream(socket_for_send.getOutputStream());
				String msg=textField.getText();
				out.writeUTF("CHAT");
				out.writeUTF(user);
				out.writeUTF(msg);
				out.writeUTF(list.getSelectedValue().toString());
				allDlm2.addElement("me: "+msg);
				textField.setText("");
				
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			   	try {
			   		allDlm2.clear();
	        		if(list.getSelectedValue().toString()!=null) {
	        		Socket s = new Socket("localhost", port);
	        		DataInputStream inputStream = new DataInputStream(s.getInputStream()); // create input and output stream
	        		DataOutputStream outStream = new DataOutputStream(s.getOutputStream());
	    			
	    			
	    			System.out.println(list.getSelectedValue().toString());
	    			
	    			outStream.writeUTF("LOAD_MSG");
	    			outStream.writeUTF(user);
	    			outStream.writeUTF(list.getSelectedValue().toString());
	    			System.out.println("select not null");
                        while(inputStream.readBoolean()) {
	    				
	    				String msg =inputStream.readUTF();
	    				String msg2 =inputStream.readUTF();
	    				InetAddress ipAddress = s.getInetAddress();
	    				System.out.println(msg);
	    				System.out.println(ipAddress);
	    				
	    				allDlm2.addElement(msg);
	    				allDlm2.addElement(msg2);
	    				}
                        
                        
                      
                        
		    			s.close();
		    			
		    			list_1.setModel(allDlm2);	
	    			
	    			
	    			
	        		
	        		}
	        		
	        
	        
	            // Mettez ici le code que vous souhaitez exécuter toutes les 5 secondes
	            System.out.println("Refreshing...");
	        	}catch(Exception ex) {
						ex.printStackTrace();}
				
				
			}
		});
		
		
		
	}
	class Read extends Thread {
		Socket soc;
		public Read(Socket s) {
			soc=s;
		}
		@Override
		public void run() {
			while (true) {
				try {
					DataInputStream input = new DataInputStream(soc.getInputStream());
					String m = input.readUTF();
					System.out.println("inside read thread : " + m);
					System.out.println(m);
					allDlm2.addElement("--->"+m);
					
					
					
				
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
		
		
	}}
}
