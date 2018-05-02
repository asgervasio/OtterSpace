<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
      <title>Create New Account</title>
		
      </head>

<form action="${pageContext.servletContext.contextPath}/register" method="post">
User Name: <input type="text" name="username"><br>
Password: <input type="text" name="password"><br>
First Name: <input type="text" name="FirstName"><br>
Last Name: <input type="text" name="LastName"><br>
E-Mail: <input type="text" name="Email"><br>
<td><input type = "Submit" name = "submit" value = "Submit" /> </td>

</form>
<form action="${pageContext.servletContext.contextPath}/login" method="get">
				<input type="submit" name="login" value="Back to login">

			</form>

</body>
</html>