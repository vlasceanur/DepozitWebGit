package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import database.DataBaseManager;

public class EditUserTest {
	
	
	public void editTest() throws SQLException
	{
	
		ResultSet rs = DataBaseManager.getUser(17);
		rs.next();
		String username = rs.getString("user_name");
		String password = rs.getString("user_password");
		String register_date = rs.getString("user_register_date");
		String creator_id = rs.getString("user_creator_id");
		String user_type = rs.getString("user_type");
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(register_date);
		System.out.println(creator_id);
		System.out.println(user_type);
		
	}
	
	public static void main(String[] args) throws SQLException {
		EditUserTest t = new EditUserTest();
		
		t.editTest();
	}

}
