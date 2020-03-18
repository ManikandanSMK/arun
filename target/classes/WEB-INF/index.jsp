<html>
<body>
<h2 id="test"></h2>
<button onclick="ajaxCall()">Click Me </button>
<script type="text/javascript">
function ajaxCall(){
	console.log("ajax function is called");
	var ajax=new XMLHttpRequest();
	var url="${requestScope.url}"+"/call";
	console.log("URL======"+url);
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4){
			var resp=ajax.responseText;
			document.getElementById("test").innerHTML=resp;
			
		}
	}
	ajax.open("GET",url,true)
	ajax.send();
}

</script>
</body>
</html>
