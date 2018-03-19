<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game Page</title>
		<link rel="stylesheet" type="text/css" href="gameScreenStyle.css" />
	</head>

	<body>	
			
			<div class="container">
				<div class="scorebar">
					<div class="room"></div>
					<div class="moves">Moves: 0</div>
					<div class="score">Score: 0</div>
				</div>

				<div class="screen">
					<div class="content">
						<div class="commandline">
						<form action="${pageContext.servletContext.contextPath}/game" method="post">
							> <input type="text" name="cmd" autofocus/>
						</form>
						</div>
					</div>
				</div>
			</div>
			
	</body>
</html>