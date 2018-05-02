<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<?php
session_start();
?>

<html>
	<head>
	<title> Login </title>

	<style>
		<jsp:include page="loginStyle.css"/>
	</style>

	</head>
	
	<body>	
			
	</php
	$_SESSION["username"];
	$_SESSION["firstName"];
	$_SESSION["lastName"];
	$_SESSION["password"];
	$_SESSION["sessionid"];
	$_SESSION["emailAddress"];
	?>
	
		<c:if test="${! empty errorMessage }">
			<div class = "error"> ${errorMessage}</div>
		</c:if>
		<form action = "${pageContext.servletContext.contextPath}/login" method = "post">
		<h1> Welcome to the <br> OtterSpace Odyssey </h1>
		

		
			
					<div class="label">Username:
					<input class="field" type="text" name="username" size="12" value="${username}" /></div><br>
			
					<div class="label">Password:
					<input class="field" type="password" name="password" size="12" value="${password}" /></div><br>
			
		
			<input type = "Submit" name = "submit" value = "Login" /> </form>
			<form action="${pageContext.servletContext.contextPath}/register" method="get">
			<input type="Submit" name="register" value="Register"> <br>
			</form>
				<input type="hidden" name="userName" value="user.getUsername">			
				<input type="hidden" name="sessionid" value="sessionid.getSessionid">
			
		
	</body>
</html>		