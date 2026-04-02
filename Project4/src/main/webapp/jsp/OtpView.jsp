<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.OtpCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Otp</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.OTP_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.OtpBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				OTP
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">OTP Code<span style="color: red">*</span></th>
					<td><input type="text" name="code"
						placeholder="Enter OTP Code"
						value="<%=DataUtility.getStringData(bean.getOtpCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("code", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Mobile Number<span style="color: red">*</span></th>
					<td><input type="text" name="number"
						placeholder="Enter Mobile Number"
						value="<%=DataUtility.getStringData(bean.getMobileNumber())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("number", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Expiry Time<span style="color: red">*</span></th>
					<td><input type="date" name="time"
						placeholder="Enter Expiry Time"
						value="<%=DataUtility.getDateString(bean.getExpiryTime())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("time", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">OTP Status<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("1", "ACTIVE");
						map.put("2", "USED");
						map.put("3", "INACTIVE");

						String htmlList = HTMLUtility.getList("status", bean.getOtpStatus(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=OtpCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=OtpCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=OtpCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=OtpCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>