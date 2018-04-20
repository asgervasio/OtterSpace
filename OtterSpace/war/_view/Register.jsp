<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
      <title>Create New Account</title>
		
      </head>

<form action="${pageContext.servletContext.contextPath}/register" method="post">
UserName: <input type="text" name="username"><br>
Password: <input type="password" name="password"><br>
Confirm Password: <input type="password" name="passconfirm"><br>
First Name: <input type="text" name="FirstName"><br>
Last Name: <input type="text" name="LastName"><br>
E-Mail: <input type="text" name="Email"><br>
<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
</form>
<form action="${pageContext.servletContext.contextPath}/login" method="get">
<td><input type = "Submit" name = "login" value = "Back to Login" /> </td>
</form>


</body>
</html>