<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		<jsp:include page="indexStyle.css"/>	
	</style>
	
	<head>
      <title>Account info</title>
		
      </head>
	<body>

<div class="account">

User Name: 	${username}<br>
First name:	${firstName}<br>
Last name:	${lastName}<br>
<form action="${pageContext.servletContext.contextPath}/account" method="post">
Old Password: ${password}<br>
New Password (leave blank if n/a):	<input type="password" ><br>
Confirm New Password (leave blank if n/a): <input type="password" ><br>
Old E-Mail: ${emailAddress}<br>
New E-Mail (leave blank if n/a): 	<input type="text" ><br>

	<td><input type = "Submit" name = "submit" value = "Submit" /> </td>
</form>
	<form action="${pageContext.servletContext.contextPath}/index" method="get">
			<input type="Submit" name="index" value="Back To Menu"> <br>
	</form>

</div>

	</body>
</html>