  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<%
	String msg = (String) request.getAttribute("msg");
	%>
	<%
	String errormsg = (String) request.getAttribute("errormsg");
	%>
	<div align="Center">

		<form action="LoginCtl" method="post">
			<h1>Login Form</h1>
			<%
			if (msg != null) {
			%>
			<h3 style="color: red"><%=msg%></h3>
			<%
			}
			%>
			<%
			if (errormsg != null) {
			%>
			<h3 style="color: red">
				<%=errormsg%>
			</h3>

			<%
			}
			%>
			<table>
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
					<td><input type="Submit" name="operation" value="SignIn"></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>