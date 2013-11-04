function subirUno(ID,menu) {
	var to = "show.php?t="+menu;
	var p = {subir: ID};
	JSPost(to, p);
}

function bajarUno(ID,menu) {
	var to = "show.php?t="+menu;
	var p = {bajar: ID};
	JSPost(to, p);
}

function deleteConfirm(ID,menu,mensaje) {
	var to = "show.php?t="+menu;
	var answer = confirm(mensaje);
	
	if (answer){
		var p = {deleteID: ID};
		JSPost(to, p);
	}
}

function JSPost (to,p) {
	  var myForm = document.createElement("form");
	  myForm.method="post";
	  myForm.action = to ;

	  for ( var k in p) {
			var myInput = document.createElement("input") ;
			myInput.setAttribute("name", k) ;
			myInput.setAttribute("value", p[k]);
			myForm.appendChild(myInput) ;
	  }
	  
	  document.body.appendChild(myForm) ;
	  myForm.submit() ;
	  document.body.removeChild(myForm) ;
}

