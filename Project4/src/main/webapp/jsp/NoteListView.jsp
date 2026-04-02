<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.NoteListCtl"%>
<%@page import="in.co.rays.proj4.bean.NoteBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Note Summary List</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.NoteBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy;">Note List</h1>

		<div style="margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.NOTE_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<NoteBean> list = (List) ServletUtility.getList(request);
			Iterator<NoteBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table>
				<tr>
					<td align="center"><label><b>Note Payable ID:</b></label> <input
						type="text" name="notePayableId"
						placeholder="Enter Note Payable ID"
						value="<%=ServletUtility.getParameter("notePayableId", request)%>">&emsp;
						<%
						HashMap<String, String> statusMap = new HashMap<String, String>();
						statusMap.put("Active", "Active");
						statusMap.put("Inactive", "Inactive");
						%> <label><b>Status:</b></label> <%=HTMLUtility.getList("status", ServletUtility.getParameter("status", request), statusMap)%>
						<input type="submit" name="operation"
						value="<%=NoteListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=NoteListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<%
			if (list != null && list.size() > 0) {
			%>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th><input type="checkbox" id="selectall"></th>
					<th>S.No</th>
					<th>Note Payable ID</th>
					<th>Total Principal</th>
					<th>Total Interest</th>
					<th>Total Outstanding</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>

				<tr>
					<td align="center"><input type="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getNotePayableId()%></td>
					<td align="center"><%=bean.getTotalPrincipal()%></td>
					<td align="center"><%=bean.getTotalInterestPaid()%></td>
					<td align="center"><%=bean.getTotalOutstanding()%></td>
					<td align="center"><%=bean.getStatus()%></td>
					<td align="center"><a
						href="<%=ORSView.NOTE_CTL%>?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						value="<%=NoteListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=NoteListCtl.OP_NEW%>"></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=NoteListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=NoteListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td><input type="submit" name="operation"
						value="<%=NoteListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>
</body>
</html>