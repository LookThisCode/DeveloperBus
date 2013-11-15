<?php
/*IMAGENES*/	
function borrarImagen($ruta, $nombre, $ext) {
	$file = $ruta . $nombre . '.' . $ext;
	if ( file_exists($file) ) {
		unlink($file);
	}
}
function renombrarImagen($ruta, $vieja,$nueva, $ext){
	$fileViejo= $ruta. $vieja . '.' .$ext;
	$fileNuevo= $ruta. $nueva . '.' .$ext;
	if ( file_exists($fileViejo) ) {
		rename($fileViejo, $fileNuevo);	
	}
}
function modificarImagen($nombre,$ruta,$nueva,$vieja,$ext,$hayNuevaImagen){
	if (!$hayNuevaImagen) {
		renombrarImagen($ruta, $vieja, $nueva,$ext);
	}
	else {
		borrarImagen($ruta, $vieja, "jpg");
		move_uploaded_file($_FILES[$nombre]["tmp_name"], $ruta . $nueva .".".$ext);
	}
}

/*BORRAR*/

function borrarColor($id) {
	include("../conexion.php");
	
	$query = "SELECT id FROM coloresproductos WHERE idColor=$id";
	$request = mysql_query($query, $conexion);
	while ($row = mysql_fetch_assoc($request)) {
		$idColProd = $row['id'];	
		$respuesta = borrarColorProducto($idColProd);
		if ( $respuesta != "ok") {
			return $respuesta;
		}
	}
	
	$query = "SELECT nombre FROM colores WHERE id=$id";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	$nombre = $row['nombre'];
	
	$query = "DELETE FROM colores WHERE id=$id"; 
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado el color: \"$nombre\" ID: $id.");
		return "ok";
	}
	else
	{
		return "Error eliminando color de ID: $id (".mysql_error().")";
	}
}

function borrarTalleYPrecio($id) {
	include("../conexion.php");
	$query = 	"SELECT pyt.talle, p.articulo  
				FROM preciosytalles AS pyt
				INNER JOIN productos AS p ON p.id=pyt.idProducto
				WHERE pyt.id=$id";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	$talle = $row['talle'];
	$articulo = $row['articulo'];
	
	$query = "UPDATE preciosytalles SET borrado=1 WHERE id=$id"; 
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado los precios del talle: \"$talle\" del producto: \"$articulo\" ID: $id.");
		return "ok";
	}
	else
	{
		return "Error eliminando los precios y el talle de ID: $id (".mysql_error().")";
	}
}


function borrarNovedad($id) {
	include("../conexion.php");
	$query = 	"SELECT imagen, nombre  
				FROM novedades
				WHERE id=$id";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	$nombre = $row['nombre'];
	$imagen = $row['imagen'];
	
	$query = "UPDATE novedades SET borrado=1 WHERE id=$id"; 
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		borrarImagen("../db/novedades/",$imagen,"jpg");
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado la novedad: \"$nombre\" ID: $id.");
		return "ok";
	}
	else
	{
		return "Error eliminando los precios y el talle de ID: $id (".mysql_error().")";
	}
}

function borrarColorProducto($id) {
	include("../conexion.php");
	$query = 	"SELECT c.nombre, p.articulo
				FROM coloresproductos AS cp
				LEFT JOIN productos AS p ON p.id=cp.idProducto
				LEFT JOIN colores AS c ON c.id=cp.idColor
				WHERE id=$id";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	$articuloProducto = $row['articulo'];
	$nombreColor = $row['nombre'];
		
	$query = "DELTE FROM coloresproductos WHERE id=$id"; 
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado el color: \"$nombreColor\" del producto: \"$articuloProducto\" ID: $id.");
		return "ok";
	}
	else
	{
		return "Error eliminando el color del producto de ID: $id (".mysql_error().")";
	}
}


function borrarProducto($id) {
	include("../conexion.php");
	$query = "SELECT id FROM coloresproductos WHERE idProducto=$id";
	$request = mysql_query($query, $conexion);
	while ($row = mysql_fetch_assoc($request)) {
		$idColProd = $row['id'];	
		$respuesta = borrarColorProducto($idColProd);
		if ( $respuesta != "ok") {
			return $respuesta;
		}
	}
	
	$query = "SELECT id FROM preciosytalles WHERE idProducto=$id";
	$request = mysql_query($query, $conexion);
	while ($row = mysql_fetch_assoc($request)) {
		$idTalles = $row['id'];	
		$respuesta = borrarTallesYPrecio($idTalles);
		if ( $respuesta != "ok") {
			return $respuesta;
		}
	}	

	$query = "SELECT articulo, foto FROM productos WHERE id=$id";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	$articulo = $row['articulo'];
	$imagen = $row['foto'];
	
	$query = "UPDATE productos SET borrado=1 WHERE id=$id"; 
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		borrarImagen("../db/productoGrande/",$imagen,"jpg");
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado el producto: \"$articulo\" ID: $id.");
		return "ok";
	}
	else
	{
		return "Error eliminando producto de ID: $id (".mysql_error().")";
	}
}

function borrarUsuario($id) {
	include("../conexion.php");
	$nombre = returnNombreUsuario($id);
	$query = "UPDATE usuarios SET borrado=1 WHERE id=$id";
	mysql_query($query, $conexion);
	if ( mysql_error() == "") {
		guardarActividad($_SESSION['userActual'], "El usuario ha eliminado el usuario y sus permisos asociados: \"$nombre\" ID: $id.");
		return "ok";
	}
	else {
		return "Error eliminando usuario de ID: $id (".mysql_error().")";
	}
}
/*OTROS*/
function randomString($cnt) {
	$source = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
	$random = "";
	$source = str_split($source,1);
	for($i=1; $i<=$cnt; $i++){     
		mt_srand((double)microtime() * 1000000);
		$num = mt_rand(1,count($source));
		$random = $random . $source[$num-1];
	}
	return $random;
}

function val_img($nombre,$imagen,$ruta,&$tieneProblema,&$log){
	if ($_FILES[$nombre]["error"] > 0)	{
		$log = "Error al subir la imagen. (" . errorMsg($_FILES[$nombre]["error"]) . ")";
		$tieneProblema=1;
	}
	else	{
		if (($_FILES[$nombre]["type"] == "image/jpg") || ($_FILES[$nombre]["type"] == "image/jpeg") )	{
			if (file_exists($ruta . $imagen .".jpg"))	{
				$log = "Ya existe la imagen.";
				$tieneProblema=1;
			}
			else{
			$log ="";
			$tieneProblema=0;
			return;	
			}
		}
		else	{
			$log = "Formato de la imagen invalido. (Permitido: JPG)";
			$tieneProblema=1;
		}
	}
}
function encriptar($password, $salt){
	$encriptado = sha1(md5($password).md5($salt));
	return $encriptado;
}

function val_modImg($nombre,$imagen,$ruta,&$hayImagen,&$tieneProblema,&$log){
	if ( $_FILES[$nombre]["error"] !=4 ) {
		$hayImagen=1;
		val_img($nombre,$imagen,$ruta,$tieneProblema,$log);
	}
	else {
	$hayImagen=0;
	$tieneProblema =0;
	}
}

function errorMsg($code) { 
	switch ($code) { 
		case UPLOAD_ERR_INI_SIZE: 
			$message = "El archivo es demasiado grande."; 
			break; 
		case UPLOAD_ERR_FORM_SIZE: 
			$message = "El archivo es demasiado grande."; 
			break; 
		case UPLOAD_ERR_PARTIAL: 
			$message = "No se completó la carga del archivo."; 
			break; 
		case UPLOAD_ERR_NO_FILE: 
			$message = "No hay archivos para subir."; 
			break; 
		case UPLOAD_ERR_NO_TMP_DIR: 
			$message = "Falta la carpeta temporal."; 
			break; 
		case UPLOAD_ERR_CANT_WRITE: 
			$message = "Error de escritura en el disco."; 
			break; 
		case UPLOAD_ERR_EXTENSION: 
			$message = "Se detuvo la carga del archivo debido a una extension"; 
			break; 
			
		default: 
			$message = "Error desconocido."; 
			break; 
	} 
	return $message; 
} 

/*PARAMETROS GET Y POST*/
function isPOST($nombre) {
	return (isset($_POST[$nombre]) && $_POST[$nombre] != ""); 
}

function isGET($nombre) {
	return (isset($_GET[$nombre]) && $_GET[$nombre] != ""); 
}


function postBool($post,&$var){
	if (ISSET ($_POST[$post])) {
		$var = filter_var($_POST[$post],FILTER_VALIDATE_BOOLEAN);
	}
	else{
		$var = 0;
	}
}

function postString($post,&$var){
	$var = filter_var($_POST[$post], FILTER_SANITIZE_STRING);
}

function postInt($post,&$var){
	$var = intval($_POST[$post]);
}

function postFloat($post,&$var){
	$var = floatval($_POST[$post]);
}

/*FUNCIONES PARA MYSQL*/
function mysql_maximo($tabla, $campo){ 	// recibe tabla y un campo de la misma y devuelve el numero maximo (sirve pra el orderBy, para cuando se sube un producto nuevo por ejemplo)
	include("../conexion.php");
	$query = "SELECT MAX($campo) AS orden FROM $tabla";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	return $row['orden'];
}

function mysql_maximoSegun($tabla, $campo, $critero){ 	// recibe tabla y un campo de la misma y devuelve el numero maximo (sirve pra el orderBy, para cuando se sube un producto nuevo por ejemplo)
	include("../conexion.php");
	$query = "SELECT MAX($campo) AS orden FROM $tabla WHERE $criterio";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	return $row['orden'];
}

function mysql_subirUno($table, $campo, $criterio){	//menor es mas primero
	include("../conexion.php");
	$query = "UPDATE $table
				SET $campo=$campo+1
				WHERE $campo=(SELECT $campo-1 FROM $table WHERE $criterio)

			UPDATE $table
				SET $campo=$campo-1
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_bajarUno($table, $campo, $criterio){	//menor es primero
	include("../conexion.php");
	$query = "UPDATE $table
				SET $campo=$campo-1
				WHERE $campo=(SELECT $campo-1 FROM $table WHERE $criterio)

			UPDATE $table
				SET $campo=$campo+1
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_subirAPosicion($table, $campo, $criterio, $posicion){	//menor es primero
	include("../conexion.php");
	$query = "UPDATE $table
				SET $campo=$campo+1
				WHERE $campo>=$posicion
				AND $campo <(SELECT $campo FROM $table WHERE $criterio)

			UPDATE $table
				SET $campo=$posicion
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_bajarAPosicion($table, $campo, $criterio, $posicion){	//menor es primero
	include("../conexion.php");
	$query = "UPDATE $table
				SET $campo=$campo-1
				WHERE $campo<=$posicion
				AND $campo >(SELECT $campo FROM $table WHERE $criterio)

			UPDATE $table
				SET $campo=$posicion
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_subirRelativo($table, $campo, $criterio, $posicion){	//menor es primero
	include("../conexion.php");
	$query = "SET @campo := (SELECT $campo FROM $table WHERE $criterio)
			UPDATE $table
				SET $campo=$campo+1
				WHERE $campo<= (@campo - $posicion)
				AND $campo > @campo

			UPDATE $table
				SET $campo=(@campo - $posicion)
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_bajarRelativo($table, $campo, $criterio, $posicion){	//menor es primero
	include("../conexion.php");
	$query = "SET @campo := (SELECT $campo FROM $table WHERE $criterio)
			UPDATE $table
				SET $campo=$campo-1
				WHERE $campo>= (@campo + $posicion)
				AND $campo < @campo

			UPDATE $table
				SET $campo=(@campo + $posicion)
				WHERE $criterio";
	$request = mysql_query($query, $conexion);
	if (mysql_error() == ''){
		return 'ok';
	}
	else {
		return mysql_error();
	}
}

function mysql_cantidad($tabla, $condicion){		// recibe una tabla y una condicion y devuelve la cantida de ocurrencias
	include("../conexion.php");
	$query = "SELECT * FROM $tabla WHERE $condicion";
	$request = mysql_query($query, $conexion);
	if ($request == false) {
		return 0;
	}
	return mysql_num_rows($request);
}

function mysql_hayOtrosConPermisos($idSeccion,$minimo){ 		//recibe el id de seccion y la minima (inclusivo) cantidad de usuarios que tienen que tener permisos y devuelve bool dependiendo de esto.
	if ( mysql_cantidad("permisos", "seccion=".$idSeccion) >= $minimo )
		return true;
	else
		return false;
}

/*PERMISOS*/
function tienePermisosParaVer($seccion,$usuario){  // recibe una seccion y nombre de usuario y devuelve bool si tiene o no permisos
	include("../conexion.php");
	$query = "SELECT * 
			FROM  secciones 
			WHERE nombre='$seccion' AND visible=0";
	$request = mysql_query($query, $conexion);
	if (mysql_num_rows($request) > 0)
		return true;
	
	$query = "SELECT * 
			FROM  secciones 
			INNER JOIN usuarios ON usuarios.usuario = '$usuario'
			INNER JOIN permisos ON permisos.usuario = usuarios.id
			AND permisos.seccion = secciones.id
			WHERE secciones.nombre = '$seccion'";
	$request = mysql_query($query, $conexion);
	
	if (mysql_num_rows($request) > 0)
		return true;
	else
		return false;
}

function tablaQueTengaPermiso($usuario) { //recibe nombre de un usuario y devuelve una tabla que tenga permisos para ver el usuario. Si no tiene permisos, lo manda a desconectarse. (sirve cuadno un usuario es redireccionado a la pagina seleccionada en config, y este no tiene permisos en esta)
	include("../conexion.php");
	$query = "SELECT secciones.nombre AS tabla 
			FROM  secciones 
			INNER JOIN usuarios ON usuarios.usuario = '$usuario'
			INNER JOIN permisos ON permisos.usuario = usuarios.id AND permisos.seccion = secciones.id
			WHERE secciones.visible=1";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	if ( $row != false)
		return "show.php?t=".$row['tabla'];
	else
		return "index.php?a=logout";
}

function tieneAlgunaSeccion($usuario, $administrativo) { //recibe un usuario y 1 o 0 (dependiendo si se quiere ver si existe sesion permitida para administrador o secciones) y devuelve bool
	include("../conexion.php");
	$query = "SELECT secciones.nombre AS tabla 
			FROM  secciones 
			INNER JOIN usuarios ON usuarios.usuario = '$usuario'
			INNER JOIN permisos ON permisos.usuario = usuarios.id AND permisos.seccion = secciones.id
			WHERE secciones.administrativo = $administrativo AND secciones.visible=1";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	if ( $row != false)
		return true;
	else
		return false;
}

function guardarActividad($usuario, $mensaje) { 				//guarda actividad cualquiera sea en tabla actividades
	include("../conexion.php");
	date_default_timezone_set('America/Argentina/Buenos_Aires');
	setlocale(LC_TIME, 'spanish');
	$timeStamp = strftime("%Y-%m-%d %H:%M:%S");
	$idUsuario = returnIdUsuario($usuario); 

	$query = "INSERT INTO actividades (idUsuario, descripcion,fecha) VALUES ($idUsuario, '$mensaje', '$timeStamp')";
	$res = mysql_query($query, $conexion);	//guardo el ultimo login del usuario		
}
/*GENERALES*/
function returnIdUsuario($usuario){ //DADO UN NOMBRE, devuelve id del usuario
	include("../conexion.php");
	$query = "SELECT id  FROM usuarios WHERE usuario = '$usuario'";
	$request = mysql_query($query, $conexion);
	$row = mysql_fetch_assoc($request);
	return $row['id'];
}

function returnNombreUsuario($idusuario){ //DADA UNA ID, devuelve nombre del usuario
	include("../conexion.php");
	$query = "SELECT usuario FROM usuarios WHERE id = $idusuario";//
	$rs = mysql_query($query, $conexion);
	$row = mysql_fetch_array($rs);
	return $row['usuario'];
}
function reemplazar($string){
	if ($string!=""){
		$sano = array("\r\n","<br>","<","'","&");
		$sabroso = array("[br]","[br]","[","%21","q?v?k");
		$string = str_replace($sano, $sabroso, $string);
		$string = str_replace('"','k?v?q', $string);
	}
	return $string;
}
function redireccionar($to){
	header ("Location: ". $to );
	ob_end_flush();
	exit;
}
?>