<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.BankCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Bank Account</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.BANK_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BankBean"
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
				Bank Account
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
					<th align="left">Account Code<span style="color: red">*</span></th>
					<td><input type="text" name="code"
						placeholder="Enter Account Code"
						value="<%=DataUtility.getStringData(bean.getAccountCode())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("code", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Employee Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Account Holder Name"
						value="<%=DataUtility.getStringData(bean.getAccountHolderName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Balance<span style="color: red">*</span></th>
					<td><input type="text" name="balance" placeholder="Enter Balance"
						value="<%=DataUtility.getStringData(bean.getBalance())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("balance", request)%>
					</font></td>
				</tr>
				<tr>
					<th align="left">Bank Status<span style="color: red">*</span></th>
					<td>
						<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Active", "Active");
						map.put("Inactive", "Inactive");
						map.put("Hotlist", "Hotlist");

						String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
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
						name="operation" value="<%=BankCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=BankCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=BankCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=BankCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>