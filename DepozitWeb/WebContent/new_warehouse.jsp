<%@page import="users.User"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="database.DataBase"%>
<%@page import="database.DataBaseManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>


<% 
						Connection c = DataBase.getConnection();
					
						PreparedStatement ps = c.prepareStatement("SELECT user_id, user_name FROM users WHERE user_type=?");
						ps.setInt(1, User.WarehouseManager);
						ResultSet rs = ps.executeQuery();
						StringBuilder sb = new StringBuilder();
						while(rs.next())
						{
							sb.append("<option value=")
								.append(rs.getInt("user_id"))
								.append(">")
								.append(rs.getString("user_name"))
								.append("</option>\n");
						}											
%>								

<html>
<head>
	<style type="text/css">
		body
		{
			text-align: center;
			}
		
		table
		{
			display: inline-block;
		}		
	</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create new warehouse</title>
</head>
<body>
	<form action="CreateNewWarehouse" method="POST" onsubmit="return verify()">
		<table>
		<tr>
				<td>
					<div>
						<label>Name:</label> 						
					</div>
				</td>
				<td><input type="text" id="name" name="name" required/></td>				
		</tr>

		<tr>
				<td>
					<div>
						<label>Location:</label> 						
					</div>
				</td>				
				<td>
					<input type="text" id="location" name="location" required/>
				</td>
		</tr>

		<tr>
				<td>
					<div>
						<label>Capital:</label> 						
					</div>
				</td>				
				<td>
					<input type="text" id="capital" name="capital" required/>
				</td>
		</tr>
		<tr>
				<td>
					<div>
						<label>Volume:</label> 
						
					</div>
				</td>
				<td>
					<input type="text" id="volume" name="volume" required/>
				</td>
		</tr>
		<tr>
				<td>
					<div>
						<label>Manager:</label> 	
					</div>	
				</td>
				<td>
				<select id="manager_id" name="manager_id">
					<%= sb.toString() %>			
				</select>
				</td>
		</tr>						
		<tr>
			<td id="buttons_row">
				<button type = "submit">Create</button><button type = "reset">Reset</button>
			</td>
		</table>
	</form>
<script>
function verify()
{
	var name = document.getElementById("name");
	var location = document.getElementById("location");
	var capital = document.getElementById("capital");
	var volume = document.getElementById("volume");
	var manager = document.getElementById("manager_id");
	var td = document.getElementById("buttons_row");
	var rtn = true;
	
	if(manager.value === "")
	{		
		td.innerHTML = "<label style=\"color:red\">You have no manager. Create a manager account or promote another user to manager</label>";
		rtn = false;
	}	
	if(name.value.match("[a-zA-Z0-9\. ]*")[0] !== name.value)
	{
		name.value = "";
		rtn = false;
	}
	if(location.value.match("[a-zA-Z0-9\. ]*")[0] !== location.value)
	{
		location.value = "";
		rtn = false;
	}
	var nrCapital = new Number(capital.value);
	if(isNaN(nrCapital))
	{
		capital.value = "";
		rtn = false;
	}
	var nrVolume = new Number(volume.value);	
	if(isNaN(nrVolume))
	{				
		volume.value = "";
		rtn = false;	
	}
	return rtn;
}

</script>
</body>
</html>