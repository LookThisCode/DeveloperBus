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
	$seccion = "hombre";
	$mensajeParaEliminar = "Realmente quieres eliminar este producto?";
	
	$colores = $row->get_value("colores");
	if ($colores != NULL)  {
		$colores = split(",",$colores);
		$colorete = "<div class=\"divColores\"><ul>";
		foreach ($colores as $n => $color) {
			$colorete .= "<li ><div style=\"width:10px; height:10px; border: 1px solid black; background-color: #$color\"></div></li>";
		}	
		$colorete .= "</ul></div>";
		$row->set_value("colorific",$colorete);
	}
	$row->set_value("imagen","<img style='margin:5px;' width=\"100px\" src=\"../db/productoGrande/".$row->get_value("foto").".jpg\"/>");
	$row->set_value("extra","<img src=\"images/editar.png\" title=\"Editar\" alt=\"Editar\" class=\"boton\" onclick=\"window.location = 'modify.php?t=$seccion&id=".$row->get_value("id")."'\">");	
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/color.gif\" title=\"Colores\" alt=\"Colores\" class=\"boton\" onclick=\"window.location = 'modify.php?t=coloresProducto&id=".$row->get_value("id")."'\">");	
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/precio.png\" title=\"Precios\" alt=\"Precios\" class=\"boton\" onclick=\"window.location = 'show.php?t=tallesPreciosProducto&id=".$row->get_value("id")."'\">");	
	$row->set_value("extra",$row->get_value("extra")."&nbsp&nbsp<img src=\"images/eliminar.png\" title=\"Eliminar\" alt=\"Eliminar\" class=\"boton\" onclick=\"deleteConfirm('". $row->get_value("id") ."', '$seccion', '$mensajeParaEliminar')\">");	
}
$conn->dynamic_loading(20);
$conn->event->attach("beforeRender","formatting");
$conn->render_sql("	SELECT productos.*, 'rojo' as colores
					FROM productos
					WHERE productos.borrado=0
					GROUP BY productos.id", "id","articulo, descripcion, imagen, colorific, novedad, visible, extra","foto, colores");

?>