<?php
	$titulo="Nueva Cancha";
	$seccion="productos";
	$funcionValidacion = "formValidationProducto";
	$hayImagenes = true;
	$to="show.php?t=$seccion";
?>

<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		include('../conexion.php');		

	?>

	<form name="form" method="post" action="upload.php?t=<?php echo $seccion ?>"  onsubmit="return <?php echo $funcionValidacion ?>(<?php if ($hayImagenes) echo "false" ?>);" <?php if ($hayImagenes) echo 'enctype="multipart/form-data"' ?>>
		<ul>
			<li><label for="articulo">Nombre Cancha</label></li>
			<li><input type="text" style="width: 270px;" name="articulo" size="35" maxlength="30" value=""/></li>
			<li><label for="descripcion" >Rubro</label></li>
			<li>
			<select>
			  <option value="1">Tenis</option>
			  <option value="2"selected>Futbol</option>
			</select>
			</li>
			<li><label for="novedad">Estacionamiento</label></li>
			<li><input type="checkbox" name="novedad" value="1" /></li>
			<li><label for="novedad">Bar</label></li>
			<li><input type="checkbox" name="novedad" value="1" /></li>
			<li><label for="novedad">Duchas</label></li>
			<li><input type="checkbox" name="novedad" value="1"/></li>
			<li><label for="imagenGrande">Foto</label></li>
			<li><input type="file" style="width: 270px;margin-bottom:20px;" name="imagenGrande" size="35"/></li>
			<li><label for="imagenGrande">Seleccione la ubicacion</label></li>
			<LI><div id="map-canvas"></div></LI>
			<input type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
			<input type="submit" class="submit" name="submit" value="Guardar" />
		</ul>
	</form>
</div>