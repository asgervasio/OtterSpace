<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
      <title>Account info</title>
		
      </head>
	<body>


User Name: 	${username}<br>
First name:	${firstName}<br>
Last name:	${lastName}<br>
<form action="${pageContext.servletContext.contextPath}/register" method="post">
Old Password: ${password}<br>
New Password (leave blank if n/a):	<input type="password" name=${password}><br>
Confirm New Password (leave blank if n/a): <input type="password" name=${passwordconfirm}><br>
Old E-Mail: ${emailAddress}<br>
New E-Mail (leave blank if n/a): 	<input type="text" name=${emailAddress}><br>

<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
	<form action="${pageContext.servletContext.contextPath}/index" method="get">
			<input type="Submit" name="index" value="Back To Menu"> <br>
	</form>
</form>


	</body>
</html>