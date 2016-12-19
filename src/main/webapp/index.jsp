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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
	body {
		color: black;
	}
	.mybtn-blue {
   		color: green;
	}
	
	.statusButtons span {
  display: inline-block;
}

	h1{
	  font-size: 30px;
	  color: black;
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
	  /* background-color: rgba(255,255,255,0.3); */
	  background-color: orange;
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
	 /*  color: #fff; */
	 color:black;
	  text-transform: uppercase;
	}
	td{
	  padding: 15px;
	  text-align: left;
	  vertical-align:middle;
	  font-weight: 300;
	  font-size: 12px;
	 /*  color: #fff; */
	 color:black;
	  border-bottom: solid 1px rgba(255,255,255,0.1);
	}

</style>
</head>

<body style="background-color: white">

	<nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid" >
          <div class="navbar-header">
            <a class="navbar-brand" href="#">Welcome, ${user.firstName} ${user.lastName}!</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
				<li class="active">
					<a href="#">Home         
				<c:choose>
					<c:when test="${user.role.userRole == 'Manager'}">
              			<span class="badge">${fn:length(pending)}</span>
              		</a>
              	</li>
              	<li>
              		<!-- <a href="#add">Add new...</a> -->
              		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              			View completed
              		</a>
              	</li>
            </ul>
              		</c:when>
              		<c:when test="${user.role.userRole == 'Employee'}">
              			<span class="badge">${fn:length(usersData)}</span>
              		</a>
              	</li>
              	<li class="dropdown">
                	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                		View by...
                		<span class="caret"></span>
                	</a>
                	<ul class="dropdown-menu">
                  		<li>
                  			<a href="#">Pending</a>
                  		</li>
                  		<li>
                  			<a href="#">Approved</a>
                  		</li>
                  		<li>
                  			<a href="#">Denied</a>
                  		</li>
                	</ul>
              	</li>
              	<li>
              		<!-- <a href="#add">Add new...</a> -->
              		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo" style="margin-top: 8px">
              			Add new...
              		</button>
              	</li>
            </ul>
              		</c:when>
              	</c:choose>
              	
            <ul class="nav navbar-nav navbar-right">
              <li role="presentation">
              	<form action="logoff.do" method="post">
    				<!-- <input type="submit" value="Logout" /> -->
    				<button type="submit" class="btn btn-primary" style="margin-top: 8px; margin-right:8px" value="Logout">
              			Log off
              		</button>
				</form>
              </li> 
            </ul>
		  </div><!--/.nav-collapse -->
		</div><!--/.container-fluid -->
	</nav>

	<br/><br/><br/><br/>
	<div id="main" class="container">
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
						<td>$<fmt:formatNumber value="${pending[i].amount}" minFractionDigits="2" /></td>
						<fmt:formatDate value="${pending[i].submitted}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
						<td>${formattedDate}</td>
						<td>${pending[i].description}</td>
						<td>${pending[i].author.firstName} ${pending[i].author.lastName}</td>
						<td>${pending[i].type.type}</td>
						<td>${pending[i].status.status}</td>
						<td>
					
						<button type="submit" class="btn btn-default mybtn-blue" aria-label="Left Align" 
						name="newStatus" value="Approved">
  							<span class="glyphicon glyphicon-ok" aria-hidden="true" ></span>
						</button>

						<button type="submit" class="btn btn-default" name="newStatus" value="Denied">
 							 <span class="glyphicon glyphicon-remove" aria-hidden="true" style="color: red"></span>
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
								<td>
							<div class="statusButtons">
							<c:choose>
								<c:when test="${usersData[i].status.status == 'Approved'}">
    								<h4><span class="label label-success" style="width:96px">${usersData[i].status.status}</span></h4>
  								</c:when>
  								<c:when test="${usersData[i].status.status == 'Denied'}">
    								<h4><span class="label label-danger" style="width:96px">${usersData[i].status.status}</span></h4>
  								</c:when>
  								<c:otherwise>
  									<h4><span class="label label-warning" style="width:96px">${usersData[i].status.status}</span></h4>
  								</c:otherwise>
							</c:choose>
							</div>
							
								</td>
							</tr>
						</c:forEach>

				</c:when>
				</c:choose>
			
			</tbody>
		</table>
	</div>
	
<%-- <c:choose>
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
</c:choose> --%>

<!-- delete after if not working -->
<form method="post" action="added.do">
<input type="hidden" name="author" value="${user.usersId}" />
<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Add new...</button> -->

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">New reimbursement</h4>
      </div>
      <div class="modal-body">
      
        <!-- <form method="post" action="added.do"> -->
          <div class="form-group">
            <label for="recipient-name" class="control-label">Amount:</label>
            <!-- <input type="text" class="form-control" id="recipient-name"> -->
           <div class="input-group">
            <span class="input-group-addon">$</span><input type="text" class="form-control" name="amount" id="exampleInputAmount" placeholder="Amount" required="required">
          </div>
          </div>
          <div class="form-group">
            <label for="message-text" class="control-label">Description:</label>
            <!-- <textarea class="form-control" id="message-text"></textarea> -->
            <textarea class="form-control" rows="3" name="description" placeholder="Description"></textarea>
          </div>
          <div class="form-group">
          	<select name="type" required="required" style="color: black" class="form-control">
				<option value="" disabled selected>Select type...</option>
				<option value="1">Lodging</option>
				<option value="2">Travel</option>
				<option value="3">Food</option>
				<option value="4">Other</option>
			</select>
          </div>
       <!--  </form> -->
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <!-- <button type="button" class="btn btn-primary">Submit</button> -->
        <input type="submit" value="Submit" style="color: black" class="btn btn-default">
      </div>
    </div>
  </div>
</div>
 </form>
 <!-- end if trying -->






	<br/><br/><br/>

</body>

<script type="text/javascript">
$(window).on("load resize ", function() {
  var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
  $('.tbl-header').css({'padding-right':scrollWidth});
}).resize();

</script>

</html>
