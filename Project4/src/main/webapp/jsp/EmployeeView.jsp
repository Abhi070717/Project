<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.EmployeeCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<html>
<head>
<title>Add Employee</title>
<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png">
</head>
<body>

<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EmployeeBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="color: navy;">
<% if (bean != null && bean.getId() > 0) { %>
    Update Employee
<% } else { %>
    Add Employee
<% } %>
</h1>

<!-- Messages -->
<h3 style="color: green;"><%=ServletUtility.getSuccessMessage(request)%></h3>
<h3 style="color: red;"><%=ServletUtility.getErrorMessage(request)%></h3>

<!-- Hidden Fields -->
<input type="hidden" name="id" value="<%=bean.getId()%>">
<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
 value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
 value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table>

<tr>
<th>Name<span style="color:red">*</span></th>
<td>
<input type="text" name="name" placeholder="Enter Name"
value="<%=DataUtility.getStringData(bean.getName())%>">
</td>
<td><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
</tr>

<tr>
<th>Department<span style="color:red">*</span></th>
<td>
<input type="text" name="department" placeholder="Enter Department"
value="<%=DataUtility.getStringData(bean.getDepartment())%>">
</td>
<td><font color="red"><%=ServletUtility.getErrorMessage("department", request)%></font></td>
</tr>

<tr>
<th>Salary<span style="color:red">*</span></th>
<td>
<input type="text" name="salary" placeholder="Enter Salary"
value="<%=DataUtility.getStringData(bean.getSalary())%>">
</td>
<td><font color="red"><%=ServletUtility.getErrorMessage("salary", request)%></font></td>
</tr>

<tr>
<th>Status<span style="color:red">*</span></th>
<td>
<%
HashMap<String,String> map = new HashMap<>();
map.put("Active","Active");
map.put("Inactive","Inactive");

String htmlList = in.co.rays.proj4.util.HTMLUtility.getList("status", bean.getStatus(), map);
%>
<%=htmlList%>
</td>
<td><font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font></td>
</tr>

<tr>
<th></th>
<td>
<% if (bean != null && bean.getId() > 0) { %>
    <input type="submit" name="operation" value="<%=EmployeeCtl.OP_UPDATE%>">
    <input type="submit" name="operation" value="<%=EmployeeCtl.OP_CANCEL%>">
<% } else { %>
    <input type="submit" name="operation" value="<%=EmployeeCtl.OP_SAVE%>">
    <input type="submit" name="operation" value="<%=EmployeeCtl.OP_RESET%>">
<% } %>
</td>
</tr>

</table>

</div>
</form>

</body>
</html>