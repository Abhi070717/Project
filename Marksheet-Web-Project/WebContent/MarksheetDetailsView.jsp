<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet Create</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div align="Center">
		<%
		String msg = (String) request.getAttribute("msg");
		%>
		<form action="MarksheetDetailCtl" method="post">
			<h1>Add Student Detail</h1>
			<%
			if (msg != null) {
			%>
			<h2><%=msg%></h2>
			<%
			}
			%>
			<table>
				<tr>
					<th>Name</th>
					<td><input type="text" name="Name" value=""
						placeholder="Enter Name"></td>
				</tr>
				<tr>
					<th>Roll Number</th>
					<td><input type="text" name="RollNo" value=""
						placeholder="Enter Roll Number"></td>
				</tr>
				<tr>
					<th>Physics</th>
					<td><input type="text" name="Physics" value=""
						placeholder="Numbers"></td>
				</tr>
				<tr>
					<th>Chemistry</th>
					<td><input type="text" name="Chemistry" value=""
						placeholder="Numbers"></td>
				</tr>
				<tr>
					<th>Mathematics</th>
					<td><input type="text" name="Maths" value=""
						placeholder="Numbers"></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="Submit" name="operation" value="Update"></td>
					<td></td>
				</tr>
			</table>
		</form>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>