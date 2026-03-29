<%@page import="org.w3c.dom.NameList"%>
<%@page import="com.google.protobuf.StringValueOrBuilder"%>
<%@page import="com.google.protobuf.StringValue"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.MaintenanceListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.MaintenanceBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Maintenance List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MaintenanceBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Maintenance
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.MAINTENANCE_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List typeList = (List) request.getAttribute("nameList");

			List<MaintenanceBean> list = (List<MaintenanceBean>) ServletUtility.getList(request);
			Iterator<MaintenanceBean> it = list.iterator();

			if (list.size() != 0) {
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Issue Type:</b></label> <input
						type="text" name="type" placeholder="Enter Issue Type"
						value="<%=ServletUtility.getParameter("type", request)%>">&emsp;

						<label><b>Request Name: </b></label> <%=HTMLUtility.getList("name", String.valueOf(bean.getRequestName()), list)%>
						&nbsp; <input type="submit" name="operation"
						value="<%=MaintenanceListCtl.OP_SEARCH%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=MaintenanceListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="25%">Request Name</th>
					<th width="30%">Issue Type</th>
					<th width="25%">Location</th>
					<th width="25%">Request Date</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = (MaintenanceBean) it.next();
				%>
				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getRequestName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getIssueType()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getLocation()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getRequestDate()%></td>
					<td style="text-align: center;"><a
						href="MaintenanceCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
					}
					%>
				
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=MaintenanceListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MaintenanceListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MaintenanceListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=MaintenanceListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			}
			if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=MaintenanceListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
			}
			%>
		</form>
	</div>
</body>
</html>