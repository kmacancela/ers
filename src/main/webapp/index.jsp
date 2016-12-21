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
	th{
		font-weight: bold;
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
	  background-color: #eff0f1;
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
	  text-align: center;
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
            <div class="navbar-brand">Welcome, ${user.firstName} ${user.lastName}!</div>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
				<li class="active">
					<a>Home         
		<c:choose>
			<c:when test="${user.role.userRole == 'Manager'}">
              			<span class="badge">${fn:length(pending)} </span>
              		</a>
              	</li>
              	<li>
              		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              			View completed
              		</a>
              	</li>
            </ul>
			</c:when>
			<c:when test="${user.role.userRole == 'Employee'}">
              			<span class="badge"><div id="headerCount">${fn:length(usersData)}</div></span>
              		</a>
              	</li>
              	<li>
              		<select id="ddlStatus" class="ddlFilterTableRow form-control" data-attribute="status" style="margin-top: 8px; width: 120px;">  
    					<option value="0">View all</option>
    					<option value="Pending">Pending</option>
    					<option value="Approved">Approved</option>
    					<option value="Denied">Denied</option>
					</select>
              	</li>
              	<li>
              		<!-- <a href="#add">Add new...</a> -->
              		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo" style="margin-top: 8px; margin-left:10px;">
              			Add new...
              		</button>
              	</li>
            </ul>
			</c:when>
		</c:choose>
              	
            <ul class="nav navbar-nav navbar-right">
              <li role="presentation">
              	<form action="logoff.do" method="post">
    				<button type="submit" class="btn btn-primary" style="margin-top: 8px; margin-right:8px" value="Logout">
              			Log off
              		</button>
				</form>
              </li> 
            </ul>
		  </div>
		</div>
	</nav>
	<br/><br/><br/><br/>
	
	<div id="main" class="container">
		<div class="tbl-header">
		<h1 id="headerCount"></h1>
    		<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-striped table-hover table-list-search">
				<thead>
					
		<c:choose>
			<c:when test="${user.role.userRole == 'Manager'}">
					<tr>
						<th style="visibility: hidden;"></th>
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
    		<table id="tableReimbursements" cellpadding="0" cellspacing="0" border="0">
				<tbody>
				<c:forEach var="i" begin="0" end="${fn:length(pending) - 1}">
					<form method="post" action="submitted.do">
						<input type="hidden" name="resolver" value="${user.username}" />
						<tr>
							<td>${i + 1}<input type="hidden" name="rowId" value="${pending[i].id}" /></td>
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
				<tr>
					<th></th>
					<th>Amount</th>
					<th>Submitted On</th>
					<th>Description</th>
					<th>Type</th>
					<th>Status</th>
				</tr>
			</thead>
		</table>
	</div>
	<div class="tbl-content">
    	<table id="tableReimbursements" cellpadding="0" cellspacing="0" border="0">
			<tbody>
				<c:choose>
					<c:when test="${fn:length(usersData) == 0}">
						No reimbursements to show!
					</c:when>
					<c:otherwise>
						<%-- <c:forEach var="i" begin="0" end="${fn:length(usersData) - 1}"> --%>
						<c:forEach var="i" begin="0" end="${fn:length(usersData) - 1}">
							<tr class="Row" data-status="${usersData[i].status.status}">
								<td>${i + 1}</td>
								<td>$<fmt:formatNumber value="${usersData[i].amount}" minFractionDigits="2" /></td>
								<fmt:formatDate value="${usersData[i].submitted}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
								<td>${formattedDate}</td>
								<td>${usersData[i].description}</td>
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
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>
			</tbody>
		</table>
	</div>

	<form method="post" action="added.do">
		<input type="hidden" name="author" value="${user.usersId}" />
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  			<div class="modal-dialog" role="document">
    			<div class="modal-content">
      				<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
        				<h4 class="modal-title" id="exampleModalLabel">New reimbursement</h4>
					</div>
      				<div class="modal-body">
						<div class="form-group">
            				<label for="recipient-name" class="control-label">Amount:</label>
           					<div class="input-group">
            					<span class="input-group-addon">$</span>
            					<input type="text" class="form-control" name="amount" id="exampleInputAmount" placeholder="Amount" required="required">
          					</div>
         				</div>
						<div class="form-group">
            				<label for="message-text" class="control-label">Description:</label>
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
      				</div>
      				<div class="modal-footer">
        				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        				<input type="submit" value="Submit" style="color: black" class="btn btn-default">
      				</div>
    			</div>
  			</div>
		</div>
	</form>
	<br/><br/>
</body>

<script type="text/javascript">

	$(document).ready(function () {
		$('select.ddlFilterTableRow').bind('change', function () {
            $('select.ddlFilterTableRow').attr('disabled', 'disabled');
           $('#tableReimbursements').find('.Row').hide();
            var critriaAttribute = '';

            $('select.ddlFilterTableRow').each(function () {
                if ($(this).val() != '0') {
                    critriaAttribute += '[data-' + $(this).data('attribute') + '="' + $(this).val() + '"]';
                }
            });

            $('#tableReimbursements').find('.Row' + critriaAttribute).show();
            $('#headerCount').html($('table#tableReimbursements tr.Row:visible').length);
            $('select.ddlFilterTableRow').removeAttr('disabled');
        });
    });

</script>

</html>
