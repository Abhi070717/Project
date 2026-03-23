<%@page import="in.co.rays.proj4.bean.BroadcastBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.BroadcastCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Broadcast User</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.BROADCAST_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BroadcastBean"
			scope="request"></jsp:useBean>

		<%
		List<BroadcastBean> roleList = (List<BroadcastBean>) request.getAttribute("broadcastList");
		%>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				Broadacast
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
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
					<th align="left">Message Title<span style="color: red">*</span></th>
					<td><input type="text" name="messageTitle"
						placeholder="Enter Message Title"
						value="<%=DataUtility.getStringData(bean.getMessageTitle())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("messageTitle", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Broadcast Code<span style="color: red">*</span></th>
					<td><input type="text" name="broadcastCode"
						placeholder="Enter Broadcast Code"
						value="<%=DataUtility.getStringData(bean.getBroadcastCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("broadcastCode", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Message Content<span style="color: red">*</span></th>
					<td><input type="text" name="messageContent"
						placeholder="Enter Message Content"
						value="<%=DataUtility.getStringData(bean.getMessageContent())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("messageContent", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Broadcast Time<span style="color: red">*</span></th>
					<td><input type="text" name="broadcastTime"
						placeholder="Enter Broadcast Time"
						value="<%=DataUtility.getStringData(bean.getBroadcastTime())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("broadcastTime", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Broadcast Status<span style="color: red">*</span></th>
					<td><input type="text" name="broadcastStatus"
						placeholder="Enter Broadcast Status"
						value="<%=DataUtility.getStringData(bean.getBroadcastStatus())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("broadcastStatus", request)%></font></td>
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
						name="operation" value="<%=BroadcastCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=BroadcastCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=BroadcastCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=BroadcastCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
		<%@ include file="Footer.jsp"%>
	</form>
</body>
</html>