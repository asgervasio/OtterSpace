$(document).ready(function()
{
	var content = document.getElementById("content");
	var moves = 0;

	function scrollDown() 
	{
		var objDiv = document.getElementById("content");
		objDiv.scrollTop = objDiv.scrollHeight;
	}
	
	function add()
	{
		var content = document.getElementById('content');
		var output = $('.commandline');
		output.before('test');
		var command = document.createElement('div');
		command.innerHTML = ("> " + output + "<br /><br />");
		content.appendChild(command);
		alert("test");
	}
	
	function show()
	{
		content.innerHTML += output.value + "<br/>";
	}

}