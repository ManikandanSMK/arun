<html>
<body>
<h2 id="test"></h2>
<button value="ajax call" onclick="ajaxCall()"> </button>
<script type="text/javascript">
function ajaxCall(){
	var ajax=new XMLHttpRequest();
	var url="${request.url}"+"/call";
	ajax.onreadystatechange=function(){
		if(request.readyState==4){
			var resp=request.responseText;
			document.getElementById("test").innerHTML=resp;
			
		}
	}
	ajax.open("GET",url,true)
}

</script>
</body>
</html>
