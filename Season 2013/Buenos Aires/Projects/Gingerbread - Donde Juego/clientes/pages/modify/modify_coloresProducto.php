<?php
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
?>

<div id="wrapper" style="width:500px;">
	<h1 style="margin-bottom: 20px;">Modificar Colores: <?php echo $articulo; ?></h1>
	
	<?php
		//EMPIEZA EL TRABAJO PARA BUSCAR LOS DATOS Y COMPLETAR LOS CUADROS

		$query = 	"SELECT colores.color as color 
					FROM coloresproductos
					INNER JOIN colores ON colores.id=coloresproductos.idColor
					where coloresproductos.idProducto = $idProducto";//
		$rs = mysql_query($query, $conexion); //recibo los colores asociados por este producto
		$cantColoresAsociados=0;
		while($row = mysql_fetch_array($rs)){
			$coloresAsociados[$row["color"]] = 1; //guardo en un array los colores asociados del producto. Lo guardo como map array['key'] = value.
			$cantColoresAsociados++; //guardo la cantidad de colores asociados al producto
		}

			
		$query = 	"SELECT nombre, id, color, LENGTH(nombre) AS longNombre
					FROM colores
					ORDER BY longNombre DESC"; 	//Ordeno por mayor longitud de nombre, para que sea mas amigable a la vista el orden de los colores
		$rs = mysql_query($query, $conexion); //recibo todas los colores existentes
		
		$permitidas=""; //guardo string de "<li>" con colores asociados
		$denegadas="";//guardo string de "<li>" con colores NO asociados
		$cantTodosColores=0; //contador que aumenta con los colores totales
		
		while($row = mysql_fetch_array($rs)){ 
			$nombreColor = $row["nombre"];
			$color = $row["color"];
			$idColor = $row["id"]; 
			$todosColores[$color]= $idColor; //guarda array con TODAS los colores del existenes con sus ID. $nombreColor => $idColor
			$cantTodosColores++;
			
			if ( isset($coloresAsociados[$color]) ){
				$permitidas=$permitidas."<li value='1' id='$color'><div style=\"background-color:#$color;\"></div>$nombreColor</li>";
			}else{
				$denegadas=$denegadas."<li value='0' id='$color'><div style=\"background-color:#$color;\"></div>$nombreColor</li>";
			}
		}

	//AGARRO LOS POST PARA ACTUALIZAR (SI ES QUE HAY)
		
		if ( isPOST("idProducto" ) ) {
			
			$query = "DELETE FROM coloresproductos WHERE idProducto='$idProducto'"; //Borro todos los permisos anteriores del usuario
			mysql_query($query, $conexion);
			
			if (mysql_error() == ''){
				foreach ($todosColores as $key => $value ) {
					if ( isPOST($key) && $_POST[$key] == 1 )  { 	//agrego permiso por permiso
						$query = "INSERT INTO coloresproductos (idProducto, idColor) VALUES ('$idProducto', '$value')";
						mysql_query($query, $conexion);
						
						if (mysql_error() == ''){
							
						}
						else {
							echo '<ul id="log"> <li>'. mysql_error().'</li></ul>';
						}
					}		
				}
				guardarActividad($_SESSION['userActual'], "El usuario ha modificado colores del producto: \"$articulo\" ID: $idProducto.");
				redireccionar($to);
			}
			else {
				echo '<ul id="log"> <li>'. mysql_error().'</li></ul>';
			}
		}
			
	?>
	
	<!-- scripts drag --> 	
	<script src="library/funciones.js"></script>
	<script language="javascript" src="library/js-draggable/jquery-1.3.1.min.js"></script>
	<script language="javascript" src="library/js-draggable/jquery-ui-personalized-1.6rc6.min.js"></script>
	<script language="javascript">
		$(document).ready(function(){
			 // defino listas como variables
			var $listaPermitidos = $('#listaPermitidos'), $listaDenegados = $('#listaDenegados');
			// activo draggable a la primera lista
			$('li',$listaPermitidos).draggable({
				revert: 'invalid',      
				helper: 'clone',        
				cursor: 'move'
			});
			   // asigno droppable en la listaPermitidos hacia la listaDenegados
			$listaPermitidos.droppable({
				accept: '#listaDenegados li',
				drop: function(ev, ui) {
							   // Al hacer drop se borra el elemento
					deletelistaDenegados(ui.draggable);
				}
			});
			// Asigno draggable a la listaDenegados
			$('li',$listaDenegados).draggable({
				revert: 'invalid',
				helper: 'clone',    
				cursor: 'move'
			});
			   // Genero droppable para la segunda lista
			$listaDenegados.droppable({
				accept: '#listaPermitidos > li',
				drop: function(ev, ui) {
					deletelistaPermitidos(ui.draggable);     
				}
			});
			// Genero el borrado de items con el evento drop
			function deletelistaPermitidos($item) {
				$item.fadeOut(function() {
							 // Agrego el item dropeado y lo hago aparecer
					$($item).appendTo($listaDenegados).fadeIn();
					$($item).val("0");
				});
				$item.fadeIn();
			}
			function deletelistaDenegados($item) {
				$item.fadeOut(function() {          
								/// Agrego el item dropeado y lo hago aparecer
					$item.appendTo($listaPermitidos).fadeIn();
					$($item).val("1");
				});
			}
		});
	</script>
	<!-- /scripts drag --> 

	<div class="divListas listasColores">
		<ul id="listaPermitidos">
			<p>Colores Asociados</p>
			<?php echo $permitidas; ?>
		</ul>
		<ul id="listaDenegados">
			<p>Colores NO Asociados</p>
			<?php echo $denegadas; ?>
		</ul>
	</div>

	<div>
		<input class="grabarPermisos" type="button" name="Cancelar" value="Cancelar" class="submit" onclick="window.location = '<?php echo $to; ?>'" />
		<input class="grabarPermisos" type="button" value="Guardar" onclick="JSPost('modify.php?t=coloresProducto&id=<?php echo $idProducto; ?>', <?php echo "{idProducto:".$idProducto;
			foreach ( $todosColores as $color => $value) {
				echo ", '". $color ."': document.getElementById('". $color ."').value";
			}
			echo "} );";
		?>
		">
	</div>
</div>

</div>