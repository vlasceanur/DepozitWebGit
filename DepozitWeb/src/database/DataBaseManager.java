package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataBaseManager {
	
	private static final int USER_TYPES_REGULAR_USER = 11;
	
	public static boolean CreateUser()
	{
		
		return false;
	}
	
	public static boolean addItem(String itemName, float itemVolume, float itemPrice)
	{
		if(itemVolume < 0.0f || itemPrice < 0.0f) return false;
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("INSERT INTO items(item_name, item_volume, item_price) VALUES(?,?,?)");
			ps.setString(1, itemName);
			ps.setFloat(2, itemVolume);
			ps.setFloat(3, itemPrice);
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			return false;
		}
	}	
	public static boolean removeItem(String itemName, float itemVolume, float itemPrice)
	{
		
		if(itemVolume < 0.0f || itemPrice < 0.0f) return false;
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("DELETE FROM items WHERE item_name=?" +
										" AND CAST(item_volume as DECIMAL(10,5))=? " +
										"AND CAST(item_price as  DECIMAL(10,5))=?");
			ps.setString(1, itemName);
			ps.setFloat(2, itemVolume);
			ps.setFloat(3, itemPrice);
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	public static boolean removeItem(int itemId)
	{
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("DELETE FROM items WHERE item_id=?");
			ps.setInt(1, itemId);
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;			
		}
	}

	public static boolean createUser(String userName, String password)
	{
		return createUser(userName, password, null, USER_TYPES_REGULAR_USER);
	}	
	public static boolean createUser(String userName, String password, Integer creator_id, int type)
	{
		if(userExists(userName)) return false;
		if(!typeExists(type)) return false;
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {			
			ps = con.prepareStatement("INSERT INTO users(user_name, user_password, user_creator_id, user_type) "
									+ "VALUES(?,?,?,?)");
			ps.setString(1, userName);
			ps.setString(2, password);
			if(creator_id == null) ps.setNull(3, java.sql.Types.NULL);
			else ps.setInt(3, creator_id);
			ps.setInt(4, type);
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}
	
	public static boolean removeUser(String userName)
	{
		if(!userExists(userName)) return false;		
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {			
			ps = con.prepareStatement("DELETE FROM users WHERE user_name=?");
			ps.setString(1, userName);			
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}
	
	public static boolean typeExists(int type) {
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT * FROM user_types WHERE user_type=?");
			ps.setInt(1, type);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}	
	public static boolean userExists(String userName)
	{
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT user_id FROM users WHERE user_name=?");
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}

	public static boolean addWarehouse(String name, String location, double cash, float volume, int manager_id)
	{
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("INSERT INTO warehouses(warehouse_name, warehouse_location, warehouse_cash," +
					" warehouse_volume, warehouse_manager_id)"
					+" VALUES(?, ?, ?, ?, ?);");
			ps.setString(1, name);
			ps.setString(2, location);
			ps.setDouble(3, cash);
			ps.setFloat(4, volume);
			ps.setInt(5, manager_id);
			
			if(ps.executeUpdate()==1) return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}						
	}
	
	public static boolean removeWarehouse(int id)
	{
		
		
		Connection con = DataBase.getConnection();
		
		if(con == null)
			throw new RuntimeException("Connection is null");
		
		PreparedStatement ps;
		try {			
			ps = con.prepareStatement("DELETE FROM warehouses WHERE =?");
			ps.setInt(1, id);			
			if(ps.executeUpdate() <=0) return false;
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}

	public static boolean addItemToWarehouse(int warehouse_id, int item_id, int quantity)
	{
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		int oldQuantity = warehouseHasItem(warehouse_id, item_id);
		if( oldQuantity >= 0)
		{
			return updateStock(warehouse_id, item_id, oldQuantity+ quantity);
		}
		else
		{
			return addToStock(warehouse_id, item_id, quantity);
		}			
	}

	
	private static boolean addToStock(int warehouse_id, int item_id,
			int quantity) {
		if(quantity<0) return false;
		
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO stock_items(warehouse_id, item_id, item_quantity) VALUES(?, ?, ?)");                                                
			ps.setInt(1, warehouse_id);
			ps.setInt(2, item_id);
			ps.setInt(3, quantity);
			
			if(ps.executeUpdate()==1)
				return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}				
	}

	private static boolean updateStock(int warehouse_id, int item_id, int new_quantity)
	{
		if (new_quantity < 0) return false;
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE stock_items SET item_quantity = ? WHERE warehouse_id=? AND item_id = ?");                                                
			ps.setInt(1, new_quantity);
			ps.setInt(2, warehouse_id);
			ps.setInt(3, item_id);
			
			if(ps.executeUpdate()==1)
				return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}				
	}
	
	public static int warehouseHasItem(int warehouse_id, int item_id) {
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT item_quantity FROM stock_items WHERE warehouse_id = ? AND item_id=?");
			ps.setInt(1, warehouse_id);
			ps.setInt(2, item_id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				return rs.getInt("item_quantity");
			}
			return -1;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}
	
	public static boolean addItemToProvider(int provider_id, int item_id)
	{
		if(checkItemOfProvider(provider_id, item_id))
			return false;
		
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO providers_stock VALUES(?, ?)");
			ps.setInt(1, provider_id);
			ps.setInt(2, item_id);
			
			if(ps.executeUpdate()==1)
				return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}			
	}
	
	private static boolean checkItemOfProvider(int provider_id, int item_id)
	{
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM providers_stock WHERE provider_id=? AND item_id=?");
			ps.setInt(1, provider_id);
			ps.setInt(2, item_id);
			
			if(ps.executeQuery().next())
				return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static boolean removeItemFromProvider(int provider_id, int item_id)
	{
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM providers_stock WHERE provider_id=? AND item_id=?");
			ps.setInt(1, provider_id);
			ps.setInt(2, item_id);
			
			if(ps.executeUpdate()==1)
				return true;
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public static ResultSet getUser(int id)
	{
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try
		{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?");
			ps.setInt(1, id);
			
			return ps.executeQuery();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static Map<Integer, String> getUsers()
	{
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		try
		
		{
			PreparedStatement ps = conn.prepareStatement("SELECT user_id, user_name FROM users");
			ResultSet rs = ps.executeQuery();
			
			Map<Integer, String> map = new TreeMap<Integer, String>();
			
			while(rs.next())
			{
				map.put(rs.getInt(1), rs.getString(2));
			}
			return map;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}		
	}
	
	public static Map<Integer, String> getTypes()
	{				
		Connection conn = DataBase.getConnection();
		if(conn == null)
			throw new RuntimeException("Connection is null");
		
		try		
		{
			PreparedStatement ps = conn.prepareStatement("SELECT user_type, user_type_name FROM user_types");
			ResultSet rs = ps.executeQuery();
			
			Map<Integer, String> map = new TreeMap<Integer, String>();
			
			while(rs.next())
			{
				map.put(rs.getInt(1), rs.getString(2));
			}
			return map;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}		
		
	}
}
