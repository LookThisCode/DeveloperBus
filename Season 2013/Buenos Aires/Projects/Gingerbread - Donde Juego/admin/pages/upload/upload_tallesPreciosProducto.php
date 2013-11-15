<?php
	$titulo="Nuevo Talle y Precios: ";
	$seccion="tallesPreciosProducto";
	$funcionValidacion = "formValidationTallePrecio";
	$hayImagenes = false;
	
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
	
	$to="show.php?t=$seccion&id=$idProducto";
	$titulo .= $articulo;
	
	
?> 
<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		include('../conexion.php');		
		if( isset($_POST['mayor']) && isset($_POST['talle'])) {
			postString("menor", $menor);
			postString("mayor", $mayor);
			postString("talle", $talle);
			
			$mayor = preg_replace("/$/",'',$mayor);
			$mayor = preg_replace("/,/",'.',$mayor);
			
			$menor = preg_replace("/$/",'',$menor);
			$menor = preg_replace("/,/",'.',$menor);
			
			if ($talle != "" && strlen($talle)<=20 && preg_match("/[0-9]{1,6}([.][0-9]{0,2})?/",$mayor) && preg_match("/[0-9]{1,6}([.][0-9]{0,2})?/",$menor) )  {
				$query = "INSERT INTO preciosytalles (idProducto, precioMenor, precioMayor, talle, borrado) VALUES ( $idProducto, '$menor', '$mayor', '$talle', 0 )";
				mysql_query($query, $conexion);
				$id = mysql_insert_id();
				if (mysql_error() == ''){
					echo '<ul id="log"> <li>Se ha cargado los datos correctamente</li></ul>';
					guardarActividad($_SESSION['userActual'], "El usuario ha creado el talle: \"$talle\" para: \"$articulo\" ID: $id.");
					redireccionar($to);
				}
				else {
					echo '<ul id="log" class="log"> <li>'. mysql_error().'</li></ul>';
				}
			}
			else {
				$log = "Hubo problemas al completar el formulario. Intente otra vez.";
				echo '<ul id="log" class="log"><li>'. $log .'</li></ul>';
			}
		}
		else {
			echo '<ul id="log"></ul>';				
		}
	?>
	
	<form name="form" method="post" action="upload.php?t=<?php echo $seccion ?>&id=<?php echo $idProducto ?>"  onsubmit="return <?php echo $funcionValidacion ?>(<?php if ($hayImagenes) echo "false" ?>);" <?php if ($hayImagenes) echo 'enctype="multipart/form-data"' ?>>
		<ul>
			<li><label for="talle">Talle</label></li>
			<li><input type="text" style="width: 270px;" name="talle" size="35" maxlength="20"/></li>
			
			<li><label for="mayor">Precio por Mayor</label></li>
			<li><input type="text" style="width: 270px;" name="mayor" size="35" maxlength="30"/></li>
			
			<li><label for="menor">Precio por Menor</label></li>
			<li><input type="text" style="width: 270px;" name="menor" size="35" maxlength="30"/></li>

			
			<input type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
			<input type="submit" class="submit" name="Guardar" value="Guardar" />
		</ul>
	</form>
</div>