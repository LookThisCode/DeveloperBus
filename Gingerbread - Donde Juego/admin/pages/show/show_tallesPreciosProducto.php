<?php
	$titulo="Talles y Precios: ";
	$seccion="tallesPreciosProducto";
	
	$to="show.php?t=hombres";
	if  (isset($_GET['id']) && $_GET['id'] != "" && is_numeric($_GET['id']))
	{
		$idProducto = $_GET['id']; 
	}else
	{			
		redireccionar($to);
	}
	
	$query = "SELECT articulo, coleccion FROM productos WHERE borrado=0 AND id=$idProducto";
	$rs = mysql_query($query, $conexion); 
	$row = mysql_fetch_array($rs);
	$coleccion = $row['coleccion'];
	$articulo = $row['articulo'];
	
	$coleccion = strtolower($coleccion);
	$coleccion = preg_replace("/ñ/",'n',$coleccion);		
	
	if ( !tienePermisosParaVer($coleccion,$_SESSION['userActual']) ){				//VALIDO QUE TENGA PERMISOS PARA VER LA SECCION
		if ( $t == $tablaRedireccion ) {						//Si justo no tiene permisos para la tabla que va predefinida, lo mando a otra. Si no tiene ninguna seccion habilitada, le cierro la sesion.
			$redireccionLog = tablaQueTengaPermiso($_SESSION['userActual']);
		}
		redireccionar($redireccionLog);
	} 
	
	if ($articulo == "" || $articulo == false){
		redireccionar($to);
	}// por si el id recibido en el GET "id" no es valido.
	
	$to="show.php?t=$coleccion";
	
	$titulo .= $articulo;
?>

<div id="wrapper">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarTalleYPrecio($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
		}
	?>
	<input type="button" value="Agregar" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = 'upload.php?t=<?php echo $seccion ?>&id=<?php echo $idProducto ?>'"/>
	<input type="button" value="Volver" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = '<?php echo $to ?>'"/>
	<div id="grid" style="overflow: hidden; height:500px; border: 1px solid #313131;"></div>
</div>

<script>
function onLoadDo(){
	myGrid = new dhtmlXGridObject("grid");
	myGrid.setImagePath("codebase/imgs/");
	myGrid.setHeader("Talle,Precio por Mayor,Precio por Menor,Acciones");
	myGrid.attachHeader("#connector_text_filter,#connector_text_filter,#connector_text_filter,");
	myGrid.setColSorting("connector,connector,connector,");
	myGrid.setColAlign("left,left,left,center");
	myGrid.setInitWidths("*,*,*,*");
	myGrid.enableResizing("false,false,false,false");
	myGrid.setColTypes("ed,ed,ed,ed");
	myGrid.setSkin("sbdark");
	myGrid.setEditable(false);
	myGrid.init();
	myGrid.load("pages/data/data_<?php echo $seccion ?>.php?id=<?php echo $idProducto;?>");
	myGrid.enableSmartRendering(true,20);
}
</script>