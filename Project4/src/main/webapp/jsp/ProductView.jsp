<%@page import="in.co.rays.proj4.controller.ProductCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.ProductBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Product</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.PRODUCT_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ProductBean"
			scope="request" />

		<div align="center">
			<h1>
				<%
				if (bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				Product
			</h1>

			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<tr>
					<th>Product Name*</th>
					<td><input type="text" name="productName"
						value="<%=DataUtility.getStringData(bean.getProductName())%>"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("productName", request)%></font></td>
				</tr>

				<tr>
					<th>Category*</th>
					<td><input type="text" name="category"
						value="<%=DataUtility.getStringData(bean.getCategory())%>"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font></td>
				</tr>

				<tr>
					<th>Price*</th>
					<td><input type="text" name="price"
						value="<%=bean.getPrice()%>"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font></td>
				</tr>

				<tr>
					<th>Stock*</th>
					<td><input type="text" name="stock"
						value="<%=bean.getStock()%>"></td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("stock", request)%></font></td>
				</tr>

				<tr>
					<td></td>
					<td>
						<%
						if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=ProductCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=ProductCtl.OP_CANCEL%>"> <%
 } else {
 %>
						<input type="submit" name="operation"
						value="<%=ProductCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=ProductCtl.OP_RESET%>"> <%
 }
 %>
					</td>
				</tr>

			</table>
		</div>

	</form>
</body>
</html>