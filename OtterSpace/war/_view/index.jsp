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
  			<form action="${pageContext.servletContext.contextPath}/editorRoom" method="get">
				<input type="submit" name="startGame" value="Create Room">

			</form>  		
  			<form action="${pageContext.servletContext.contextPath}/editorItem" method="get">
				<input type="submit" name="startGame" value="Create Item">

			</form>  		
		</div>	
	</body>
</html>