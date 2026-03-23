<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ParkingCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.ParkingBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Parking</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.PARKING_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ParkingBean"
			scope="request"></jsp:useBean>
			
			<% HashMap<String , String > map =(HashMap)request.getAttribute("map"); %>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				Parking
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
					<th align="left">Vehicle Number<span style="color: red">*</span></th>
					<td><input type="text" name="vehicle"
						placeholder="Enter Vehicle Number"
						value="<%=DataUtility.getStringData(bean.getVehicleNumber())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("vehicle", request)%>
					</font></td>
				</tr>

				<tr>
				<tr>
					<th align="left">Slot Number<span style="color: red">*</span></th>
					<td>
						<%
						
						String htmlList = HTMLUtility.getList("slot", bean.getSlotNumber(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("slot", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Entry Time<span style="color: red">*</span></th>
					<td><input type="text" name="entry"
						placeholder="Enter Entry Time"
						value="<%=DataUtility.getDateString(bean.getEntryTime())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("entry", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Exit Time<span style="color: red">*</span></th>
					<td><input type="text" name="exit"
						placeholder="Enter Exit Time"
						value="<%=DataUtility.getDateString(bean.getExitTime())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("exit", request)%></font></td>
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
						name="operation" value="<%=ParkingCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=ParkingCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=ParkingCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=ParkingCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>