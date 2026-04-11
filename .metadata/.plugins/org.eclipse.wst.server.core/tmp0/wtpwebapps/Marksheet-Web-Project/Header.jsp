<%@page import="in.co.rays.bean.MarksheetBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
</head>
<body>
	<%
	MarksheetBean mb = (MarksheetBean) session.getAttribute("user");
	%>
	<%
	if (mb != null) {
	%>
	<h2><%="Hii, " + mb.getName()%></h2>
	<a href="MarksheetLoginCtl?operation=logout">Logout</a> |
	<a href="MarksheetListCtl">Student List</a> |
	<a href="MarksheetDetailCtl">Add Student Detail</a>
	<%
	} else {
	%>
	<h2>Hello, Student</h2>
	<a href="MarksheetLoginCtl">Login</a> |
	<a href="WelcomeCtl">Welcome</a> |
	<a href="MarksheetDetailCtl">Student Registration</a> |
	<%
 }
 %>
 <hr>
</body>
</html>