<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Form</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div align="center">
	<%
	String msg = (String) request.getAttribute("msg");
	%>

		<form action="UserRegistrationCtl" method="post">
			<h1>User Detail Form</h1>
			
			<%
			if(msg != null){
			%>
			<h3 style="color: green"><%= msg %></h3>
			<%
			}
			%>
			<table>
				<tr>
					<th>First Name</th>
					<td><input type="text" name="firstName" value=""
						placeholder="Enter First Name"></td>
				</tr>
				<tr>
					<th>Last Name</th>
					<td><input type="text" name="lastName" value=""
						placeholder="Enter Last Name"></td>
				</tr>

				<tr>
					<th>Date Of Birth</th>
					<td><input type="date" name="DOB" value=""></td>
				</tr>
				<tr>
					<th>Login</th>
					<td><input type="email" name="login" value=""
						placeholder="Enter your Email"></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password" name="password"
						placeholder="Enter Your Password"></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="Submit" name="operation" value="SignUp"></td>
					<td></td>
				</tr>
			</table>

		</form>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>