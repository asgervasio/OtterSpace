<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
      <title>Create New Account</title>
		
		<style>
		<link rel="stylesheet" type="text/css" href="css/loginStyle.css">
	</style>
	
      </head>

<form action="${pageContext.servletContext.contextPath}/register" method="post">
User Name: <input type="text" name="username"><br>
Password Name: <input type="text" name="password"><br>
<!--Employee Number: <input type ="text" name="UserID"><br>-->
First Name: <input type="text" name="FirstName"><br>
Last Name: <input type="text" name="LastName"><br>
E-Mail: <input type="text" name="Email"><br>
<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
<td><input type = "Back to Login" name = "login" value = "Login" /> </td>
</form>


</body>
</html>