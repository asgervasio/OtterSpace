	<?php
// Start the session
session_start();
?>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
	<title> Login </title>

	<style>
		<link rel="stylesheet" type="text/css" href="css/loginStyle.css">
	</style>

	</head>
	
	<body>			
	
		<c:if test="${! empty errorMessage }">
			<div class = "error"> ${errorMessage}</div>
		</c:if>
		<form action = "${pageContext.servletContext.contextPath}/login" method = "post">
		<div id = "PageName"> Welcome to the OtterSpace Odyssey</div>
		<div id = "logon">
		
			
					Username:<br>
					<input type="text" name="username" size="12" value="${username}" /><br>
			
					Password:<br>
					<input type="password" name="password" size="12" value="${password}" /><br>
			
		
			<input type = "Submit" name = "submit" value = "Login" /> <br>
			<input type="Submit" name="Register" value="Register"> <br>
				<input type="hidden" name="userName" value="user.getUsername">			
				<input type="hidden" name="sessionid" value="sessionid.getSessionid">
			</div>
		</form>
	</body>
</html>		