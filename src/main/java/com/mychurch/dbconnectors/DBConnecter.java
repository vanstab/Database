package com.mychurch.dbconnectors;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecter {
	
	private String dbName = "jdbc:sqlite:";
	private static Connection database = null;
	public static Connection getConnection(){
		try {
			if(database == null || database.isClosed()) new DBConnecter();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return database;
	}
	private DBConnecter(){
		try {
			URI uri = DBConnecter.class.getResource("/testData.db").toURI();
			System.out.println(uri);
			Class.forName("org.sqlite.JDBC");
			database = DriverManager.getConnection(dbName+uri.getPath());
		} catch (SQLException e) {
			// TODO Auto-generated catch block and send error code to server
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
