package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

import database.DataBase;
import database.DataBaseManager;

public class DataBaseTest {

	@Test
	public void TestConnection()
	{
		Connection con = DataBase.getConnection();
		assertNotNull(con);		
	}
	
	
	
	@Test	
	public void addItemTest()	
	{
		assertTrue("add 1", DataBaseManager.addItem("Pepsi 2L", 2.2f, 6.32f));					
		assertTrue("add 2",DataBaseManager.addItem("Pepsi 0.5", 0.6f, 3.5f));
		assertTrue("add 3",DataBaseManager.addItem("Fanta 2L", 2.2f, 6.5f));
		assertFalse("add 4",DataBaseManager.addItem("Fanta 2L", -4f, -5f));							
	}
	
	@Test
	public void removeItemTest()
	{
		assertTrue("rem 1",DataBaseManager.removeItem("Pepsi 2L", 2.2f, 6.32f));
		assertTrue("rem 2",DataBaseManager.removeItem("Pepsi 0.5", 0.6f, 3.5f));
		assertTrue("rem 3",DataBaseManager.removeItem("Fanta 2L", 2.2f, 6.5f));
		assertFalse("rem 4",DataBaseManager.removeItem("Fanta 2L", -4f, -5f));
	}
	
	@Test
	public void userExistsTest()
	{
		assertTrue("User should exists", DataBaseManager.userExists("admin"));
		assertFalse("User should not exists", DataBaseManager.userExists("sagasgafasgasgasga"));
	}
	
	@Test
	public void typeExistsTest()
	{
		assertTrue("Type should exists", DataBaseManager.typeExists(1));
		assertFalse("Type should not exists", DataBaseManager.typeExists(-100));
	}
	
	@Test
	public void createUserTest()
	{
		//delete the user first
		DataBaseManager.removeUser("username");
		
		assertTrue("Create failed but should succed", DataBaseManager.createUser("username", "password"));
		assertFalse("Create must fail", DataBaseManager.createUser("username", "password"));
		
	}
	
	@Test
	public void deleteUserTest()
	{
		//create a junk user
		DataBaseManager.createUser("test", "test");
		assertTrue("User must be deleted", DataBaseManager.removeUser("test"));
		assertFalse("User shoudln`t exist so nothing to delete", DataBaseManager.removeUser("test"));
		
	}
	
	@Test
	public void createWareHouseTest()
	{
		assertTrue("Warehouse must be created", DataBaseManager.addWarehouse("Calarasi wh2", "test street", 1111112222255555.123456, 1000000f, 1));
	}
	

	@Test
	public void getItemCountTest()
	{
		assertEquals(1510, DataBaseManager.warehouseHasItem(1, 111));
		assertEquals(-1, DataBaseManager.warehouseHasItem(1, 112));
	}
	
	@Test
	public void changeStockTest()
	{
		assertTrue(DataBaseManager.addItemToWarehouse(12, 1121, 500));
		assertTrue(DataBaseManager.addItemToWarehouse(55, 55, 100));
	}
	
	@Test
	public void addItemToProvider()
	{
		assertTrue("Must be added", DataBaseManager.addItemToProvider(1, 1));
	}
}
