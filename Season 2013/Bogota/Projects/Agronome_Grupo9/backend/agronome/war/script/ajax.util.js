var HOME='home';
var ENTITY_PROVEEDOR='proveedor';

//parameter object definition
var param=function(name,value){
	this.name=name;
	this.value=value;
}

function obtenerUsuarioSession(){
	 var data=new Array();
	 data[data.length]=new param('action','sessionUser');
	 //making the ajax call
	 $.ajax({
			url : "/login",
			type : "GET",
			data:data,
			success : function(data) {
				if (data == 'ERROR') {
					window.location.href="index.html";
				}else{
					$('#usuario').val( data.data[0].usuario);
					$('#nit').val( data.data[0].nit);
					$('#nombre').val( data.data[0].nombre);
					$('#telefono').val( data.data[0].telefono);
					$('#etiquetas').val( data.data[0].etiquetas);
					$('#latitud').val( data.data[0].latitud);
					$('#longitud').val( data.data[0].longitud);
					$(".gllpUpdateButton").click();
					return data;
				}
					
			}
		});
}

function guardarProveedor(){
	var hasError = false;
	var usuario = $("#usuario").val();
	var pass = $("#pass").val();
	var confpass = $("#confpass").val();
	var nombre = $("#nombre").val();
	var telefono = $("#telefono").val();
	var etiquetas = $("#etiquetas").val();
	var longitud = $("#longitud").val();
	var latitud = $("#latitud").val();
	
	if(usuario == ""){
		hasError = true;
	}
	if(pass == "" ){
		hasError = true;
	}
	if(confpass == "" ){
		hasError = true;
	}
	if(nombre == "" ){
		hasError = true;
	}
	if(telefono == "" ){
		hasError = true;
	}
	if(etiquetas == "" ){
		hasError = true;
	}
	if( longitud == ""){
		hasError = true;
	}
	if( latitud == ""){
		hasError = true;
	}
	
	if(hasError == true){
		$('#proveedor-show-message').show().html('<p><b>'+'Valores Obligatorios'+'</b></p>');
		return;
	}
	// creating the data object to be sent to backend
	 var data=new Array();
	// collecting the field values from the form
	 var formEleList = $('form#proveedor-create-form').serializeArray();
	 for(var i=0;i<formEleList.length;i++){
		data[data.length]=new param(formEleList[i].name,formEleList[i].value);
	 }
	 //setting action as PUT
	 data[data.length]=new param('action','PUT');
	 //option 
	 data[data.length]=new param('option','CREATE');
	 
	 //making the ajax call
	 $.ajax({
			url : "/proveedor",
			type : "POST",
			data:data,
			success : function(data) {
				if (data != 'OK') {
					$('#proveedor-show-message').show().html('<p><b>'+data+'</b></p>');
				}else{
					window.location.href="index.html";
				}
				
			}
		});
}
function actualizarProveedor(){
	var hasError = false;
	var nombre = $("#nombre").val();
	var telefono = $("#telefono").val();
	var etiquetas = $("#etiquetas").val();
	var longitud = $("#longitud").val();
	var latitud = $("#latitud").val();
	
	if(nombre == "" ){
		hasError = true;
	}
	if(telefono == "" ){
		hasError = true;
	}
	if(etiquetas == "" ){
		hasError = true;
	}
	if( longitud == ""){
		hasError = true;
	}
	if( latitud == ""){
		hasError = true;
	}
	
	if(hasError == true){
		$('#proveedor-show-message').show().html('<p><b>'+'Valores Obligatorios'+'</b></p>');
		return;
	}
	// creating the data object to be sent to backend
	 var data=new Array();
	// collecting the field values from the form
	 var formEleList = $('form#proveedor-update-form').serializeArray();
	 for(var i=0;i<formEleList.length;i++){
		data[data.length]=new param(formEleList[i].name,formEleList[i].value);
	 }
	 //setting action as PUT
	 data[data.length]=new param('action','PUT');
	 //option 
	 data[data.length]=new param('option','UPDATE');
	 
	 //making the ajax call
	 $.ajax({
			url : "/proveedor",
			type : "POST",
			data:data,
			success : function(data) {
				if (data != 'OK') {
					$('#proveedor-show-message').show().html('<p><b>'+data+'</b></p>');
				}else{
					window.location.href="index.html";
				}
				
			}
		});
}

function loginUser(){
	var login = $("#usuario").val();
	var pass = $("#pass").val();
	var hasError = false;
	if(login == "" ){
		hasError = true;
	}
	if(pass == ""){
		hasError = true;
	}
	
	if(hasError == true){
		$('#login-show-message').show().html('<p><b>No ha ingresado los datos obligatorios</b></p>');
		return;
	}
	
	 var data=new Array();
		// collecting the field values from the form
		 var formEleList = $('form#loginForm').serializeArray();
		 for(var i=0;i<formEleList.length;i++){
			data[data.length]=new param(formEleList[i].name,formEleList[i].value);
		 }
		 //setting action as PUT
		 data[data.length]=new param('action','PUT');
		//making the ajax call
		 $.ajax({
				url : "/login",
				type : "POST",
				data:data,
				success : function(data) {
					if (data != '') {
						window.location.href="editar.html";
					}else{
						$('#login-show-message').show().html('<p><b>Usuario Inválido</b></p>');
					}
				}
			});
}



