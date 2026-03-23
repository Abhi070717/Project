<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
</head>
<body>
	<h3>Hi, Guest</h3>

	<a href="RecipeCtl"><b>Add Recipe</b></a>
	<b>|</b>
	<a href="BroadcastCtl"><b>Add Broadcast</b></a>
	<b>|</b>
	<a href="WalletCtl"><b>Add Wallet</b></a>
	<b>|</b>
	<a href="OtpCtl"><b>Add Otp</b></a>
	<b>|</b>
	<a href="PetCtl"><b>Add Pet</b></a>
	<b>|</b>
	<a href="PetListCtl"><b>Pet List</b></a>
	<b>|</b>
	<a href="ParkingCtl"><b>Add Parking</b></a>
	<b>|</b>
	<a href="ParkingListCtl"><b>Parking List</b></a>
	<b>|</b>
	<a href="SupplierCtl"><b>Add Supplier</b></a>
	<b>|</b>
	<a href="SupplierListCtl"><b>Supplier List</b></a>
	<b>|</b>
	<a href=<%=ORSView.MAINTENANCE_CTL%>><b>Add Maintenance</b></a>		<!-- Standard Way to write -->
	<hr>
</body>
</html>