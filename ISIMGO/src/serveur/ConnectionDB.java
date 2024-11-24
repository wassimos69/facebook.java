package serveur;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;


import java.io.IOException;
import java.sql.Connection;





public class ConnectionDB {
	
	public static Connection getconnection() {
	
		Connection connection = null;
		
		try {
		
			
			 Class.forName("com.mysql.cj.jdbc.Driver");
           
			
				
			 connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/isimgo","root","root");  
			     System.out.println("connecte");
			     
		}
		
		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,"connexion non valid avec la base de donne "+ ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		return connection;
	}

}