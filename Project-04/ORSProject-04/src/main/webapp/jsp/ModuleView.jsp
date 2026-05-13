<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<script src="/ORSProject-04/js/checkbox.js"></script>
<script src="/ORSProject-04/js/datepicker.js"></script>
<link rel="icon   " type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>


<body>
	<img src="<%=request.getContextPath()%>/img/customLogo.jpg"
		align="right" width="100" height="40" border="0">
	<%
	UserBean user = (UserBean) session.getAttribute("user");
	boolean loggedIn = user != null;
	%>
	<%
	if (loggedIn) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>


	<a href="<%=ORSView.HEALTH_CTL%>">Add Health</a>
	<b>|</b>
	<a href="<%=ORSView.HEALTH_LIST_CTL%>">Health List</a>
	<b>|</b>
	<a href="<%=ORSView.PURGE_CTL%>">Add Purge</a>
	<b>|</b>
	<a href="<%=ORSView.PURGE_LIST_CTL%>">Purge List</a>
	<b>|</b>
	<a href="<%=ORSView.SYSTEM_CTL%>">Add System Event</a>
	<b>|</b>
	<a href="<%=ORSView.SYSTEM_LIST_CTL%>">System Event List</a>
	<b>|</b>
	<a href="<%=ORSView.SUBSCRIPTION_CTL%>">Add Subscription Plan</a>
	<b>|</b>
	<a href="<%=ORSView.SUBSCRIPTION_LIST_CTL%>">Subscription Plan List</a>
	<b>|</b>
	<a href="<%=ORSView.FEATURE_CTL%>">Add Feature Access</a>
	<b>|</b>
	<a href="<%=ORSView.FEATURE_LIST_CTL%>">Feature Access List</a>
	<b>|</b>
	<a href="<%=ORSView.AUDIT_CTL%>">Add Audit Trail</a>
	<b>|</b>
	<a href="<%=ORSView.AUDIT_LIST_CTL%>">Audit Trail List</a>
	<b>|</b>
	<a href="<%=ORSView.BLOCK_CTL%>">Add Block List</a>
	<b>|</b>
	<a href="<%=ORSView.BLOCK_LIST_CTL%>">Block List</a>
	<b>|</b>
	<a href="<%=ORSView.RULE_CTL%>">Add Rule Engine</a>
	<b>|</b>
	<a href="<%=ORSView.RULE_LIST_CTL%>">Rule Engine List</a>
	<b>|</b>
	<a href="<%=ORSView.ACCESS_CTL%>">Add Access Log</a>
	<b>|</b>
	<a href="<%=ORSView.ACCESS_LIST_CTL%>">Access Log List</a>
	<b>|</b>
	<a href="<%=ORSView.TRANSFORMATION_CTL%>">Add Transformation</a>
	<b>|</b>
	<a href="<%=ORSView.TRANSFORMATION_LIST_CTL%>">Transformation List</a>
	<b>|</b>
	<a href="<%=ORSView.PASSWORD_CTL%>">Add Password Reset</a>
	<b>|</b>
	<a href="<%=ORSView.PASSWORD_LIST_CTL%>">Password Reset List</a>
	<b>|</b>
	<a href="<%=ORSView.QUEUE_CTL%>">Add Queue Listener</a>
	<b>|</b>
	<a href="<%=ORSView.QUEUE_LIST_CTL%>">Queue Listener List</a>
	<b>|</b>
	<a href="<%=ORSView.EVENT_CTL%>">Add Event Listener</a>
	<b>|</b>
	<a href="<%=ORSView.EVENT_LIST_CTL%>">Event Listener List</a>
	<b>|</b>
	<a href="<%=ORSView.ALLOW_CTL%>">Add Allow List</a>
	<b>|</b>
	<a href="<%=ORSView.ALLOW_LIST_CTL%>">Allow List</a>
	<b>|</b>
	<a href="<%=ORSView.FACE_CTL%>">Add Face Recognition</a>
	<b>|</b>
	<a href="<%=ORSView.FACE_LIST_CTL%>">Face Recognition List</a>
	<b>|</b>
	<a href="<%=ORSView.CLAIM_CTL%>">Add Claim</a>
	<b>|</b>
	<a href="<%=ORSView.CLAIM_LIST_CTL%>">Claim List</a>
	<b>|</b>
	<a href="<%=ORSView.ACCOUNT_CTL%>">Add Account Status</a>
	<b>|</b>
	<a href="<%=ORSView.ACCOUNT_LIST_CTL%>">Account Status List</a>
	<b>|</b>
	<a href="<%=ORSView.REGISTRATION_CTL%>">Add Registration</a>
	<b>|</b>
	<a href="<%=ORSView.REGISTRATION_LIST_CTL%>">Registration List</a>
	<%
	}
	%>
	<hr>
	
	<br> <br> <br><br><br>
		<h1 align="center">
			<font size="10px" color="navy">Welcome to Daily Modules</font>
		</h1>
	<%@ include file="Footer.jsp"%>

</body>
</html>