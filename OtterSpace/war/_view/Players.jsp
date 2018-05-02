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

<br>
<c:forEach  items="${usernames}" var="user">
	<c:forEach  items="${emails}" var="email">
   <tr>
    <td>Username: ${user}</td>
	<td>Email Address: ${email}</td>
    
   </tr>

</a>
<br>
	</c:forEach>
</c:forEach>
<div>
  		
  			<form action="${pageContext.servletContext.contextPath}/index" method="get">
				<input type="submit" name="Menu" value="Back to menu">
			</form>
 
		
	</body>
</html>