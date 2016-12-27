<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Sign in: Expense Reimbursement System</title>

<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<link href="signin.css" rel="stylesheet">


<style>
	form {
		text-align: center;
	}
	.container {
		padding: 100px 400px
	}
	#loginStyle {
		margin: 10px 0px
	}
	#showhide{
		float: right;
		padding: 7px 0px 7px 0px;
		color: white
	}
</style>
</head>
<body style="background-color: #082047">
	<c:choose>
		<c:when test="${authFailed == 'Try to login again'}">
			<div class="alert alert-danger" role="alert" align="center">
				<strong>Oh snap!</strong> Incorrect username or password. Please try again.
			</div>
		</c:when>
	</c:choose>

	<div class="container" style="background-color: #082047">
		<form class="form-signin" method="post" action="home.do">
			<h2 style="color: white">Welcome to ERS</h2>
			<input class="form-control" placeholder="Username" required name="username" id="loginStyle"> 
			<input id="pwd0" type="password" class="form-control" placeholder="Password" required name="password" id="loginStyle"> 
			<a href="#" onclick="toggle_password('pwd0');" id="showhide" style="text-decoration : none">Show password</a>
			<button class="btn btn-lg btn-primary btn-block" type="submit" id="loginStyle">Sign in</button>
		</form>
	</div>

	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>

<script type="text/javascript">
function toggle_password(target){
    var d = document;
    var tag = d.getElementById(target);
    var tag2 = d.getElementById("showhide");

    if (tag2.innerHTML == 'Show password'){
        tag.setAttribute('type', 'text');   
        tag2.innerHTML = 'Hide password';

    } else {
        tag.setAttribute('type', 'password');   
        tag2.innerHTML = 'Show password';
    }
}

$("#txt1").click(function() {
  $(this).effect( "shake" );
});
</script>

</html>