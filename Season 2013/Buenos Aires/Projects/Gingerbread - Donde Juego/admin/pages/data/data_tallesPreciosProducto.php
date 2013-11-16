<?php 

	$to="show.php?t=hombres";
	if  (isset($_GET['id']) && $_GET['id'] != "" && is_numeric($_GET['id']))
	{
		$idProducto = $_GET['id']; 
	}else
	{			
		redireccionar($to);
	}
	
	include("../../../conexion.php");
	include("../../config.php");
	include("../../library/funciones.php");
	session_start();
	if ( !isset($_SESSION['userActual']) ){redireccionar("../../index.php");} //VALIDO QUE ESTE LOGUEADO EL USUARIO
	
require_once("../../codebase/connector/grid_connector.php");

$conn = new GridConnector($conexion,"MySQL");
$conn->set_encoding("iso-8859-1");

function formatting($row){
	$seccion = "tallesPreciosProducto&id=".$row->get_value("idProducto");
	$mensajeParaEliminar = "Realmente quieres eliminar el talle con sus precios?";
	
	$row->set_value("extra","<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=$seccion&id=".$row->get_value("id")."&idp=".$row->get_value("idProducto")."'\">");	
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/eliminar.png\" title=\"Eliminar\" alt=\"Eliminar\" class=\"boton\" onclick=\"deleteConfirm('". $row->get_value("id") ."', '$seccion', '$mensajeParaEliminar')\">");	
}
$conn->dynamic_loading(20);
$conn->event->attach("beforeRender","formatting");
$conn->render_sql("	SELECT talle, CONCAT('$',precioMayor) as precioMayor, CONCAT('$',precioMenor) as precioMenor, idProducto, id
					FROM preciosytalles
					WHERE idProducto=$idProducto AND borrado=0", "id","talle, precioMayor, precioMenor, extra","idProducto");

?>