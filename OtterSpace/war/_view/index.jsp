<!DOCTYPE html>

<html>
	<style>
		<jsp:include page="indexStyle.css"/>	
	</style>
	
	
	<head>
		<title>OtterSpace Index</title>
	</head>

	<body>
	

<div class="heading">
    		<h4>Hello, ${username}.</h4>
  		</div>

  		<div class="Index">
  			<form action="${pageContext.servletContext.contextPath}/gameAjax.html" method="post">
				<input type="submit" name="startGame" value="Play">
			</form>
  			<form action="${pageContext.servletContext.contextPath}/editorRoom" method="get">
				<input type="submit" name="startGame" value="Create Room">

			</form>  		
  			<form action="${pageContext.servletContext.contextPath}/editorItem" method="get">
				<input type="submit" name="startGame" value="Create Item">

			</form>  
			<form action="${pageContext.servletContext.contextPath}/players" method="get">
				<input type="submit" name="players" value="View Players">

			</form>  
			<form action="${pageContext.servletContext.contextPath}/account" method="get">
				<input type="submit" name="account" value="Account Info">

			</form>  		
		</div>	
	</body>
</html>