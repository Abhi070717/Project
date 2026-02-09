<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet List</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<%
	List list = (List) request.getAttribute("list");
	%>
	<div align="Center">
		<form action="MarksheetListCtl" method="Post">
			<h1>Marksheet List</h1>
			<table width="100%" border="1px">
				<tr>
					<th>Select</th>
					<th>Id</th>
					<th>Name</th>
					<th>RollNo</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
				</tr>
				<%
				Iterator it = list.iterator();

				while (it.hasNext()) {
					MarksheetBean bean = (MarksheetBean) it.next();
				%>
				<tr align="Center">
					<td><input type="Checkbox" name="ids"
						value="<%=bean.getId()%>">
					<td><%=bean.getId()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>
				</tr>
				<%
				}
				%>
				<tr></tr>
			</table>
			<table>
				<td><input type="Submit" name="operation" value="Delete"></td>
			</table>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>