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
	$row->set_value("imagenGrande","<img style='margin:5px;' width=\"100px\" src=\"../db/novedades/".$row->get_value("imagen").".jpg\"/>");
	/*$row->set_value("extra","<img src=\"images/bajar.png\" title=\"Bajar\" alt=\"Bajar\" class=\"boton\" onclick=\"bajarUno('". $row->get_value("id") ."', 'novedades')\">");
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/subir.png\" title=\"Subir\" alt=\"Subir\" class=\"boton\" onclick=\"subirUno('". $row->get_value("id") ."', 'novedades')\">");*/
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=novedades&id=".$row->get_value("id")."'\">&nbsp&nbsp<img src=\"images/eliminar.png\" title=\"Eliminar\" alt=\"Eliminar\" class=\"boton\" onclick=\"deleteConfirm('". $row->get_value("id") ."', 'novedades', 'Realmente quieres eliminar esta novedad?')\">");
	if ($row->get_value("idSubMenu") == 0)
		$row->set_value("subMenu","-");
	
}
$conn->dynamic_loading(20);
$conn->event->attach("beforeRender","formatting");
$conn->render_sql("	select *
					from novedades
					where borrado=0 
					ORDER BY orderBy ASC", "id","nombre, info, imagenGrande, visible, extra","imagen");

?>