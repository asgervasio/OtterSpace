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
    		<h1>Otter Space</h1>
  		</div>

  		<div class="Index">
  			<form action="${pageContext.servletContext.contextPath}/game" method="post">
				<input type="submit" name="startGame" value="Play">
			</form>
  			<form action="${pageContext.servletContext.contextPath}/editor" method="post">
				<input type="submit" name="startGame" value="Create">

			</form>  		
		</div>	
	</body>
</html>