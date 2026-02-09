<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User</title>
</head>
<body>
	<%
	UserBean bean = (UserBean) request.getAttribute("bean");
	%>
	<%@ include file="Header.jsp"%>
	<div align="Center">
		<%
		String msg = (String) request.getAttribute("msg");
		%>
		<form action="UserCtl.do" method="Post">
			<h1><%=bean != null && bean.getId() > 0 ? "Update User" : "Add User"%></h1>
			<%-- <%
			if (bean != null && bean.getId() > 0) {
			%>
			<h1>Update User</h1>
			<%
			} else {
			%>
			<h1>Add User</h1>
			<%
			}
			%> --%>
			<%
			if (msg != null) {
			%>
			<h2 style="color: green"><%=msg%></h2>
			<%
			} else {
			%>
			<%
			}
			%>
			<table>
				<input type="hidden" name="id"
					value="<%=bean != null ? bean.getId() : ""%>">
				<tr>
					<th>First Name</th>
					<td><input type="text" name="firstName"
						value="<%=bean != null ? bean.getFirstName() : ""%>"
						placeholder="Enter first name"></td>
				</tr>
				<tr>
					<th>Last Name</th>
					<td><input type="text" name="lastName"
						value="<%=bean != null ? bean.getLastName() : ""%>"
						placeholder="Enter last name"></td>
				</tr>
				<tr>
					<th>Dob</th>
					<td><input type="date" name="DOB"
						value="<%=bean != null ? bean.getDob() : ""%>"></td>
				</tr>
				<tr>
					<th>login</th>
					<td><input type="email" name="login"
						value="<%=bean != null ? bean.getLogin() : ""%>"
						placeholder="Enter your email"></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password"
						value="<%=bean != null ? bean.getPassword() : ""%>"
						placeholder="Enter your password"></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=bean != null ? "Update" : "Save"%>"></td>
				</tr>
			</table>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>