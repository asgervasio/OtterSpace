<!DOCTYPE html>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		This is the index view jsp	
	
		<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="post">
			<input type="Submit" name="MultiplySubmit" value="Multiply Numbers!">
		</form>
		
		<form action="${pageContext.servletContext.contextPath}/addNumbers" method="post">
			<input type="Submit" name="AddSubmit" value="Add Numbers!">
			
		</form>
			<form action="${pageContext.servletContext.contextPath}/guessingGame" method="post">
			<c:if test="${empty game}">
				<input name="startGame" type="submit" value="Guessing Game!" />
			</c:if>
		</form>
	</body>
</html>
