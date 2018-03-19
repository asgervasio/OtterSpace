	<html>
	<style>
		<jsp:include page="gameScreenStyle.css"/>
	</style>
	<head>
	</head>
	
	<body>			

		<form action = "${pageContext.servletContext.contextPath}/login" method = "post">
		<div> Welcome to the Otter Space!</div>
		<div id = "logon">
		<table>
			<tr>
					<td class = "label"> Username: </td>
					<td><input type="text" name="username" size="12" value="${username}" /></td>
			</tr>
			<tr>
					<td class = "label"> Password: </td>
					<td><input type="password" name="password" size="12" value="${password}" /></td>
			</tr>
			</table>
			 <input type = "Submit" name = "submit" value = "Login" /> 
			
			
				<input type="hidden" name="userName" value="user.getUsername">			
				
			</div>
		</form>
	</body>
</html>		