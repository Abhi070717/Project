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

	<div align="center">
		<form action="MarksheetLoginCtl" method="post">

			<h1>Login Form</h1>

			<table>
				<tr>
					<th>Name</th>
					<td><input type="text" name="Name" placeholder="Enter Name"
						required></td>
				</tr>

				<tr>
					<th>Roll Number</th>
					<td><input type="text" name="RollNo"
						placeholder="Enter Roll Number" required></td>
				</tr>

				<tr>
					<th>Please Enter Captcha</th>
					<td><img
						src="<%=request.getContextPath()%>/captcha.jsp?ts=<%=System.currentTimeMillis()%>"
						id="captchaImage" alt="CAPTCHA Image">

						<button type="button" onclick="refreshCaptcha()">Reload</button> <br>
					<br> <input type="text" name="captchaInput"
						placeholder="Enter CAPTCHA" required></td>
				</tr>

				<tr>
					<th></th>
					<td><input type="submit" value="Login"></td>
				</tr>
			</table>

		</form>
	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>
