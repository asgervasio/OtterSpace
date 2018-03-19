<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

	<style>
		<jsp:include page="gameScreenStyle.css"/>
	</style>
	<head>
		<title>Game Page</title>
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
						<% out.println(request.getParameter("cmd")); %>
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