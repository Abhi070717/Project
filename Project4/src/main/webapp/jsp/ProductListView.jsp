<%@page import="in.co.rays.proj4.controller.ProductListCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.ProductBean"%>
<%@page import="in.co.rays.proj4.util.*"%>
<%@page import="java.util.*"%>

<html>
<head>
<title>Product List</title>
</head>

<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ProductBean"
		scope="request" />

	<div align="center">

		<h1>Product List</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<form action="<%=ORSView.PRODUCT_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<ProductBean> list = (List<ProductBean>) ServletUtility.getList(request);
			Iterator<ProductBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- Search -->
			<table>
				<tr>
					<td>Product Name: <input type="text" name="productName"
						value="<%=ServletUtility.getParameter("productName", request)%>">

						Category: <input type="text" name="category"
						value="<%=ServletUtility.getParameter("category", request)%>">

						<input type="submit" name="operation"
						value="<%=ProductListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=ProductListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<table border="1" width="100%">
				<tr>
					<th>Select</th>
					<th>S.No</th>
					<th>Name</th>
					<th>Category</th>
					<th>Price</th>
					<th>Stock</th>
					<th>Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>
				<tr>
					<td><input type="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getProductName()%></td>
					<td><%=bean.getCategory()%></td>
					<td><%=bean.getPrice()%></td>
					<td><%=bean.getStock()%></td>
					<td><a href="ProductCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<br>

			<table width="100%">
				<tr>

					<td><input type="submit" name="operation"
						value="<%=ProductListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center"><input type="submit" name="operation"
						value="<%=ProductListCtl.OP_NEW%>"> <input type="submit"
						name="operation" value="<%=ProductListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=ProductListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>

				</tr>
			</table>

		</form>
	</div>
</body>
</html>