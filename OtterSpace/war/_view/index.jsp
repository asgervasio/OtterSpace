<!DOCTYPE html>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		This is the index view jsp	
	
		<form action="${pageContext.servletContext.contextPath}/game" method="post">
			<input type="Submit" name="MultiplySubmit" value="Multiply Numbers!">
		</form>
		
		<form action="${pageContext.servletContext.contextPath}/gameEditor" method="post">
			<input type="Submit" name="AddSubmit" value="Add Numbers!">
			
	</body>
</html>
