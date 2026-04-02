<%@page import="in.co.rays.proj4.bean.NoteBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.NoteCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Note</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

	<form action="<%=ORSView.NOTE_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.NoteBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="color: navy">

				<%
				if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
				} else {
				%>
				Add
				<%
				}
				%>
				Note Summary
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
					<th align="left">Note Payable ID<span style="color: red">*</span></th>
					<td><input type="text" name="notePayableId"
						placeholder="Enter Note Payable ID"
						value="<%=DataUtility.getStringData(bean.getNotePayableId())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("notePayableId", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Total Principal<span style="color: red">*</span></th>
					<td><input type="text" name="totalPrincipal"
						placeholder="Enter Total Principal"
						value="<%=DataUtility.getStringData(bean.getTotalPrincipal())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("totalPrincipal", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Total Interest Paid<span style="color: red">*</span></th>
					<td><input type="text" name="totalInterestPaid"
						placeholder="Enter Total Interest Paid"
						value="<%=DataUtility.getStringData(bean.getTotalInterestPaid())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("totalInterestPaid", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Total Outstanding<span style="color: red">*</span></th>
					<td><input type="text" name="totalOutstanding"
						placeholder="Enter Total Outstanding"
						value="<%=DataUtility.getStringData(bean.getTotalOutstanding())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("totalOutstanding", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><input type="text" name="status"
						placeholder="Enter Status"
						value="<%=DataUtility.getStringData(bean.getStatus())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
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
						name="operation" value="<%=NoteCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=NoteCtl.OP_CANCEL%>">
					</td>

					<%
					} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=NoteCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=NoteCtl.OP_RESET%>">
					</td>

					<%
					}
					%>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>