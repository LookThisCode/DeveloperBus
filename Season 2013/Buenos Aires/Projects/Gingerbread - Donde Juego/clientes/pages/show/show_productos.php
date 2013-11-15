<?php
	$titulo="Canchas";
	$seccion="productos";
?>

<div id="wrapper">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarProducto($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
		}
	?>
	<input type="button" value="Agregar" style="margin-bottom: 20px; margin-top:0;" onclick="window.location = 'upload.php?t=<?php echo $seccion ?>'"/>
	<table BORDER=1 align=center style="text-align:center">
<TR>
<th style="width:100px;">NOMBRE CANCHA</th>
<th style="width:200px;">IMAGEN</th>
<th style="width:100px;">RUBRO</th>
<th style="width:100px;">ESTACIONAMIENTO</th>
<th style="width:100px;">BAR</th>
<th style="width:100px;">DUCHAS</th>
<TH style="width:100px;">TECHADA</TH>
<TH style="width:100px;">ACCIONES</TH>
</tr>
<TR>
	<td>
	SERRANO CORNER
	</td>
	<td>
	<img src="images/FUTBOL1.JPG">
	</td>
	<td>
	FUTBOL
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/eliminar.png">
	</td><td>
	<a href="modify.php?t=productos&id=2"><img src="images/editar.png"></a>
	</td>
</tr>
<TR>
	<td>
	CANCHITAS "NACHO"
	</td>
		<td>
	<img src="images/FUTBOL1.JPG">
	</td>
	<td>
	FUTBOL
	</td>
	<td>
	<img src="images/ELIMINAR.png">
	</td>
	<td>
	<img src="images/ELIMINAR.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/CHECK.png">
	</td><td>
	<a href="modify.php?t=productos&id=2"><img src="images/editar.png"></a>
	</td>
</tr>
<TR>
	<td>
	TENIS "LA VIA"
	</td>	<td>
	<img src="images/TENIS1.JPG">
	</td>
	<td>
	TENIS
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/ELIMINAR.png">
	</td>
	<td>
	<img src="images/eliminar.png">
	</td><td>
	<a href="modify.php?t=productos&id=2"><img src="images/editar.png"></a>
	</td>
</tr>
<TR>
	<td>
	FUTBOL PAPI 5
	</td>
		<td>
	<img src="images/FUTBOL3.JPG">
	</td>
	<td>
	FUTBOL
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/check.png">
	</td>
	<td>
	<img src="images/eliminar.png">
	</td><td>
	<a href="modify.php?t=productos&id=2"><img src="images/editar.png"></a>
	</td>
</tr>
</table>
</div>

