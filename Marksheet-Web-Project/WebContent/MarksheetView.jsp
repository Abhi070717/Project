<%@ page import="in.co.rays.bean.MarksheetBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Marksheet</title>

</head>
<body>
	<%@ include file="Header.jsp"%>
	<%
	MarksheetBean bean = (MarksheetBean) request.getAttribute("bean");

	if (bean == null) {
		response.sendRedirect("StudentLogin.jsp");
		return;
	}

	int total = bean.getPhysics() + bean.getChemistry() + bean.getMaths();
	double percentage = total / 3.0;

	String result = (bean.getPhysics() >= 33 && bean.getChemistry() >= 33 && bean.getMaths() >= 33) ? "PASS" : "FAIL";
	%>

	<div class="container">

		<h2>Student Marksheet</h2>

		<table>
			<tr>
				<th>Name</th>
				<td><%=bean.getName()%></td>
			</tr>
			<tr>
				<th>Roll Number</th>
				<td><%=bean.getRollNo()%></td>
			</tr>
		</table>

		<br>

		<table>
			<tr>
				<th>Subject</th>
				<th>Marks</th>
			</tr>
			<tr>
				<td>Physics</td>
				<td><%=bean.getPhysics()%></td>
			</tr>
			<tr>
				<td>Chemistry</td>
				<td><%=bean.getChemistry()%></td>
			</tr>
			<tr>
				<td>Maths</td>
				<td><%=bean.getMaths()%></td>
			</tr>
			<tr>
				<th>Total</th>
				<th><%=total%></th>
			</tr>
			<tr>
				<th>Percentage</th>
				<th><%=String.format("%.2f", percentage)%> %</th>
			</tr>
			<tr>
				<th>Result</th>
				<th
					class="<%=result.equals("PASS") ? "result-pass" : "result-fail"%>">
					<%=result%>
				</th>
			</tr>
		</table>

		<div class="button">
			<a href="StudentLogin.jsp">Back to Login</a>
		</div>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>
