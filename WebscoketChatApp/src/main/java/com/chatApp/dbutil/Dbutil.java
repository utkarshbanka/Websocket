package com.chatApp.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.jsp.tagext.TryCatchFinally;

public class Dbutil {

	
	 public static Connection provideconnection()
	 {
		 
		 Connection conn = null;
			
		  
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
//			String url = "jdbc:mysql://localhost:3306/humanres";
			String url = "jdbc:postgresql://localhost:5432/chatApp";
			
			try {
				conn = DriverManager.getConnection(url, "postgres", "54541216");
				System.out.println("Connection Success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return conn;
		 
	 }
	  
	public static void main(String[] args) {
		
		
		provideconnection();
		
	}
}
