<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Login</title>

<script>
function refreshCaptcha() {
	document.getElementById("captchaImage").src =
		"<%=request.getContextPath()%>
	/captcha.jsp?ts="
				+ new Date().getTime();
	}
</script>

</head>
<body>
	<%@ include file="Header.jsp"%>
	<%
	String error = (String) request.getAttribute("error");
	%>
	<div class="center">

		<form action="MarksheetLoginCtl" method="post">
			<h1>Student Login</h1>
			<%
			if (error != null) {
			%>
			<h3 style="color: red;"><%=error%></h3>
			<%
			}
			%>
			<table>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="Name" placeholder="Enter Name"
						required></td>
				</tr>
				<tr>
					<td>Roll Number:</td>
					<td><input type="text" name="RollNo"
						placeholder="Enter Roll Number" required></td>
				</tr>
				<tr>
					<td>CAPTCHA:</td>
					<td><img
						src="<%=request.getContextPath()%>/captcha.jsp?ts=<%=System.currentTimeMillis()%>"
						id="captchaImage" alt="CAPTCHA Image">
						<button type="button" onclick="refreshCaptcha()">Reload</button></td>
				</tr>
				<tr>
					<td>Please Enter CAPTCHA:</td>
					<td><input type="text" name="captchaInput"
						placeholder="Enter CAPTCHA" required></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="GO"><input
						type="submit" value="GO"></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>
