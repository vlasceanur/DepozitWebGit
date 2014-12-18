package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import database.DataBase;
import database.DataBaseManager;

public class User {
	
	
	public static final	int Administrator = 9;
	public static final int	Accountant = 10;
	public static final	int Regular = 11;	
	public static final int WarehouseManager = 12;
	public static final int Provider = 13;
	
	
	private int id;
	private String name;
	private String password;
	private Timestamp register_date;
	private int creator_id;
	private int type;
	
	private boolean verified = false;
	public boolean isVerified()
	{
		return verified;
	}
	
	public int getType()
	{
		return type;
	}
	
	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}
	
	public boolean verify() throws SQLException
	{
		if(!DataBaseManager.userExists(name))
			return false;
		
		Connection conn = DataBase.getConnection();
		
		if(conn == null)
			throw new RuntimeException("Invalid connection");
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE user_name = ? AND user_password = ?");
		ps.setString(1, name);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			id = rs.getInt(1);
			name = rs.getString(2);
			password = rs.getString(3);
			register_date = rs.getTimestamp(4);
			creator_id = rs.getInt(5);
			type = rs.getInt(6);
			verified = true;
			return true;
		}		
		return false;
	}
	
	public void printInfo()
	{
		System.out.println(id);
		System.out.println(name);
		System.out.println(password);
		System.out.println(register_date);
		System.out.println(creator_id);
		System.out.println(type);
		System.out.println(verified);
	}
	
	public boolean createCookieAndSession(HttpSession s)
	{
		if(verified == true)
		{
			if(s == null) return false;
			s.setAttribute("user", this);
			return true;				
		}
		return false;
	}	
	
	@Override
	public String toString() {
		return name;
	}
}
