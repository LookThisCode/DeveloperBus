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

function formatting($row){
	$row->set_value("editar","<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=permisos&id=".$row->get_value("id")."'\">");
}
$conn->dynamic_loading(100);
$conn->event->attach("beforeRender","formatting");
$conn->render_sql("SELECT * FROM usuarios WHERE borrado=0 order by id ASC", "id","usuario, editar","id");
?>