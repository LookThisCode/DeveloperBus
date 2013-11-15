<?php
	$titulo="Modificar Producto de la Colección Niño";
	$seccion="nino";
	$funcionValidacion = "formValidationProducto";
	$hayImagenes = true;
	$to="show.php?t=$seccion";
?>


<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		include('../conexion.php');	
		if( isset($_GET['id']) && $_GET['id'] != "" && is_numeric($_GET['id']) ) {
			$id = $_GET['id'];				
		}
		else {
			redireccionar($to);
		}
		
		$rutaGrande = "../db/productoGrande/";
		
		$query = "SELECT * FROM productos WHERE borrado=0 AND id=". $id;
		$result = mysql_query($query, $conexion);
		$row = mysql_fetch_assoc($result);
		if( $row == false ){
			redireccionar($to);
		}
		$old_articulo = $row['articulo'];
		$old_descripcion = $row['descripcion'];
		$old_foto = $row['foto'];
		$old_visible = $row['visible'];
		$old_novedad = $row['novedad'];
		
		$breaks = array("<br />","<br>","<br/>");  
		$old_descripcion = str_ireplace($breaks, "", $old_descripcion);  
			
		
		if( isset($_POST['articulo']) && isset($_POST['descripcion'])) {
			postInt("articulo",$articulo);
			postString("descripcion", $descripcion);
			postBool("visible",$visible);
			postBool("novedad",$novedad);			
			
			$old_articulo = $articulo;
			$old_descripcion = $descripcion;
			$old_visible = $visible;
			$old_novedad = $novedad;
			
			if ($articulo != "" && strlen($descripcion)>0 ) {
			
				$random = randomString(6);		
				$imagen =  $articulo . "-" . $random;
				$imagen = preg_replace("/[^A-Za-z0-9\-]/",'',$imagen);
				val_modImg("imagenGrande",$imagen,$rutaGrande,$hayImagenGrande,$problemaImagenGrande,$logGrande);
				
				if ( !$hayImagenGrande || !$problemaImagenGrande ){
						
					$query = "UPDATE productos SET articulo=$articulo, descripcion='$descripcion', foto='$imagen', visible=$visible, novedad=$novedad WHERE id=$id";
					mysql_query($query, $conexion);
					
					if (mysql_error() == ''){
						echo '<ul id="log" > <li>Se ha cargado los datos correctamente</li></ul>';

						modificarImagen("imagenGrande",$rutaGrande,$imagen,$old_foto,"jpg",$hayImagenGrande);
						guardarActividad($_SESSION['userActual'], "El usuario ha modificado el producto: \"$articulo\" ID: $id.");
						redireccionar($to);
					}
					else {
						echo '<ul id="log" class="log"> <li>'. mysql_error().'</li></ul>';
					}
				}
				else {
					if ($logGrande != "") $logGrande = "<li>".$logGrande."</li>";
					echo '<ul id="log" class="log">'. $logGrande .'</ul>';				
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
	
	<form name="form" method="post" action="modify.php?t=<?php echo $seccion ?>&id=<?php echo $id?>"  onsubmit="return <?php echo $funcionValidacion ?>(<?php if ($hayImagenes) echo "true" ?>);" <?php if ($hayImagenes) echo 'enctype="multipart/form-data"' ?> >
		<ul>
			<li><label for="articulo">Articulo</label></li>
			<li><input type="text" style="width: 270px;" name="articulo" size="35" maxlength="30" value="<?php echo $old_articulo?>"/></li>
			<li><label for="descripcion" >Descripción</label></li>
			<li><textarea name="descripcion" style="max-width: 265px;width: 265px; height: 200px; margin-bottom: 10px; margin-left: 2px;" ><?php echo $old_descripcion?></textarea></li>
			<li><label for="imagenGrande">Foto</label></li>
			<li><input type="file" style="width: 270px;margin-bottom:20px;" name="imagenGrande" size="35"/></li>
			<li><label for="novedad">Novedad</label></li>
			<li><input type="checkbox" name="novedad" value="1" <?php if($old_novedad) echo 'checked="checked"'; ?> /></li>
			<li><label for="visible">Visible</label></li>
			<li><input type="checkbox" name="visible" value="1" <?php if($old_visible) echo 'checked="checked"'; ?> /></li>
			<input type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
			<input type="submit" class="submit" name="submit" value="Guardar" />
		</ul>
	</form>
</div>