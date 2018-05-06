<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		<jsp:include page="indexStyle.css"/>	
	</style>
	
	
	<head>
		<title>View Other Otters</title>
	</head>

	<body>
	

<div class="heading">
    		<h1>Fellow Otters In Arms</h1>
  		</div>
			<div class="playerList">

<br>
<c:forEach  items="${poop}" var="user" varStatus = "iter">
	
   <tr>
    <td>Username: ${user.username}</td>
	<td>Email Address: ${user.email}</td>
    
   </tr>


<br>
	
</c:forEach>
<div>
  		
  			<form action="${pageContext.servletContext.contextPath}/index" method="get">
				<input type="submit" name="Menu" value="Back to menu">
			</form>
 
		
	</body>
</html>