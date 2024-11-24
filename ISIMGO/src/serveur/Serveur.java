package serveur;

import java.awt.EventQueue;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;








public class Serveur extends Thread{
	
	private static Map<String, Socket> allUsersList = new ConcurrentHashMap<>(); // keeps the mapping of all the
	private static int port = 8818;  // port number to be used
	private ServerSocket serverSocket; //server socket variable
	
	  Map<Integer, String> MESSAGE_MAP= new TreeMap<>();
	  Map<Integer, String> MESSAGE_MAP1= new TreeMap<>();
	
	public static void main(String[] args) {
		Serveur x= new Serveur();
		x.start();
		}

	 /* constrecteur de la classe Serveur */
	public Serveur() {  
		
		try {
			
			serverSocket = new ServerSocket(port);  // create a socket for server
			System.out.println("Server started on port: " + port + "\n"); // print messages to server message board
			System.out.println("Waiting for the clients...\n");
			new ClientAccept().start(); // this will create a thread for client
			 for (String key : allUsersList.keySet()) {
	             System.out.println("Clé : " + key);
	         }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


class ClientAccept extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				
				Socket clientSocket = serverSocket.accept();  // create a socket for client
				
				String TACH_TYPE = new DataInputStream(clientSocket.getInputStream()).readUTF(); 
				if(TACH_TYPE.equalsIgnoreCase("REGISTER")) {
					String uName = new DataInputStream(clientSocket.getInputStream()).readUTF(); // this will receive the username sent from client register view
					String uPass = new DataInputStream(clientSocket.getInputStream()).readUTF(); 
					DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
					PreparedStatement ps;
					ResultSet rs;
					ps=ConnectionDB.getconnection().prepareStatement("SELECT idusers FROM Users where username=?");
					ps.setString(1,uName);
					rs = ps.executeQuery();
					
					if(rs.next()) {
						cOutStream.writeUTF("NO");
						ps.close();
					}
					else {
						ps=ConnectionDB.getconnection().prepareStatement("insert into users(username,password)values(?,?)");
						ps.setString(1,uName);
						ps.setString(2,uPass);
						ps.execute();
						ps.close();
						cOutStream.writeUTF("YES");
					}
					
				}
				
				
				if(TACH_TYPE.equalsIgnoreCase("LOGIN")) {
					String uName = new DataInputStream(clientSocket.getInputStream()).readUTF(); // this will receive the username sent from client register view
					String uPass = new DataInputStream(clientSocket.getInputStream()).readUTF(); 
					DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream()); // create an output stream for client
					System.out.println(uName+"-"+uPass+"-"+TACH_TYPE);
					
					PreparedStatement ps;
					ResultSet rs;
					ps=ConnectionDB.getconnection().prepareStatement("SELECT idusers FROM Users where username=?  and password = ?");
					ps.setString(1,uName);
					ps.setString(2,uPass);
					rs = ps.executeQuery();
					InetAddress ipAddress = clientSocket.getInetAddress();
					
					
					int rowCount = 0;
					while(rs.next()) {
						rowCount++;
					}
					
					if(rowCount==0) {
						
						cOutStream.writeUTF("wrog username or password ");
						System.out.println(ipAddress);
						clientSocket.close();
					}
					cOutStream.writeUTF("");
					
					
				}
				
				
				if(TACH_TYPE.equalsIgnoreCase("LOAD_PUB")) {
					PreparedStatement ps1;
					ResultSet rs;
					ps1=ConnectionDB.getconnection().prepareStatement("SELECT username,pub FROM publication ");
					rs = ps1.executeQuery();
					System.out.println(TACH_TYPE+"home");
					DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
					while(true) {
						if (rs.next()) {
							cOutStream.writeBoolean(true);
						String user=rs.getString("username");
						String pub=rs.getString("pub");
						System.out.println(user+pub+"\n");
						cOutStream.writeUTF(user);
						cOutStream.writeUTF(pub);
						
						
						}
						else {
							cOutStream.writeBoolean(false);
							clientSocket.close();
						}
						
						}
					
					}
				
     if(TACH_TYPE.equalsIgnoreCase("PUBLISH")) {
    	 String Uname = new DataInputStream(clientSocket.getInputStream()).readUTF(); // this will receive the username sent from client register view
			String pub = new DataInputStream(clientSocket.getInputStream()).readUTF();
			PreparedStatement ps1;
		
			ps1=ConnectionDB.getconnection().prepareStatement("insert into publication (username,pub) values (?,?) ");
			ps1.setString(1,Uname);
			ps1.setString(2,pub);
			 ps1.execute();
			 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("LOAD_FRIEND")) {
    	 
    	 String user1=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps1;
			ResultSet rs;
			ps1=ConnectionDB.getconnection().prepareStatement("SELECT  * FROM frined WHERE usernameA=? ");
			ps1.setString(1,user1);
			rs = ps1.executeQuery();
			
			DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
			while(true) {
				if (rs.next()) {
					cOutStream.writeBoolean(true);
				String user=rs.getString("usernameB");
				cOutStream.writeUTF(user);
				}
				else {
					cOutStream.writeBoolean(false);
					clientSocket.close();
				}
				
				}
			
			
			
    	 
     }
     
     
     
     if(TACH_TYPE.equalsIgnoreCase("LOAD_MSG")) {
    	 String user1=new DataInputStream(clientSocket.getInputStream()).readUTF(); //capture de qui les messages
    	 String user2=new DataInputStream(clientSocket.getInputStream()).readUTF();//capture a qui les messages
    	 PreparedStatement ps1,ps2;
			ResultSet rs,rs2;
/*envoi*/			ps1=ConnectionDB.getconnection().prepareStatement("SELECT  * FROM message WHERE usernameA=? and usernameB=? ");
			        ps1.setString(1,user1);
			        ps1.setString(2, user2);
			        rs = ps1.executeQuery();
			        //recevoi
			        ps2=ConnectionDB.getconnection().prepareStatement("SELECT  * FROM message WHERE usernameA=? and usernameB=? ");
			        ps2.setString(1,user2);
			        ps2.setString(2, user1);
			        rs2 = ps2.executeQuery();
			        DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
			        Boolean found1=false;
			        Boolean found2=false;
			        
			     /*   while(rs.next()) {
			        	MESSAGE_MAP.put(Integer.parseInt(rs.getString("id_msg")),(rs.getString("usernameA")+":"+rs.getString("msg")) )	;
			        }
			        while(rs2.next()) {
			        	MESSAGE_MAP.put(Integer.parseInt(rs2.getString("id_msg")),(rs.getString("usernameA")+":"+rs2.getString("msg")) );
			        }
			        
			        System.out.println("TreeMap : " + MESSAGE_MAP);*/
			     
			     
			   
			       
			        
			        
			    	while(true) {
						if (rs.next() ) {
							cOutStream.writeBoolean(true);
						String msg=rs.getString("msg");
						cOutStream.writeUTF("me: "+msg); //message envoyer
						System.out.println(msg);
						
						}
						else {
							found1=true;
						}
						if(rs2.next()) {
							String msg2=rs2.getString("msg");
							System.out.println(msg2);
							cOutStream.writeUTF(user2+": "+msg2); //mesage recu
							
						}
						else {
							found2=true;
						}
						if(found1 && found2) {
							cOutStream.writeBoolean(false);
							
							System.out.println(found1+"  "+found2);
							System.out.println("failed");
						}
						
						}
			        
			        
			        
			        
			        
     }
     if(TACH_TYPE.equalsIgnoreCase("SOCKET_FOR_REC_MSG")) {
    	 String usernamechat=new DataInputStream(clientSocket.getInputStream()).readUTF(); //capture de qui les messages
    	
    	 System.out.println(usernamechat+"has added to list waiting.....");
    	 allUsersList.put(usernamechat, clientSocket);
    	
    	
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("CHAT")) {
    	 System.out.println("prepar for chat");
    	 String userEVN=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String message=new DataInputStream(clientSocket.getInputStream()).readUTF(); //capture de qui les messages
    	 String userdistinateur=new DataInputStream(clientSocket.getInputStream()).readUTF();//capture a qui les messages
    	 System.out.println(message+userdistinateur);
    	 System.out.println(allUsersList.containsKey(userdistinateur));
    	 PreparedStatement ps1;
 		
			ps1=ConnectionDB.getconnection().prepareStatement("insert into message(usernameA,usernameB,msg) values (?,?,?) ");
			ps1.setString(1,userEVN);
			ps1.setString(2,userdistinateur);
			ps1.setString(3,message);
			 ps1.execute();
    	 
    	 if(allUsersList.containsKey(userdistinateur)) {
    		
    		 Socket x=allUsersList.get(userdistinateur);
    		 DataOutputStream cOut = new DataOutputStream(x.getOutputStream());
    		 cOut.writeUTF(userEVN+": "+message);
    		 System.out.println("message sent");
    	 }
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("COMMENT")) {
    	 
    	 String publication=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String userpub=new DataInputStream(clientSocket.getInputStream()).readUTF(); 
    	 DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
    	
    	 PreparedStatement ps1;
			ResultSet rs;
			ps1=ConnectionDB.getconnection().prepareStatement("SELECT  * FROM comment WHERE username=? and publication=? ");
			ps1.setString(1, userpub);       
			ps1.setString(2,publication);        
			rs = ps1.executeQuery();
			
			System.out.println(ps1);
			while(true) {
				if (rs.next()) {
					
					cOutStream.writeBoolean(true);
					
				String user=rs.getString("username_com");
				String comm=rs.getString("comm");
				cOutStream.writeUTF(user);
				cOutStream.writeUTF(comm);
				
				}
				else {
					System.out.println(userpub+publication);
					cOutStream.writeBoolean(false);
					clientSocket.close();
					ps1.close();
				}
				
				}
    	 
    	 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("COMMENT_SEND")) {
    	 String userpub=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String publication=new DataInputStream(clientSocket.getInputStream()).readUTF(); 
    	 String comm=new DataInputStream(clientSocket.getInputStream()).readUTF(); 
    	 String username=new DataInputStream(clientSocket.getInputStream()).readUTF(); 
    	 PreparedStatement ps1;
 		
			ps1=ConnectionDB.getconnection().prepareStatement("insert into comment (username,publication,comm,username_com) values (?,?,?,?) ");
    	    ps1.setString(1, userpub);
    	    ps1.setString(2, publication);
    	    ps1.setString(3, comm);
    	    ps1.setString(4, username);
    	    ps1.execute();
    	    clientSocket.close();
    	 ps1.close();
    	 
    	 
     }
     
     if(TACH_TYPE.equalsIgnoreCase("LOAD_FR")) {
    	 String username=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps3;
  		
			ps3=ConnectionDB.getconnection().prepareStatement("SELECT username FROM `users` WHERE username NOT IN(SELECT usernameB FROM frined WHERE usernameA=?) AND username!=? AND username NOT in (SELECT usernameB from friend_request WHERE usernameA=?);");
			ps3.setString(1, username);
 	    	ps3.setString(2, username);
 	    	ps3.setString(3, username);
 	    	ResultSet rs;
 	    	rs=ps3.executeQuery();
 	    	
 	   	DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
		while(true) {
			if (rs.next()) {
				cOutStream.writeBoolean(true);
			String user=rs.getString("username");
			cOutStream.writeUTF(user);
			}
			else {
				cOutStream.writeBoolean(false);
				clientSocket.close();
			}
			
			}
 	    	
    	 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("SEND_FR")) {
    	 String usernameA=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String usernameB=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps4;
   		
			ps4=ConnectionDB.getconnection().prepareStatement("insert into friend_request(usernameA,usernameB) values(?,?)");
			ps4.setString(1, usernameA);
	    	ps4.setString(2, usernameB);
	    	ps4.execute();
	    	
    	 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("LOAD_FRR")) {
    	 String usernameA=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps5;
    	 ResultSet rs;
    	 ps5=ConnectionDB.getconnection().prepareStatement("select * from friend_request where usernameB=?");
			ps5.setString(1, usernameA);
	    	rs=ps5.executeQuery();
	    	DataOutputStream cOutStream = new DataOutputStream(clientSocket.getOutputStream());
			while(true) {
				if (rs.next()) {
					cOutStream.writeBoolean(true);
				String user=rs.getString("usernameA");
				cOutStream.writeUTF(user);
				}
				else {
					cOutStream.writeBoolean(false);
					clientSocket.close();
				}
				
				}
    	 
    	 
    	 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("FR_ACCEPT")) {
    	 String usernameA=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String usernameB=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps6;
    	 ps6=ConnectionDB.getconnection().prepareStatement("insert into frined (usernameA,usernameB) values(?,?)");
			ps6.setString(1, usernameA);
			ps6.setString(2, usernameB);
	    	ps6.execute();
	    	ps6.close();
	    	ps6=ConnectionDB.getconnection().prepareStatement("insert into frined (usernameA,usernameB) values(?,?)");
	    	ps6.setString(2, usernameA);
			ps6.setString(1, usernameB);
	    	ps6.execute();
	    	ps6.close();
	    	ps6=ConnectionDB.getconnection().prepareStatement("delete from friend_request where usernameA=? and usernameB=?");
			ps6.setString(1, usernameB);
			ps6.setString(2, usernameA);
	    	ps6.execute();
	    	ps6.close();
    	 
    	 
     }
     if(TACH_TYPE.equalsIgnoreCase("FR_REFUSE")) {
    	 String usernameA=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 String usernameB=new DataInputStream(clientSocket.getInputStream()).readUTF();
    	 PreparedStatement ps6;
    	 ps6=ConnectionDB.getconnection().prepareStatement("delete from friend_request where usernameA=? and usernameB=?");
			ps6.setString(1, usernameB);
			ps6.setString(2, usernameA);
	    	ps6.execute();
	    	ps6.close();
    	 
    	 
     }
     
     
     
				
				
				
			} catch (IOException ioex) {  // throw any exception occurs
				ioex.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
}
