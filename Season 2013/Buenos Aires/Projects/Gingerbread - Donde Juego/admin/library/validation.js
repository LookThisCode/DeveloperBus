function formValidationTallePrecio() {
	document.getElementById("log").innerHTML = "";
	var talle = document.form.talle;
	var mayor = document.form.mayor;
	var menor = document.form.menor;
	var error = false;
	
	if (!val_minMaxLength(talle,1,20,"El nombre del color debe tener entre 1 y 20 caracteres.") ){
		error=true;
	}
	
	if( !val_precio(mayor,"El precio por mayor debe ser numerico y debe ser menor a 999999,99. Se aceptan hasta dos decimales.") ) {
		error=true;
	}
	
	if( !val_precio(menor,"El precio por menor debe ser numerico y debe ser menor a 999999,99. Se aceptan hasta dos decimales.") ) {
		error=true;
	}
	
	if (!error) {
		return true;
	}
	return false;
}

function formValidationColor() {
	document.getElementById("log").innerHTML = "";
	var nombre = document.form.nombre;
	var color = document.form.color;
	var error = false;
	
	if (!val_minMaxLength(nombre,1,30,"El nombre del color debe tener entre 1 y 30 caracteres.") ){
		error=true;
	}
	
	if(!val_minMaxLength(color,7,7,"Utilizar la herramienta debajo para elejir el color.") || !allhexa(color,"El color debe comenzar con # seguido de 6 numeros hexadecimales.") ) {
		error=true;
	}
	
	if (!error) {
		return true;
	}
	return false;
}

function formValidationNovedad(imagenModificable) {
	document.getElementById("log").innerHTML = "";
	var imagenGrande = document.form.imagenGrande;
	var nombre = document.form.nombre;
	var descripcion = document.form.descripcion;
	var error = false;
	
	if(!val_minMaxLength(nombre,1,40, "El nombre no puede quedar vacio y debe tener menos de 50 caracteres.")){
		error=true;
	}

	if(!val_noEmpty(descripcion, "Completar con una descripción del producto.")){
		error=true;
	}
	
	if (!imagenModificable){
		if (!val_noEmpty(imagenGrande, "Seleccionar una imagen asociada al producto.") ){
			error=true;
		}
	}
	
	if (!error) {
		return true;
	}
	return false;
}

function formValidationProducto(imagenModificable) {
	document.getElementById("log").innerHTML = "";
	var imagenGrande = document.form.imagenGrande;
	var articulo = document.form.articulo;
	var descripcion = document.form.descripcion;
	var error = false;

	if(!val_noEmpty(descripcion, "Completar el cuadro de información con una descripción del producto.")){
		error=true;
	}
	
	if(!val_noEmpty(articulo, "Completar el cuadro de articulo con el numero de articulo del producto.") || !allnumeric(articulo, "El cuadro de articulo debe contener solo numeros.")){
		error=true;
	}
	
	if (!imagenModificable){
		if (!val_noEmpty(imagenGrande, "Seleccionar una imagen asociada al producto.") ){
			error=true;
		}
	}
	
	if (!error) {
		return true;
	}
	return false;
}

function formValidationUsuarios() {
	document.getElementById("log").innerHTML = "";
	var usuario = document.form.usuario;
	var password = document.form.password;
	var error = false;
	
	if(!val_minMaxLength(usuario,0,50,"El nombre de usuario debe tener menos de 50 caracteres.")) {
		error=true;
	}
	
	if(!val_minMaxLength(password,7,50,"El password debe tener entre 8 y 50 caracteres.")) {
		error=true;
	}
	
	if (!error) {
		return true;
	}
	return false;
}

function log(logId, msg){
	var item = document.getElementById(logId);
	item.setAttribute("class", "log");
	item.setAttribute("className", "newClass");
	item.innerHTML = item.innerHTML + "<li>" + msg + "</li>";
}


function val_isDifferent (str,compare,msg){
	if (str.value != compare){
		return true;
	}
	log("log",msg);
	return false;	
}

function val_noEmpty (str,msg){
	if (str.value.length == 0){
		log("log",msg);
		return false;
	}
	return true;	
}
 
function val_minMaxLength(str,min,max,msg) {
	var str_len = str.value.length;
	if (str_len > max || str_len < min) {
		log("log",msg);
		return false;
	}
	return true;
}

function val_combo (combo,msg) {
	if(combo.value == "Default") {
		log("log",msg);
		return false;
	}
	return true;
}

function val_allLetter(str,msg) { 
	var letters = /^[A-Za-záéíóúÁÉÍÓÚ]+$/;
	if(str.value.match(letters)) {
		return true;
	}
	log("log",msg);
	return false;
}

function alphanumeric(str,msg) { 
	var letters = /^[0-9a-zA-ZáéíóúÁÉÍÓÚ]+$/;
	if(str.value.match(letters)) {
		return true;
	}
	log("log",msg);
	return false;
}

function allnumeric(str,msg) { 
	var numbers = /^[0-9]+$/;
	if(str.value.match(numbers)) {
		return true;
	}
	log("log",msg);
	return false;
}

function allhexa(str,msg) { 
	var numbers = /^#[0-9abcdefABCDEF]{6}$/;
	if(str.value.match(numbers)) {
		return true;
	}
	log("log",msg);
	return false;
}

function val_precio(str,msg) { 
	var precio = /^[0-9]{1,6}([,\.][0-9]{0,2})?$/;
	if(str.value.match(precio)) {
		return true;
	}
	log("log",msg);
	return false;
}


function val_email(email,msg) {
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(email.value.match(mailformat)) {
		return true;
	}
	log("log",msg);
	return false;
} 

function val_sex(umsex,ufsex,msg) {
	x=true;

	if(umsex.checked) {
		x=false;
	} 
	if(ufsex.checked) {
		x=false; 
	}

	if(x) {
		log("log",msg);
		return false;
	}
	return true;
} 

function completeCombo(array,element){
	var comboValue = element.value;
	element.options.length=1;
	for (var valued in array){
		if (array[valued] != "--No hay sub-menu--"){
			addCombo(valued, array[valued].toString(), element);
		}
	}
	
}

function cambiarComboSubMenu (array, element, change){
	var elemValue = element.value;
	if ( elemValue == "Default" || array[elemValue] == "--No hay sub-menu--" ) {
		change.setAttribute("disabled","disabled")
		change.value = "Default";
	}
	else {
		change.removeAttribute("disabled")
		completeCombo(array[elemValue],change);
	}
}

function addCombo(value, text, element) {
    var option = document.createElement("option");
	option.setAttribute('value',value);
	option.innerHTML = text;
    element.appendChild(option);
}

function selectCombo(id,element){
	if (id==0){
		element.value = "Default";
	}
	else {
	element.value = id;
	}
}