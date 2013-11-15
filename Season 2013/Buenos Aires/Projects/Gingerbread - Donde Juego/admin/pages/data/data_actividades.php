<?php 
	$t="actividades";
	include("../../../conexion.php");
	include("../../config.php");
	include("../../library/funciones.php");
	session_start();
	if ( !isset($_SESSION['userActual']) ){redireccionar("../../index.php");} //VALIDO QUE ESTE LOGUEADO EL USUARIO
	
require_once("../../codebase/connector/grid_connector.php");

$conn = new GridConnector($conexion,"MySQL");
$conn->set_encoding("iso-8859-1");

$conn->dynamic_loading(100);
$conn->render_sql("SELECT actividades.*, u.usuario
					FROM actividades 
					INNER JOIN usuarios AS u ON u.id = actividades.idUsuario
					ORDER BY actividades.fecha", "id","descripcion, usuario, fecha","id");
?>