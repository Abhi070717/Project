<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.PressListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.PressBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Press List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.PressBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Press List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.PRESS_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<PressBean> titleList = (List<PressBean>) request.getAttribute("titleList");

			List<PressBean> list = (List<PressBean>) ServletUtility.getList(request);
			Iterator<PressBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<label><b>Title: </b></label><%=HTMLUtility.getList("title", String.valueOf(bean.getTitle()), titleList)%>
					&emsp;
					<label><b>Author: </b></label>
					<input type="text" name="author"
					placeholder="Enter Author Name"
						value="<%=ServletUtility.getParameter("author", request)%>">
					&emsp;
					<input type="submit" name="operation"
						value="<%=PressListCtl.OP_SEARCH%>">&emsp;
					<input type="submit" name="operation"
						value="<%=PressListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="40%">Title</th>
					<th width="25%">Author</th>
					<th width="25%">Release Date</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = (PressBean) it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>

					<td style="text-align: center;"><%=index++%></td>

					<td style="text-align: center; text-transform: capitalize;"><%=bean.getTitle()%>
					</td>

					<td style="text-align: center; text-transform: capitalize;"><%=bean.getAuthor()%>
					</td>

					<td style="text-align: center;"><%=bean.getReleaseDate()%></td>

					<td style="text-align: center;"><a
						href="PressCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=PressListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PressListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=PressListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=PressListCtl.OP_NEXT%>"
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
						value="<%=PressListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>
		</form>
	</div>
</body>
</html>