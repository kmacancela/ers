<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="com.ers.beans.Reimbursement"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ERS: View your reimbursements</title>
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

#update{
	display:inline-block;
	margin-right: 10px;
}

#update2{
	display:inline-block;
}


h1{
  font-size: 30px;
  color: #fff;
  text-transform: uppercase;
  font-weight: 300;
  text-align: center;
  margin-bottom: 15px;
}
table{
  width:100%;
  table-layout: fixed;
}
.tbl-header{
  background-color: rgba(255,255,255,0.3);
 }
.tbl-content{
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid rgba(255,255,255,0.3);
}
th{
  padding: 20px 15px;
  text-align: left;
  font-weight: 500;
  font-size: 12px;
  color: #fff;
  text-transform: uppercase;
}
td{
  padding: 15px;
  text-align: left;
  vertical-align:middle;
  font-weight: 300;
  font-size: 12px;
  color: #fff;
  border-bottom: solid 1px rgba(255,255,255,0.1);
}


/* demo styles */

@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,300,700);
body{
  background: -webkit-linear-gradient(left, #25c481, #000000);
  background: linear-gradient(to bottom, #082047, #000000);
  font-family: 'Roboto', sans-serif;
}
section{
  margin: 50px;
}


</style>

<!-- <script type="text/javascript">
            function greeting(){
                alert("Welcome " + document.forms["frm1"]["fname"].value + "!")
            }
</script> -->

</head>
<body style="background-color: black">

	<%-- <% List<Reimbursement> usersData = (List<Reimbursement>) request.getAttribute("usersData"); 
   List<Reimbursement> pending = (List<Reimbursement>) request.getAttribute("pending");
%> --%>

<nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid" >
          <div class="navbar-header">
            <!-- <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button> -->
            <a class="navbar-brand" href="#">Welcome, ${user.firstName} ${user.lastName}!</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li class="active"><a href="#">Home 
              
              <c:choose>
						<c:when test="${user.role.userRole == 'Manager'}">
              				<span class="badge">${fn:length(pending) - 1}</span>
              			</c:when>
              			<c:when test="${user.role.userRole == 'Employee'}">
              				<span class="badge">${fn:length(usersData) - 1}</span>
              			</c:when>
              		</c:choose>
              </a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">View by...<span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Pending</a></li>
                  <li><a href="#">Approved</a></li>
                  <li><a href="#">Denied</a></li>
                  <li role="separator" class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li>
              <li><a href="#add">Add new...</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <!-- <li class="active"><a href="./">Default <span class="sr-only">(current)</span></a></li>
              <li><a href="../navbar-static-top/">Static top</a></li> -->
              
              <li role="presentation">
             <!--  <a href="../navbar-fixed-top/">
              Log off
              </a> -->
              <form action="logoff.do" method="post">
    			<input type="submit" value="Logout" />
			</form>
              
              </li>
              
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>





<!-- <div class="dropdown">
  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    Select...
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
    <li><a href="#">Approve</a></li>
    <li><a href="#">Deny</a></li>
  </ul>
</div> -->
<!-- <button type="button" class="btn btn-default" aria-label="Left Align">
  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
</button>

<button type="button" class="btn btn-default">
  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
</button> -->


<br/><br/><br/><br/>
	<div id="main" class="container">
<!-- 		<br />
		<br/><a href="#add">Add a reimbursement now!</a> -->
<div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0" >
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
			</table>
			</div>
			<div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
			<tbody>

				
				<c:forEach var="i" begin="0" end="${fn:length(pending) - 1}">
					<form method="post" action="submitted.do">
					<input type="hidden" name="resolver" value="${user.username}" />
					<tr>
						<td>${i + 1}<input type="hidden" name="rowId" value="${pending[i].id}" />
						</td> <!-- instead of th -->
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
					

<!-- 							<select name="newStatus" required="required" style="color: black">
								<option value="" disabled selected>Select...</option>
								Will give the value of either "Approved" or "Denied" to newStatus which can be used by the servlet
								<option value="Approved">Approve</option>
								<option value="Denied">Deny</option>
						</select> 
						<input type="submit" value="Submit" style="color: black" name="submit"> -->
						<button type="submit" class="btn btn-default" aria-label="Left Align" name="newStatus" value="Approved">
  <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
</button>

<button type="submit" class="btn btn-default" name="newStatus" value="Denied">
  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
</button>

						
						</td>
					</tr>
					</form>
				</c:forEach>

				</c:when>

				<c:when test="${user.role.userRole == 'Employee'}">
					<th></th>
					<th>Amount</th>
					<th>Submitted On</th>
					<th>Description</th>
					<!--         <th>Submitted By</th> -->
					<th>Type</th>
					<th>Status</th>
					<!-- </tr>
					</thead>
					<tbody> -->
					</tr>
			</thead>
			</table>
			</div>
			<div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
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

				</c:when>
				</c:choose>
			
			</tbody>
		</table>
	</div>
	
<c:choose>
	<c:when test="${user.role.userRole == 'Employee'}">
	<a name="add"></a>
	<h1>Add a new reimbursement</h1><br/><br/>
	<!-- We submit the employee's user ID, amount of the reimbursement, description, and type to the 
	servlet which we then lead them to /submit.do 
	***Maybe I can put this in a submit.do page and then it redirects them to /home.do Will possibly
	need another jsp page***-->
	
	<form method="post" action="added.do">
	
	<input type="hidden" name="author" value="${user.usersId}" />
	 <label>Amount</label> 
	 	<!-- i had input-group instead of form-group -->
	 	<!-- <div class="form-group"><input name="amount" placeholder="amount" style="color: black" required="required" class="form-control"> </div> -->   
		
		<div class="input-group">
      <div class="input-group-addon">$</div>
      <input type="text" class="form-control" name="amount" id="exampleInputAmount" placeholder="Amount" required="required">
    </div>
    
	<br/><label>Description</label> 
	<!-- <div class="input-group"><input name="description" placeholder="description" style="color: black" class="form-control"> </div>  --> 
	
	<textarea class="form-control" rows="3" name="description" placeholder="Description"></textarea>
	
	<br/><label>Type</label>
	<select name="type" required="required" style="color: black" class="form-control">
		<option value="" disabled selected>Select type...</option>
		<option value="1">Lodging</option>
		<option value="2">Travel</option>
		<option value="3">Food</option>
		<option value="4">Other</option>
	</select>    <br/>
	<input type="submit" value="Submit" style="color: black" class="btn btn-default">
	</form>
	<br/><br><br/>
	</c:when>
</c:choose>
	<br/><br/><br/>
</body>

<script type="text/javascript">
$(window).on("load resize ", function() {
  var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
  $('.tbl-header').css({'padding-right':scrollWidth});
}).resize();

</script>

</html>
