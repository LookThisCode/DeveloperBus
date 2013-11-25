var req = createXMLHttpRequestObject();
var resultado = null;

function createXMLHttpRequestObject() {
	var req = null;
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return req;
}

function realizaBusca(chave){
	req.open("POST", "/busca", true);
	req.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	req.send("chave=" + chave+"&page=1");
	req.onreadystatechange = function() {
		if (req.readyState == 4 && req.status == 200) {
			resultado = req.responseText;
			var servicos = eval("(" + resultado + ")");
			alert("aqui");
		}
	}
}

function getResultado(){
	return resultado;
}

function clear(){
	resultado = null;
}