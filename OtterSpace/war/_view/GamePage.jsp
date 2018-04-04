<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

	<style>
		<jsp:include page="gameScreenStyle.css"/>
	</style>
	<script src="${pageContext.request.contextPath}/JavaScript/gamePage.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/JavaScript/jquery.js" type="text/javascript"></script>
	<script>
	function add()
	{
		var content = document.getElementById('content');
		var output = $('.commandline');
		var input = $('input');
		var cmd = input.val();
		output.before("> " + cmd + "<br /><br />");
	}
	</script>
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
						<div class="commandline">
						<form action="${pageContext.servletContext.contextPath}/game" method="post" onsubmit="add();">
							> <input type="text" id="cmd" name="cmd" autofocus/>
						</form>
						
						</div>
					</div>
				</div>
			</div>
			
	</body>
</html>