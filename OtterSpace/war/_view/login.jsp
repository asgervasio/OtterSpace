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
		<form>
			
					Username:
					<input type="text" name="username" size="12" value="${username}" />
			
					Password:
					<input type="password" name="password" size="12" value="${password}" />
			
		</form>
			<td><input type = "Submit" name = "submit" value = "Login" /> </td>
			<td><input type="Submit" name="Register" value="Register"> </td>
				<input type="hidden" name="userName" value="user.getUsername">			
				<input type="hidden" name="sessionid" value="sessionid.getSessionid">
			</div>
		</form>
	</body>
</html>		