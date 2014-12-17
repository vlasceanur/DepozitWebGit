<%@page import="pages.PAGE_URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User home page</title>
</head>
<body>
	<h1>Hello: <%= request.getSession().getAttribute("user") %></h1>
	<ul>
		<li><a href="<%= PAGE_URL.BROWSE_ITEMS %>">Browse warehouses</a></li>
		<li><a href="<%= PAGE_URL.BROWSE_BASKET %>">Browse basket</a></li>
		<li><a href="<%= PAGE_URL.LOG_OUT %>">Log out</a></li>
	</ul> 
</body>
</html>