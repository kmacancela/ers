<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ers.beans.Reimbursement" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Reimbursements</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <style>
  body{
  	color:white;
  }
  </style>
</head>
<body style="background-color:black">

<% List<Reimbursement> usersData = (List<Reimbursement>) request.getAttribute("usersData"); %>

<div class="container">        
  <br /><br /> <br />
  Hello, ${user.firstName} ${user.lastName}!
  <table class="table">
    <thead>
      <tr>
        <th> </th>
        <th>Amount</th>
        <th>Submitted On</th>
        <th>Description</th>
        <th>Submitted By</th>
        <th>Type</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
    
    <c:forEach var="i" begin="0" end="${fn:length(usersData) - 1}">
    	<tr>
        <td>${i + 1}</td>
        <td>$<fmt:formatNumber value="${usersData[i].amount}" minFractionDigits="2"/></td>
        <fmt:formatDate value="${usersData[i].submitted}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
        <td>${formattedDate}</td>
        <td>${usersData[i].description}</td>
        <td>${usersData[i].author.firstName}</td>
        <td>${usersData[i].type.type}</td>
        <td>${usersData[i].status.status}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
