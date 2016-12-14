<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ers.beans.Reimbursement"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Reimbursements</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
body {
	color: white;
}

/* The side navigation menu */
.sidenav {
    height: 100%; /* 100% Full-height */
    width: 0; /* 0 width - change this with JavaScript */
    position: fixed; /* Stay in place */
    z-index: 1; /* Stay on top */
    top: 0;
    left: 0;
    background-color: #111; /* Black*/
    overflow-x: hidden; /* Disable horizontal scroll */
    padding-top: 60px; /* Place content 60px from the top */
    transition: 0.5s; /* 0.5 second transition effect to slide in the sidenav */
}

/* The navigation menu links */
.sidenav a {
    padding: 8px 8px 8px 32px;
    text-decoration: none;
    font-size: 25px;
    color: #818181;
    display: block;
    transition: 0.3s
}

/* When you mouse over the navigation links, change their color */
.sidenav a:hover, .offcanvas a:focus{
    color: #f1f1f1;
}

/* Position and style the close button (top right corner) */
.sidenav .closebtn {
    position: absolute;
    top: 0;
    right: 25px;
    font-size: 36px;
    margin-left: 50px;
}

/* Style page content - use this if you want to push the page content to the right when you open the side navigation */
#main {
    transition: margin-left .5s;
    padding: 20px;
}

/* On smaller screens, where height is less than 450px, change the style of the sidenav (less padding and a smaller font size) */
@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

</style>

<script type="text/javascript">
            function greeting(){
                alert("Welcome " + document.forms["frm1"]["fname"].value + "!")
            }
</script>

</head>
<body style="background-color: black">

	<%-- <% List<Reimbursement> usersData = (List<Reimbursement>) request.getAttribute("usersData"); 
   List<Reimbursement> pending = (List<Reimbursement>) request.getAttribute("pending");
%> --%>

<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
  <a href="#">About</a>
  <a href="#">Services</a>
  <a href="#">Clients</a>
  <a href="#">Contact</a>
</div>

<span onclick="openNav()">open</span>

	<div id="main" class="container">
		<br />
		<br /> <br /> Hello, ${user.firstName} ${user.lastName}!
		<table class="table">
			<thead>
				<tr>

					<c:choose>
						<c:when test="${user.role.userRole == 'Manager'}">
							<th></th>
							<th>Amount</th>
							<th>Submitted On</th>
							<th>Description</th>
							<th>Submitted By</th>
							<th>Type</th>
							<th>Status</th>
							<th></th>
				</tr>
			</thead>
			<tbody>

				
				<c:forEach var="i" begin="0" end="${fn:length(pending) - 1}">
					<form method="post" action="submitted.do">
					<input type="hidden" name="resolver" value="${user.username}" />
					<tr>
						<td>${i + 1}ID:${pending[i].id} Resolver:${user.username} <input
							type="hidden" name="rowId" value="${pending[i].id}" />
						</td>
						<td>$<fmt:formatNumber value="${pending[i].amount}"
								minFractionDigits="2" /></td>
						<fmt:formatDate value="${pending[i].submitted}"
							var="formattedDate" type="date" pattern="MM-dd-yyyy" />
						<td>${formattedDate}</td>
						<td>${pending[i].description}</td>
						<td>${pending[i].author.firstName}
							${pending[i].author.lastName}</td>
						<td>${pending[i].type.type}</td>
						<td>${pending[i].status.status}</td>
						<td>
							<!-- <form method="post" action="submit.do"> --> 
							<select name="newStatus" required="required" style="color: black">
								<option value="" disabled selected>Select...</option>
								<!-- Will give the value of either "Approved" or "Denied" to newStatus which can be used by the servlet -->
								<option value="Approved">Approve</option>
								<option value="Denied">Deny</option>
						</select> <!-- ***Do I need the value and if I do where?*** --> <input
							type="submit" value="Submit" style="color: black" name="submit">
						</td>
					</tr>
					</form>
				</c:forEach>

				</c:when>
				<%--     	<c:otherwise> --%>







				<c:when test="${user.role.userRole == 'Employee'}">
					<th></th>
					<th>Amount</th>
					<th>Submitted On</th>
					<th>Description</th>
					<!--         <th>Submitted By</th> -->
					<th>Type</th>
					<th>Status</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach var="i" begin="0" end="${fn:length(usersData) - 1}">
							<tr>
								<td>${i + 1}</td>
								<td>$<fmt:formatNumber value="${usersData[i].amount}"
										minFractionDigits="2" /></td>
								<fmt:formatDate value="${usersData[i].submitted}"
									var="formattedDate" type="date" pattern="MM-dd-yyyy" />
								<td>${formattedDate}</td>
								<td>${usersData[i].description}</td>
								<%--         <td>${usersData[i].author.firstName}</td> --%>
								<td>${usersData[i].type.type}</td>
								<td>${usersData[i].status.status}</td>
							</tr>
						</c:forEach>
						<%--     	</c:otherwise> --%>
						
						
						
				</c:when>
				</c:choose>
			
			

			</tbody>
		</table>
	</div>
	
	<c:choose>
	<c:when test="${user.role.userRole == 'Employee'}">
	<h1>Add a new reimbursement</h1><br/><br/>
	<form method="post" action="added.do" onsubmit="greeting()">
	<input type="hidden" name="author" value="${user.usersId}" />
	Amount: <input name="amount" placeholder="amount" style="color: black" required="required">    
	Description: <input name="description" placeholder="description" style="color: black">   
	Type: <select name="type" required="required" style="color: black">
		<option value="" disabled selected>Select...</option>
		<option value="1">Lodging</option>
		<option value="2">Travel</option>
		<option value="3">Food</option>
		<option value="4">Other</option>
	</select>    
	<input type="submit" value="Submit" style="color: black">
	</form>
	<br/><br><br/>
	</c:when>
	</c:choose>
</body>

<script type="text/javascript">
/* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

/* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}
</script>

</html>
