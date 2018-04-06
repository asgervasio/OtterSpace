<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<style>
		<jsp:include page="EditorItemScreenStyle.css"/>
	</style>
	<head>
		<title>Item Creation</title>
	</head>

	<body>		
		<form action="${pageContext.servletContext.contextPath}/editorItem" method="post">
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
						<!-- Allows the user to enter the description of the room -->
						<h2>Description:</h2>
						<input type="text" name="description" value="${description}"/>
					</div>				
					<div class="column">
						<!-- Allows the user to enter the stat that will change when the user picks up the item -->
						<h2>Stat That Will Change:</h2>
						<input type="text" name="statAffected" value="${statAffected}"/>
						<!-- Allows the user to enter the degree of change that the item will have -->
						<h2>Value of Statistical Change:</h2>
						<input type="text" name="statChangeVal" value="${statChangeVal}"/>
											
					</div>
					<div class="column">
						<!-- Allows the user to put any requirements for entry into this room -->
						<h2>Room that will Havve the Item:</h2>
						<input type="text" name="roomLocat" size="12" value="${roomLocat}"/>
					</div>
				</div>
			</div>
			<!-- Creates the room and inserts it into the User's map-->
			<div class="createItem">
				<input type="Submit" name="construct" value="Create Item!">
			</div>
		</form>
								
	</body>
</html>