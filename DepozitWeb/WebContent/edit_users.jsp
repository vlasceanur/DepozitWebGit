<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="database.DataBase"%>
<%@page import="java.sql.Connection"%>
<%@page import="users.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
//if not admin return error
HttpSession sess = request.getSession();
User u = (User)sess.getAttribute("user");
if(u==null || u.getType()!=User.Administrator)
{
	response.sendError(HttpServletResponse.SC_FORBIDDEN);
}
%>

<%
//create a list with all users
StringBuilder sb = new StringBuilder();

Connection conn = DataBase.getConnection();

if(conn == null)
	response.sendError(500);
else
{
	PreparedStatement ps = conn.prepareStatement("SELECT user_id, user_name FROM users");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		sb.append(String.format("<li><a href=\"edit.jsp?user_id=%d\">%s</a></li>", rs.getInt("user_id"), rs.getString("user_name")))
		.append("\n");
	}
	
}

%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit users</title>

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
	
</head>
<body>
<section>
	<h3>User list (click to edit)</h3>
	<ul>
		<%= sb.toString() %>
	</ul>
</section>
</body>
</html>