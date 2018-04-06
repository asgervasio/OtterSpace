<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		<link rel="stylesheet" type="text/css" href="css/EditorRoomScreenStyle.css">
	</style>
	<head>
		<title>Room Creation</title>
	</head>

	<body>		
		<form action="${pageContext.servletContext.contextPath}/editorRoom" method="post">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>

			<div class="screen">
				<!-- Allows the user to enter the title (name) of the room -->
				<div class="title">Title:
					<input type="text" name="title" size="12" value="${title}"/>
				</div>
				<div class="row">			
					<div class="column">
						<!-- Allows the user to input the 3D location of the room -->
						<h2>LocationX:</h2>
						<input type="text" name="locationX" size="12" value="${locationX}"/>				
						<h2>LocationY:</h2>
						<input type="text" name="locationY" size="12" value="${locationY}"/>
						<h2>LocationZ:</h2>
						<input type="text" name="locationZ" size="12" value="${locationZ}"/>
					</div>				
					<div class="column">
						<!-- Allows the user to enter the description of the room -->
						<h2>Description:</h2>
						<input type="text" name="description" value="${description}"/>
					</div>
					<div class="column">
						<!-- Allows the user to put any requirements for entry into this room -->
						<h2>Requirement:</h2>
						<input type="text" name="requirement" size="12" value="${requirement}"/>
						<!-- Allows the user to insert an item into the room that the player can pick up -->
						<h2>Item:</h2>
						<input type="text" name="itemList" size="12" value="${itemList}"/>
					</div>
					<div class="column">
						<h2>Connection:</h2>
						<input type="text" name="connection" size="12" value="${connection}"/>
					</div>				
				</div>
			</div>
			<!-- Creates the room and inserts it into the User's map-->
			<div class="createRoom">
				<input type="Submit" name="construct" value="Create Room!">
			</div>
		</form>
								
	</body>
</html>