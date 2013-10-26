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
				$row->set_value("extra","<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=usuarios&id=".$row->get_value("id")."'\">&nbsp&nbsp<img src=\"images/eliminar.png\" title=\"Eliminar\" alt=\"Eliminar\" class=\"boton\" onclick=\"deleteConfirm('". $row->get_value("id") ."', 'usuarios', 'Realmente quieres eliminar este usuario y sus permisos asociados?')\">");
	}

$conn->event->attach("beforeRender","formatting");
$conn->render_sql("SELECT * FROM usuarios WHERE borrado=0", "id","usuario, extra","id");
 
?>