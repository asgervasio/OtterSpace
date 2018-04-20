<!DOCTYPE html>

<html>
	<style>
		<jsp:include page="indexStyle.css"/>	
	</style>
	
	
	<head>
		<title>Otter Space Index</title>
	</head>

	<body>
	

<div class="heading">
    		<h1>Fellow Otters In Arms</h1>
  		</div>
			<div>
/--SOPs needed for this Employee --/<br>
<br>
<c:forEach items="${users}" var="user">
Username: ${user.Username} <br>
Email Address: ${user.emailAdress}<br>
<br>
</c:forEach>
<div>
  		
  			<form action="${pageContext.servletContext.contextPath}/index.html" method="post">
				<input type="submit" name="Menu" value="Back to menu">
			</form>
 
		
	</body>
</html>