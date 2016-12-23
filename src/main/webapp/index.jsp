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
<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>

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
	
/* 	td {
    	vertical-align: top;
	} */
	 #table2 {
 		display: none;
	}
	
	.successMessage{
		text-align: center;
		position: relative;
  		top: 0px;
  		left: 470px;
  		background-color: #5cb85c;
	}
	
	.noReimb{
		text-align: center;
		font-size: 20px;
		color: black;
		position: absolute;
  		top: 50%;
  		left: 50%;
 	 	margin: -150px 0 0 -150px;
	}
	
 	.resolvedDate{
		font-size: 10px;
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
/* 	  line-height: 70%; */
	 }
	.tbl-content{
	  overflow-x:auto;
	  margin-top: 0px;
	  border: 1px solid rgba(255,255,255,0.3);
	}
	th{
	  padding: 20px 8px;
	  text-align: center;
	  font-weight: bold;
	  font-size: 12px;
	 /*  color: #fff; */
	  color:black;
	  text-transform: uppercase;
	}
	td{
	  /* vertical-align: top; */
	  padding: 5px;
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
		<c:choose>
			<c:when test="${user.role.userRole == 'Manager'}">
					<a href="#" onclick='show(3);'>Home 
              			<span class="badge">${fn:length(pending)} </span>
              		</a>
              	</li>
              	<li>
              		<a href="#" onclick='show(2);' class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              			View completed <span class="badge">${fn:length(completed)} </span>
              		</a>
              	</li>
            </ul>
			</c:when>
			<c:when test="${user.role.userRole == 'Employee'}">
					<a href="#">Home 
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
	
	<c:choose>
		<c:when test="${added == 'Successful'}">
			<div id="alert_message" class="alert successMessage" style="width:420px">
				<span class="close" data-dismiss="alert">&times;</span>
				<span><strong>Congratulations!</strong> Reimbursement added.</span>
			</div>
		</c:when>
		<c:when test="${updated == 'Approved'}">
			<div id="alert_message" class="alert successMessage" style="width:420px">
				<span class="close" data-dismiss="alert">&times;</span>
				<span><strong>Success!</strong> Reimbursement approved.</span>
			</div>
		</c:when>
		<c:when test="${updated == 'Denied'}">
			<div id="alert_message" class="alert successMessage" style="width:420px">
				<span class="close" data-dismiss="alert">&times;</span>
				<span><strong>Success!</strong> Reimbursement denied.</span>
			</div>
		</c:when>
	</c:choose>
				
		<c:choose>
			<c:when test="${user.role.userRole == 'Manager'}">
				<c:choose>
						<c:when test="${fn:length(pending) == 0}">
							<div class="noReimb">
							<img src="No-reimb.jpg"><br/>
							*sad kitty*<br/>No reimbursements to show here!
							</div>
						</c:when>
						<c:otherwise>
	<div id="table3"> 
		<div id="main" class="container">
			<div class="tbl-header">
			<h1 id="headerCount"></h1>
				<!-- cellpadding="0" cellspacing="0" border="0" ///// table-hover table-list-search table-striped-->
	    		<table class="table table-bordered">
					<thead>
						<tr>
							<th>ID#</th>
							<th>Amount</th>
							<th>Submitted On</th>
							<th>Description</th>
							<th>Submitted By</th>
							<th>Status</th>
							<th>Action</th>
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
								<td>${pending[i].id} <input type="hidden" name="rowId" value="${pending[i].id}" /></td>
								<td>$<fmt:formatNumber value="${pending[i].amount}" minFractionDigits="2" /></td>
								<fmt:formatDate value="${pending[i].submitted}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
								<td>${formattedDate}</td>
								<td>${pending[i].description}</td>
								<td>${pending[i].author.firstName} ${pending[i].author.lastName}</td>
								<td>${pending[i].type.type}</td>
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
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="table2"> 
		<div id="main" class="container">
			<div class="tbl-header">
			<h1 id="headerCount"></h1>
	    		<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-striped table-hover table-list-search">
					<thead>
						<tr>
							<th>ID#</th>
							<th>Amount</th>
							<th>Completed On</th>
							<th>Description</th>
							<th>Submitted By</th>
							<th>Type</th>
							<th>Status</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tbl-content">
	    		<table id="tableReimbursements" cellpadding="0" cellspacing="0" border="0">
					<tbody>
					<c:forEach var="i" begin="0" end="${fn:length(completed) - 1}">
						<input type="hidden" name="resolver" value="${user.username}" />
						<tr>
							<td>${completed[i].id}</td>
							<td>$<fmt:formatNumber value="${completed[i].amount}" minFractionDigits="2" /></td>
							<fmt:formatDate value="${completed[i].resolved}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
							<td>${formattedDate}</td>
							<td>${completed[i].description}</td>
							<td>${completed[i].author.firstName} ${pending[i].author.lastName}</td>
							<td>${completed[i].type.type}</td>
							<td>
								<div class="statusButtons">
								<c:choose>
									<c:when test="${completed[i].status.status == 'Approved'}">
				    					<h4><span class="label label-success" style="width:96px">${completed[i].status.status}</span></h4>
				  					</c:when>
				  					<c:when test="${completed[i].status.status == 'Denied'}">
				    					<h4><span class="label label-danger" style="width:96px">${completed[i].status.status}</span></h4>
				  					</c:when>
				  					<c:otherwise>
				  						<h4><span class="label label-warning" style="width:96px">${completed[i].status.status}</span></h4>
				  					</c:otherwise>
								</c:choose>
								</div>
							</td>
						</tr>	
					</c:forEach>
	                </tbody>
				</table>
			</div>
		</div>
	</div>	
					</c:otherwise>
				</c:choose>	
			</c:when>
			<c:when test="${user.role.userRole == 'Employee'}">
	 				<c:choose>
						<c:when test="${fn:length(usersData) == 0}">
							<div class="noReimb">
							<img src="No-reimb.jpg"><br/>
							*sad kitty*<br/>No reimbursements to show here!
							</div>
						</c:when>
						<c:otherwise>
	<div id="main" class="container">
		<div class="tbl-header">
		<h1 id="headerCount"></h1>
    		<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-striped table-hover table-list-search">
				<thead>
					<tr>
						<th>ID#</th>
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
							<c:forEach var="i" begin="0" end="${fn:length(usersData) - 1}">
								<tr class="Row" data-status="${usersData[i].status.status}">
									<td>${usersData[i].id}</td>
									<td>$<fmt:formatNumber value="${usersData[i].amount}" minFractionDigits="2" /></td>
									<fmt:formatDate value="${usersData[i].submitted}" var="formattedDate" type="date" pattern="MM-dd-yyyy" />
									<td>${formattedDate}</td>
									<td>${usersData[i].description}</td>
									<td>${usersData[i].type.type}</td>
									<td>
										<div class="statusButtons">
									<c:choose>
										<c:when test="${usersData[i].status.status == 'Approved'}">
				    						<div style="line-height: 10%">
					    						<h4><span class="label label-success" style="width:96px">${usersData[i].status.status}</span></h4>
					    						<fmt:formatDate value="${usersData[i].resolved}" var="formattedDate2" type="date" pattern="MM-dd-yyyy" />
					    						<br/>
					    						<div class="resolvedDate">
					    							On ${formattedDate2} by ${usersData[i].resolver.firstName} ${usersData[i].resolver.lastName}
					    						</div>
				    						</div>
				  						</c:when>
				  						<c:when test="${usersData[i].status.status == 'Denied'}">
				    						<div style="line-height: 10%">
				    							<h4><span class="label label-danger" style="width:96px">${usersData[i].status.status}</span></h4>
				  								<fmt:formatDate value="${usersData[i].resolved}" var="formattedDate2" type="date" pattern="MM-dd-yyyy" />
					    						<br/>
					    						<div class="resolvedDate">
					    							On ${formattedDate2} by ${usersData[i].resolver.firstName} ${usersData[i].resolver.lastName}
					    						</div>
				    						</div>
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
				</tbody>
			</table>	
		</div>
	</div>
				</c:when>
			</c:choose>	

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
            					<input type="number" step="0.01" pattern="^\d+(?:\.\d{1,2})?$" class="form-control" name="amount" id="exampleInputAmount" placeholder="Amount" required="required">
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
    
    function show(nr) {
    	/* document.getElementById("table1").style.display="none"; */
    	document.getElementById("table2").style.display="none";
    	document.getElementById("table3").style.display="none";
    	/* document.getElementById("table4").style.display="none"; */
    	document.getElementById("table"+nr).style.display="block";
	}
	
	window.setTimeout(function() {
		$("#alert_message").fadeTo(500, 0).slideUp(500, function(){
    		$(this).remove(); 
		});
	}, 3000);
</script>

</html>
