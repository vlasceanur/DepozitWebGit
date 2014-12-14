package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import database.DataBase;
import database.DataBaseManager;

public class User {
	private int id;
	private String name;
	private String password;
	private Timestamp register_date;
	private int creator_id;
	private int type;
	
	private boolean verified = false;
	
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
	
	public Cookie createCookieAndSession(HttpSession s)
	{
		if(verified == true)
		{
			if(s == null) return null;
			s.setAttribute("user", this);
			return new Cookie("user", this.hashCode() + "");			
		}
		return null;
	}		
}
