<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="sun.security.pkcs11.Secmod.DbMode"%>
<%@page import="database.DataBaseManager"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
ResultSet rs = DataBaseManager.getUser(Integer.parseInt(request.getParameter("user_id")));
if(rs!= null)
	rs.next();
else
	return;
String username = rs.getString("user_name");
String password = rs.getString("user_password");
String register_date = rs.getString("user_register_date");
String creator_id = rs.getString("user_creator_id");
String user_type = rs.getString("user_type");

TreeMap<Integer, String> m = (TreeMap<Integer, String>)DataBaseManager.getUsers();
StringBuilder userList = new StringBuilder();
if(m!=null)
{
	for(Map.Entry<Integer, String> ent : m.entrySet())
	{
		userList.append(String.format("<option value=%d>%s</option>", ent.getKey(), ent.getKey() + "  -  " + ent.getValue()));
	}
}


TreeMap<Integer, String> tps = (TreeMap<Integer, String>)DataBaseManager.getTypes();
StringBuilder typeList = new StringBuilder();
if(m!=null)
{
	for(Map.Entry<Integer, String> ent : tps.entrySet())
	{
		typeList.append(String.format("<option value=%d>%s</option>", ent.getKey(), ent.getKey() + "  -  " + ent.getValue()));
	}
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Edit user</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
td
{
	padding: 1px 10px;
}
</style>
</head>
<body>


<form id="save" action="SaveUserChangesServlet" method="post" onsubmit="return verify()">
<table>

<tr>
	<th>Property</th>
	<th>Current</th>
	<th>Modify to</th>
</tr>
<tr>
	<td><label>id:</label></td>
	<td><%= request.getParameter("user_id") %></td>
	<td><label style="color:blue">Read only</label></td>
</tr>

<tr>
	<td><label>username:</label></td>
	<td><%= username %></td>
	<td><input type="text" id="usr" required></td>
</tr>

<tr>
	<td><label>password:</label></td>
	<td><%= password %></td>
	<td><input type="password" id="pwd" required></td>
</tr>
<tr>
	<td><label>register date:</label></td>
	<td><%= register_date %></td>
	<td><label style="color:blue">Read only</label>
</tr>
<tr>
	<td><label>type:</label></td>
	<td><%= user_type %></td>
	<td><select  id="type_id">
		<%= typeList.toString() %>				
	</select></td>
</tr>
<tr>
	<td><label>creator id:</label></td>
	<td><%= creator_id %></td>
	<td>
		<select id="creator_id">
			<%= userList.toString() %>			
		</select>
	</td>
</tr>
<tr>
	<td>
			<input type="hidden" name="id" value="<%=request.getParameter("user_id") %>"/>
			<button type="submit">Save changes</button>
	</td>
	<td>
		
			<button form="delete" type="submit" >Delete user</button>		
	</td>
</tr>

</table>
</form>

<form id="delete" action="DeleteUserServlet" method="post" onsubmit="return confirmDelete()">
			<input type="hidden" name="usr_name" value="<%=username %>"/>
</form>
<script>
function verify()
{
	var usr = document.getElementById("usr").value;
	
	if(usr.match("[a-zA-Z]*")[0] !== usr)
	{
		document.getElementById("usr").value = "";
		alert("invalid name");
		return false;
	}		
	return true;
}

function confirmDelete()
{
	return confirm("Delete?");
}

</script>

</body>
</html>