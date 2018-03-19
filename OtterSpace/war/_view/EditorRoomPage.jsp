<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Room Creation</title>
		<link rel="stylesheet" type="text/css" href="EditorRoomScreenStyle.css" />
	</head>

	<body>		
		<form action="${pageContext.servletContext.contextPath}/editor" method="post">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>

			<table>
				<tr>
					<td class="label">Title:</td>
					<td><input type="text" name="title" size="20" value="${title}" /></td>
				</tr>
				<tr>
					<td class="label">Description:</td>
					<td><input type="text" name="description" size="20" value="${description}" /></td>
				</tr>
				<tr>
					<td class="label">Requirement:</td>
					<td><input type="text" name="requirement" size="20" value="${requirement}" /></td>
				</tr>				

				<tr>
					<td class="label"> Forward connection:</td>
					<td><input type="checkbox" name="connections" size="20" value="${connectionN}" /></td>
				</tr>				
				<tr>
					<td class="label">Left connection:</td>
					<td><input type="checkbox" name="test" size="20" value="${connectionW}"/></td>
				</tr>
				<tr>
					<td class="label">Right connection:</td>
					<td><input type="checkbox" name="test" size="20" value="${connectionE}"/></td>
				</tr>
				<tr>
					<td class="label">Rear connection:</td>
					<td><input type="checkbox" name="test" size="20" value="${connectionS}"/></td>
				</tr>

				<tr>
					<td class="label">Location:</td>
					<td><input type="text" name="location" size="20" value="${location}" /></td>
				</tr>				
				<tr>
					<td class="label">Item:</td>
					<td><input type="text" name="itemList" size="20" value="${itemList}" /></td>
				</tr>				

			</table>
			<input type="Submit" name="construct" value="Create Room!">
		</form>
								
	</body>
</html>