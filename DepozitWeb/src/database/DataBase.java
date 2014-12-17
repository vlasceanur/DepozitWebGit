package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {		
	
	private static final String USERNAME="root";
	private static final String PASSWORD="";
	private static final String URL = "jdbc:mysql://localhost:3306/DepozitWebSchema";
	
	private static Connection INSTANCE;
	
	private DataBase(){
		
	}
	
	public static Connection getConnection()
	{
		if(INSTANCE == null)
		{
			try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				INSTANCE = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			} catch (SQLException e) {
				System.out.println(e);
				INSTANCE = null;
			}
		}
		
		return INSTANCE;
	}

}
