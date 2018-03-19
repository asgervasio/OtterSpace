<!DOCTYPE html>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		This is the index view jsp	
	
		<form action="${pageContext.servletContext.contextPath}/game" method="post">
			<input type="Submit" name="Game" value="Play the Game!">
		</form>
		
		<form action="${pageContext.servletContext.contextPath}/editor" method="post">
			<input type="Submit" name="Editor" value="Build the Game!">
		</form>	
	</body>
</html>
