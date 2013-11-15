<?php 


	include("../../../conexion.php");
	include("../../config.php");
	include("../../library/funciones.php");
	session_start();
	if ( !isset($_SESSION['userActual']) ){redireccionar("../../index.php");} //VALIDO QUE ESTE LOGUEADO EL USUARIO
	
require_once("../../codebase/connector/grid_connector.php");

$conn = new GridConnector($conexion,"MySQL");
$conn->set_encoding("iso-8859-1");

function formatting($row){
	$seccion = "colores";
	$mensajeParaEliminar = "Realmente quieres eliminar este color?";
	
	$row->set_value("imagen","<img style='margin:5px; width: 50px; height: 50px; background-color:#".$row->get_value("color").";' />");
	$row->set_value("extra","<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=$seccion&id=".$row->get_value("id")."'\">");	
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/eliminar.png\" title=\"Eliminar\" alt=\"Eliminar\" class=\"boton\" onclick=\"deleteConfirm('". $row->get_value("id") ."', '$seccion', '$mensajeParaEliminar')\">");	
}
$conn->dynamic_loading(20);
$conn->event->attach("beforeRender","formatting");
$conn->render_sql("	SELECT *
					FROM colores", "id","nombre, color, imagen, extra","color");

?>